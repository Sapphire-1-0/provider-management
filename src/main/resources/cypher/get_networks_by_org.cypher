MATCH (org:Organization)
OPTIONAL MATCH (org)-[:HAS_ROLE]->(:RoleInstance)-[:SERVES]->(:RoleNetwork)-[:NETWORK_IS]->(net:Network)
OPTIONAL MATCH (org)-[r]->(identifier)
  WHERE elementId(org) = $orgId
RETURN org,
       collect(DISTINCT net) AS networks,
       collect({relType: type(r), node: i}) AS identifiers
