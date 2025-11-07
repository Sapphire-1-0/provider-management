MATCH (org:Organization)
  WHERE elementId(org) = $orgId
OPTIONAL MATCH (org)-[:HAS_ROLE]->(:RoleInstance)-[:SERVES]->(:RoleNetwork)-[:NETWORK_IS]->(net:Network)
OPTIONAL MATCH (org)-[r]->(identifier:Identifier)
RETURN org,
       collect(DISTINCT net) AS networks,
       collect({relType: type(r), node: identifier}) AS identifiers
