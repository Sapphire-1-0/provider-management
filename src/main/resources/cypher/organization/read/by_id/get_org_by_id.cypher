WITH $orgId AS organizationId

MATCH (org:Organization)
  WHERE elementId(org) = organizationId

// ---- Organization identifiers & qualifications ----
OPTIONAL MATCH (org)-[rel]->(id:Identifier)
OPTIONAL MATCH (org)-[:HAS_QUALIFICATION]->(qual:Qualification)
RETURN
  org,

// Organization-level identifiers
  collect(DISTINCT { relType: type(rel), node: id }) AS identifiers,

// Organization-level qualifications
  collect(DISTINCT qual) AS qualifications
