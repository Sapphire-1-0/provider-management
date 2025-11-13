MATCH (o:Organization)-[r]->(id)
WHERE type(r) = $relType AND id.value = $value
RETURN o, collect({relType: type(r), node: id}) AS identifiers
