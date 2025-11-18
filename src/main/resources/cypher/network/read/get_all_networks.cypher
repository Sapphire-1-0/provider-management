MATCH (prod:Product)<-[:PART_OF]-(net:Network)
  WHERE ($networkCode IS NULL OR net.code = $networkCode)
  AND ($networkName IS NULL OR net.name = $networkName)
  AND ($productCode IS NULL OR prod.code = $productCode)
RETURN net, collect(prod) AS products

