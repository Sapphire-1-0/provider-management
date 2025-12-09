WITH [
       {
         code:"2650",
         name: "US Script California Health and Wellness",
         product:{ code:"2644", name:"California Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"5296",
         name: "ASH California Health and Wellness",
         product:{ code:"2644", name:"California Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"8956",
         name: "MHN California Health and Wellness",
         product:{ code:"2644", name:"California Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"14347",
         name: "BioReference California Health and Wellness - VAL",
         product:{ code:"2644", name:"California Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"14593",
         name: "Opticare California Health and Wellness - VAL",
         product:{ code:"2644", name:"California Medicaid" },
         lob:{ code:"MCD", name:"Medicaid" }
       }
     ] AS networks
UNWIND networks AS net

// Create or reuse LOB
MERGE (lob:LOB { code: net.lob.code })
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
