MATCH (org:Organization)
  WHERE elementId(org) = $orgId
OPTIONAL MATCH (org)-[:HAS_ROLE]->(:RoleInstance)-[:PERFORMED_AT]->(:RoleLocation)-[:LOCATION_IS]->(loc:Location)
OPTIONAL MATCH (org)-[r]->(identifier:Identifier)
RETURN org,
       collect(DISTINCT loc) AS locations,
       collect({relType: type(r), node: identifier}) AS identifiers