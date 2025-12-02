MATCH (prac:Practitioner)
  WHERE prac.code = $pracCode
RETURN prac