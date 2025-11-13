MATCH (org:Organization)
  WHERE elementId(org) = $orgId

CALL {
WITH org
OPTIONAL MATCH (org)-[:HAS_ROLE]->(:RoleInstance)-[:PERFORMED_AT]->(:RoleLocation)-[:LOCATION_IS]->(loc:Location)
RETURN collect(DISTINCT loc) AS locations
}

CALL {
WITH org
OPTIONAL MATCH (org)-[r]->(identifier:Identifier)
RETURN collect(DISTINCT {relType: type(r), node: identifier}) AS identifiers
}

RETURN org, locations, identifiers