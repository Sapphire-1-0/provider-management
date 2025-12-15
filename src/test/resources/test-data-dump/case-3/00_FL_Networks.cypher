WITH [
       {
         code:"1836",
         name: "Dental Health and Wellness FL-Medicare",
         product:{ code:"378", name:"Sunshine Health Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"379",
         name: "Medicare FL",
         product:{ code:"378", name:"Sunshine Health Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"2897",
         name: "BIOREF Medicare FL",
         product:{ code:"378", name:"Sunshine Health Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"803",
         name: "TMS Transportation Medicare FL",
         product:{ code:"378", name:"Sunshine Health Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"801",
         name: "MCNA Medicare FL",
         product:{ code:"378", name:"Sunshine Health Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       }
     ] AS networks
UNWIND networks AS net

// Create or reuse LOB
MERGE (lob:LineOfBusiness { code: net.lob.code })
  ON CREATE SET lob.name = net.lob.name

// Create or reuse Product
MERGE (prod:Product { code: net.product.code })
  ON CREATE SET prod.name = net.product.name

// Create Network
MERGE (network:Network { code: net.code })
  ON CREATE SET network.name = net.name

// Relationships
MERGE (network)-[:PART_OF]->(prod)
MERGE (network)-[:PART_OF]->(lob);
