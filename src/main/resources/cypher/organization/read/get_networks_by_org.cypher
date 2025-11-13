MATCH (org:Organization)
  WHERE elementId(org) = $orgId

CALL {
WITH org
OPTIONAL MATCH (org)-[:HAS_ROLE]->(:RoleInstance)-[:SERVES]->(:RoleNetwork)-[:NETWORK_IS]->(net:Network)
RETURN collect(DISTINCT net) AS networks
}

CALL {
WITH org
OPTIONAL MATCH (org)-[r]->(identifier:Identifier)
RETURN collect(DISTINCT {relType: type(r), node: identifier}) AS identifiers
}

RETURN org, networks, identifiers

