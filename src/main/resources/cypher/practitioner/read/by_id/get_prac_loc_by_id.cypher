WITH $pracId AS practitionerId,
     $locId AS locationId

MATCH (prac:Practitioner)
  WHERE elementId(prac) = practitionerId

MATCH (loc:Location)
  WHERE elementId(loc) = locationId

MATCH (prac)-[:HAS_ROLE]->(ri:RoleInstance)

// ---- Practitioner details
CALL (prac, ri) {
  with prac, ri
  WHERE prac IS NOT NULL
  OPTIONAL CALL (prac, ri) {
    with prac, ri
    WHERE ri IS NOT NULL
    OPTIONAL MATCH (ri)-[:HAS_PRACTITIONER_CONTACT]->(contact:Contact)
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
  OPTIONAL CALL (prac, ri) {
    WITH prac
    OPTIONAL MATCH (prac)-[rel]->(id:Identifier)
    RETURN collect(DISTINCT { relType: type(rel), node: id }) AS identifiers
  }
  OPTIONAL CALL (prac) {
    WITH prac
    OPTIONAL MATCH (prac)-[:HAS_QUALIFICATION]->(qual:Qualification)
    RETURN collect(DISTINCT qual) AS qualifications
  }
  OPTIONAL CALL (ri) {
    WITH ri
    WHERE ri IS NOT NULL
    OPTIONAL MATCH (ri)-[:SPECIALIZES]->(spec:Specialty)
    WITH spec
    RETURN COLLECT(
      DISTINCT CASE
        WHEN spec IS NOT NULL
        THEN {
          specialty: spec
        } ELSE null
      END
      ) AS specialties
  }
  RETURN {
    identifiers: identifiers,
    qualifications: qualifications,
    contacts: contacts,
    specialties: specialties
  } as pracDetails
}
// Location details
CALL (loc, ri, net){
  WITH loc, ri, net
  WHERE loc IS NOT NULL
  OPTIONAL CALL (loc) {
    WITH loc
    OPTIONAL MATCH (loc)-[locRel]->(id:Identifier)
    OPTIONAL MATCH (loc)-[:ACCESSIBLE]->(acc:Accessibility)
    RETURN collect(DISTINCT { relType: type(locRel), node: id }) AS identifiers,
           collect(DISTINCT acc) AS accessibilities
  }
  OPTIONAL CALL (ri, net){
    WITH ri, net
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
    accessibilities: accessibilities,
    roleLocationServes:roleLocationServes
  } AS locDetails
}
RETURN {
          prac:prac,
          pracDetails: pracDetails
      } as pracInfo,
      {
          loc: loc,
          locDetails: locDetails
      } as locInfo
