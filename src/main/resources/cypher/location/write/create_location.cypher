WITH $orgElementId AS orgId,
     $location AS locationMap

MATCH (org:Organization)
WHERE elementId(org) = orgId

CREATE (loc:Location)
SET loc += {
    code: locationMap.code,
    name: locationMap.name,
    streetAddress: locationMap.streetAddress,
    city: locationMap.city,
    state: locationMap.state,
    zip: locationMap.zip
}

MERGE (org)-[:HAS_ROLE]->(roleInstance:RoleInstance)

// ===============================
// Identifiers
// ===============================

// OSPHPD ID
WITH loc, org, roleInstance
UNWIND coalesce($oshpdIdList, []) AS oshpdIdMap
MERGE (oshpd:OSHPD_ID:Identifier {value: oshpdIdMap.value})
SET oshpd += {
  startDate: oshpdIdMap.startDate,
  endDate: oshpdIdMap.endDate
}
MERGE (loc)-[:HAS_OSHPD_ID]->(oshpd)

// ===============================
// Qualifications
// ===============================

with DISTINCT loc, org, roleInstance, coalesce($qualifications, []) AS qualifications
FOREACH (qualificationMap IN qualifications |
  CREATE (q:Qualification {
  type: qualificationMap.type,
  issuer: qualificationMap.issuer,
  startDate: qualificationMap.startDate,
  endDate: qualificationMap.endDate,
  level: qualificationMap.level,
  value: qualificationMap.value
})
  MERGE (loc)-[:HAS_QUALIFICATION]->(q)
)


CREATE (rl:RoleLocation)
CREATE (roleInstance)-[:PERFORMED_AT]->(rl)
CREATE (rl)-[:LOCATION_IS]->(loc)

RETURN loc