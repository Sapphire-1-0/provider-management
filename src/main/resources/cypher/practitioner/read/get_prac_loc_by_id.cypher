WITH $pracId AS practitionerId,
     $locId AS locationId

MATCH (prac:Practitioner)
  WHERE elementId(prac) = practitionerId

MATCH (loc:Location)
  WHERE elementId(loc) = locationId

MATCH (prac)-[:HAS_ROLE]->(ri:RoleInstance)-[:PERFORMED_AT]->(rl:RoleLocation)-[LOCATION_IS]->(loc)


CALL  {
WITH rl
OPTIONAL MATCH (rl)-[:HAS_LOCATION_CONTACT]->(contact:Contact) WHERE contact.use = "DIR"
OPTIONAL MATCH (contact)-[:TELECOM_IS]->(tel:Telecom)
OPTIONAL MATCH (contact)-[:PERSON_IS]->(person: Person)
return contact, tel, person
}


// ---- Practitioner identifiers & qualifications ----
OPTIONAL MATCH (prac)-[pracRel]->(id:Identifier)
OPTIONAL MATCH (prac)-[:HAS_QUALIFICATION]->(qual:Qualification)
RETURN
  prac,

// Practitioner-level identifiers
  collect(DISTINCT { relType: type(pracRel), node: id }) AS pracIdentifiers,

// Practitioner-level qualifications
  collect(DISTINCT qual) AS pracQualifications,

  loc, COLLECT(DISTINCT {contact: contact, telecom: tel, person: person}) AS locContacts
