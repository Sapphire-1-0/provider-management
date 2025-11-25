// Step 1: Merge all identifiers
UNWIND $identifiers AS id

// Determine merge keys per type
WITH id,
     CASE
       WHEN id.type = 'MEDICAID_ID' THEN ['value', 'state']
       ELSE ['value']
       END AS mergeKeys,
     apoc.map.submap(id, mergeKeys) AS mergeProps

// Merge identifier node dynamically
CALL apoc.merge.node([id.type], mergeProps, {}, {}) YIELD node

// Prepare dynamic relationship type e.g. "HAS_TIN"
WITH id, node, "HAS_" + id.type AS relType

// Collect
WITH collect({node: node, relType: relType}) AS idList

// Step 2: Merge Org
MERGE (o:Organization { name: $name })
  ON CREATE SET o.createdDate = datetime()
SET o.updatedDate = datetime()

// Step 3: Create dynamic relationships
UNWIND idList AS item
CALL apoc.merge.relationship(
o,
item.relType,
{},    // relationship MERGE properties
{},    // on-create props
item.node
) YIELD rel

RETURN o, idList;
