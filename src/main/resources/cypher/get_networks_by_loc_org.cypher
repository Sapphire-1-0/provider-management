MATCH (org:Organization)
  WHERE elementId(org) = $orgId

OPTIONAL MATCH (org)-[:HAS_ROLE]->(ri:RoleInstance)
                 -[:PERFORMED_AT]->(rl:RoleLocation)
                 -[:LOCATION_IS]->(loc:Location)
  WHERE elementId(loc) = $locId

// RoleLocation and related relationships
OPTIONAL MATCH (rl)-[rls:ROLE_LOCATION_SERVES]->(rn:RoleNetwork)
                 -[:NETWORK_IS]->(net:Network)

// One-to-one edge types (optional)
OPTIONAL MATCH (rl)-[has_panel:HAS_PANEL]->(rn)
OPTIONAL MATCH (rl)-[is_pcp:IS_PCP]->(rn)

// Org identifiers
OPTIONAL MATCH (org)-[r]->(identifier:Identifier)

// Collect all SERVES relationships for each RoleNetwork
WITH org, loc, identifier, r, net, rn, has_panel, is_pcp,
     collect(DISTINCT rls) AS servesRels

// Now aggregate by network (each network has one rn)
WITH org, loc, identifier, r, net,
     collect(DISTINCT {
       servesRels: servesRels,
       hasPanelRel: has_panel,
       isPcpRel: is_pcp
     }) AS roleNetworkData

RETURN
  org,
  loc,
  collect(DISTINCT {
    network: net,
    roleNetworkData: roleNetworkData
  }) AS networks,
  collect({ relType: type(r), node: identifier }) AS identifiers
