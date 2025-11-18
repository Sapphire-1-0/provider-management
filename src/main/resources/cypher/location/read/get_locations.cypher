MATCH (loc:Location)
  WHERE ($name IS NULL OR toLower(loc.name)  CONTAINS toLower($name))
  AND ($city IS NULL OR toLower(loc.city)  CONTAINS toLower($city))
  AND ($state IS NULL OR toLower(loc.state) CONTAINS toLower($state))
RETURN loc;
