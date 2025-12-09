WITH [
       {
         code:"7357",
         name: "MASS Medicare Advantage",
         product:{ code:"7356", name:"MA Medicare" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"7358",
         name: "MASS Dental Medicare Advantage",
         product:{ code:"7356", name:"MA Medicare" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"7359",
         name: "MASS Medicaid Network",
         product:{ code:"7355", name:"MA Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"7360",
         name: "MASS Dental Medicaid",
         product:{ code:"7355", name:"MA Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"7361",
         name: "MASS Exchange",
         product:{ code:"7354", name:"MA Federal Exchange" },
         lob:{ code:"EXCH", name:"Marketplace" }
       }
     ] AS networks
UNWIND networks AS net

// Create or reuse LOB
MERGE (lob:LOB { code: net.lob.code })
  ON CREATE SET lob.name = net.lob.name

// Create or reuse Product
MERGE (prod:Product { code: net.product.code })
  ON CREATE SET prod.name = net.product.name

// Create or reuse Network
MERGE (network:Network { code: net.code })
  ON CREATE SET network.name = net.name

// Relationships
MERGE (network)-[:PART_OF]->(prod)
MERGE (network)-[:PART_OF]->(lob);
