WITH $orgId AS organizationId,
     $locId AS locationId

MATCH (org:Organization)
  WHERE elementId(org) = organizationId

MATCH (loc:Location)
  WHERE elementId(loc) = locationId


MATCH (org)-[:HAS_ROLE]->(ri:RoleInstance)

// ---- Organization details
CALL (org) {
  WITH org
  WHERE org IS NOT NULL
  OPTIONAL CALL (org){
    OPTIONAL MATCH (org)-[:HAS_ORGANIZATION_CONTACT]->(contact:Contact)
    OPTIONAL MATCH (contact)-[:PERSON_IS]->(person:Person)
    OPTIONAL MATCH (contact)-[:ADDRESS_IS]->(address:Address)
    OPTIONAL MATCH (contact)-[:TELECOM_IS]->(telecom:Telecom)
    WITH contact, person, telecom, address
    RETURN COLLECT(
        DISTINCT {
          contact: contact,
          person: person,
          telecom: telecom,
          address: address
        }
      ) AS contacts
  }

  OPTIONAL CALL (org) {
    WITH org
    OPTIONAL MATCH (org)-[orgRel]->(id:Identifier)
    RETURN collect(DISTINCT { relType: type(orgRel  ), node: id }) AS identifiers
  }

  OPTIONAL CALL (org) {
    WITH org
    OPTIONAL MATCH (org)-[:HAS_QUALIFICATION]->(qual:Qualification)
    RETURN collect(DISTINCT qual) AS qualifications
  }
  RETURN {
    identifiers: identifiers,
    qualifications: qualifications,
    contacts: contacts
  } as orgDetails
}

// Location details
CALL (loc, ri){
  WITH loc, ri
  WHERE loc IS NOT NULL AND ri IS NOT NULL
  OPTIONAL CALL (ri) {
    WITH ri
    WHERE ri IS NOT NULL
      OPTIONAL MATCH (ri)-[:PERFORMED_AT]->(rl:RoleLocation)-[:LOCATION_IS]->(loc)
      RETURN DISTINCT rl
  }
  OPTIONAL CALL (loc) {
    WITH loc
    OPTIONAL MATCH (loc)-[locRel]->(id:Identifier)
    OPTIONAL MATCH (loc)-[:ACCESSIBLE]->(acc:Accessibility)
    RETURN 
      collect(DISTINCT { relType: type(locRel), node: id }) AS identifiers,
      collect(DISTINCT acc) AS accessibilities
  }
  
  OPTIONAL CALL (rl) {
    WITH rl
    WHERE rl IS NOT NULL
      OPTIONAL MATCH (rl)-[:HAS_LOCATION_CONTACT]->(contact:Contact)
      OPTIONAL MATCH (contact)-[:PERSON_IS]->(person:Person)
      OPTIONAL MATCH (contact)-[:ADDRESS_IS]->(address:Address)
      OPTIONAL MATCH (contact)-[:TELECOM_IS]->(telecom:Telecom)
      WITH contact, person, telecom, address
      RETURN COLLECT(
        DISTINCT CASE
          WHEN contact IS NOT NULL OR
               person IS NOT NULL OR
               telecom IS NOT NULL OR
               address IS NOT NULL
          THEN {
               contact: contact,
               person: person,
               telecom: telecom,
               address: address
          } ELSE null
        END
      ) AS contacts
  }
  RETURN {
    identifiers: identifiers,
    contacts: contacts,
    accessibilities: accessibilities
  } AS locDetails
}
RETURN {
          org: org,
          orgDetails: orgDetails
       } as orgInfo,
      {
          loc: loc,
          locDetails: locDetails
      } as locInfo
