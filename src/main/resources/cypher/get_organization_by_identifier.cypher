MATCH (o:Organization)-[r]->(i)
WHERE type(r) = $relType AND i.value = $value
RETURN o, collect({relType: type(r), node: i}) AS identifiers
