MATCH (o:Organization)
OPTIONAL MATCH (o)-[r]->(i)
  WHERE type(r) IN ['HAS_NPI', 'HAS_TIN', 'HAS_MEDICARE_ID', 'HAS_MEDICAID_ID']
RETURN o, collect({relType: type(r), node: i}) AS identifiers
