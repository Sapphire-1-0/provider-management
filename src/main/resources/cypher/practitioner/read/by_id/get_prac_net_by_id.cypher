WITH $pracId AS practitionerId,
     $netId AS networkId

MATCH (prac:Practitioner)
  WHERE elementId(prac) = practitionerId

MATCH (net:Network)
  WHERE elementId(net) = networkId

MATCH (prac)-[:HAS_ROLE]->(ri:RoleInstance)

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
RETURN {
          prac:prac,
          pracDetails: pracDetails
      } as pracInfo,
      {
          net: net,
          netDetails: netDetails
      } as netInfo
