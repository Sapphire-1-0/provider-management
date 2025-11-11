MATCH (org:Organization)
  WHERE elementId(org) = $orgId

OPTIONAL MATCH (org)-[:HAS_ROLE]->(ri:RoleInstance)
                 -[:SERVES]->(rn:RoleNetwork)
                 -[:NETWORK_IS]->(net:Network)
  WHERE elementId(net) = $netId

// RoleLocation and related relationships
OPTIONAL MATCH (rn)<-[rls:ROLE_LOCATION_SERVES]-(rl:RoleLocation)
                 -[:LOCATION_IS]->(loc:Location)

// One-to-one edge types (optional)
OPTIONAL MATCH (rn)<-[has_panel:HAS_PANEL]-(rl)
OPTIONAL MATCH (rn)<-[is_pcp:IS_PCP]-(rl)


// Collect all SERVES relationships for each RoleLocation
WITH org, net, loc, rl, has_panel, is_pcp,
     collect(DISTINCT rls) AS servesRels

// Now aggregate by location (each location has one rl)
WITH org, net, loc,
     collect(DISTINCT {
       servesRels: servesRels,
       hasPanelRel: has_panel,
       isPcpRel: is_pcp
     }) AS roleLocationData

// Org identifiers
CALL {
WITH org
OPTIONAL MATCH (org)-[r]->(identifier:Identifier)
RETURN collect(DISTINCT { relType: type(r), node: identifier }) AS identifiers
}

RETURN
  org,
  net,
  collect(DISTINCT {
    location: loc,
    roleLocationData: roleLocationData
  }) AS locations, identifiers
