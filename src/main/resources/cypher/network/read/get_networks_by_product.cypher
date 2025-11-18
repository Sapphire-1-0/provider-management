MATCH (net:Network)-[:PART_OF]-(prod:Products)
where prod.code = $prodCode
RETURN net, collect(prod) as products
