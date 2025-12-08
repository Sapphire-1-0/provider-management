WITH $pracId AS practitionerId

MATCH (prac:Practitioner)
  WHERE elementId(prac) = practitionerId

// ---- Practitioner identifiers & qualifications ----
OPTIONAL MATCH (prac)-[pracRel]->(id:Identifier)
OPTIONAL MATCH (prac)-[:HAS_QUALIFICATION]->(qual:Qualification)
RETURN
  prac,

// Practitioner-level identifiers
  collect(DISTINCT { relType: type(pracRel), node: id }) AS identifiers,

// Practitioner-level qualifications
  collect(DISTINCT qual) AS qualifications
