MATCH (o:Organization)-[r]->(id)
  WHERE o.code = $orgCode
RETURN o