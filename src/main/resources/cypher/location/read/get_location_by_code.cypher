MATCH (loc:Location)
  WHERE loc.code = $locCode
RETURN loc