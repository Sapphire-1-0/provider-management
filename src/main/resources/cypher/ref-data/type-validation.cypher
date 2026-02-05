UNWIND keys($referenceData) AS label
UNWIND $referenceData[label] AS code

CALL apoc.cypher.run(
'
MATCH (n)
WHERE $label IN labels(n)
  AND n.code = $code
RETURN count(n) > 0 AS exists
',
{label: label, code: code}
) YIELD value

RETURN
  label,
  code,
  value.exists AS exists
  ORDER BY label, code;
