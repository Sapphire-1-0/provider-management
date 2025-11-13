MATCH (prod:Product)<-[:PART_OF]-(net:Network)
RETURN net, collect(prod) AS products
