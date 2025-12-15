WITH [
       {
         code:"2432",
         name: "Medicare GA",
         product:{ code:"2430", name:"Peach State Health Plan Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"1017",
         name: "CareCentrix Medicare GA",
         product:{ code:"2430", name:"Peach State Health Plan Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"6556",
         name: "NIA Medicare GA",
         product:{ code:"2430", name:"Peach State Health Plan Advantage" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"47",
         name: "Medicaid GA",
         product:{ code:"37", name:"Peach State Healthplan" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"9317",
         name: "Envolve Rx GA Medicaid",
         product:{ code:"37", name:"Peach State Healthplan" },
         lob:{ code:"MCD", name:"Medicaid" }
       }
     ] AS networks
UNWIND networks AS net

// Create or reuse LOB
MERGE (lob:LineOfBusiness { code: net.lob.code })
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
