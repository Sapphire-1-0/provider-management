WITH
  $pracId as practitionerId,
  $orgId as orgId
MATCH(prac:Practitioner) where elementId(prac) = practitionerId

OPTIONAL CALL (prac, orgId){
  WITH *
    WHERE prac IS NOT NULL AND orgId IS NOT NULL
    MATCH (org: Organization) where elementId(org) = orgId
    MATCH (prac)-[:HAS_ROLE]->(ri:RoleInstance) -[:CONTRACTED_BY]-> (org)

    OPTIONAL CALL (org) {
      WITH *
        WHERE org IS NOT NULL
        OPTIONAL MATCH(org)-[r]->(id:Identifier)
        RETURN collect({relType: type(r), node: id}) AS orgIdentifiers
    }
    RETURN DISTINCT org, orgIdentifiers, ri
  }

OPTIONAL CALL (prac) {
  WITH prac
    OPTIONAL MATCH(prac)-[r]->(id:Identifier)
    RETURN collect({relType: type(r), node: id}) AS identifiers
  }

RETURN prac, identifiers, org, orgIdentifiers