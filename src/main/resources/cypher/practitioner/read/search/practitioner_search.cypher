CALL db.index.fulltext.queryNodes('practitionerSearchIndex', 'Rebecca')
YIELD node AS prac, score
WHERE score > 1.0

WITH prac
  WHERE true

OPTIONAL MATCH (prac)-[r]->(id:Identifier)
RETURN DISTINCT prac, collect(DISTINCT {relType: type(r), node: id}) AS identifiers
  SKIP 0 LIMIT 10