WITH $orgId AS organizationId,
     $locId AS locationId,
     $netId AS networkId

MATCH (org:Organization)
  WHERE elementId(org) = organizationId

MATCH (loc:Location)
  WHERE elementId(loc) = locationId

MATCH (net:Network)
  WHERE elementId(net) = networkId

MATCH (org)-[:HAS_ROLE]->(ri:RoleInstance)

// Network Details
CALL (net) {
  WITH net
  OPTIONAL MATCH (net)-[:PART_OF]->(prod:Product)
  OPTIONAL MATCH (net)-[:PART_OF]->(lob:LineOfBusiness)
  return {
         products: COLLECT(DISTINCT prod),
         lobs: COLLECT(DISTINCT lob)
        } as netDetails
}

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
CALL (loc, ri, net){
  WITH loc, ri, net
  WHERE loc IS NOT NULL
  OPTIONAL CALL (loc) {
    WITH loc
    OPTIONAL MATCH (loc)-[locRel]->(id:Identifier)
    RETURN collect(DISTINCT { relType: type(locRel), node: id }) AS identifiers
  }
  OPTIONAL CALL (ri, net){
    WITH ri, net
    WHERE ri IS NOT NULL AND net IS NOT NULL
    OPTIONAL MATCH (ri)-[:PERFORMED_AT]->(rl:RoleLocation)-[LOCATION_IS]->(loc)
    OPTIONAL MATCH (rl)-[rls:ROLE_LOCATION_SERVES]->(rn:RoleNetwork)-[:NETWORK_IS]->(net)
    RETURN rl, collect(DISTINCT rls) as roleLocationServes
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
    roleLocationServes:roleLocationServes
  } AS locDetails
}
RETURN {
          org: org,
          orgDetails: orgDetails
       } as orgInfo,
      {
          loc: loc,
          locDetails: locDetails
      } as locInfo,
      {
          net: net,
          netDetails: netDetails
      } as netInfo
