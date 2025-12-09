WITH [
       {
         code:"3856",
         name: "Opticare Alabama Medicaid",
         product:{ code:"2978", name:"Alabama Healthcare Advantage Inc.," },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"14744",
         name: "PremierEye Medicare Alabama - VAL",
         product:{ code:"14219", name:"Alabama Medicare PremierEye Vision" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"14176",
         name: "PremierEye Medicare Alabama",
         product:{ code:"14219", name:"Alabama Medicare PremierEye Vision" },
         lob:{ code:"MCR", name:"Medicare" }
       },
       {
         code:"3857",
         name: "Opticare Alabama Medicaid East",
         product:{ code:"3878", name:"Alabama Healthcare Advantage East" },
         lob:{ code:"MCD", name:"Medicaid" }
       },
       {
         code:"14151",
         name: "DentaQuest Medicare Alabama",
         product:{ code:"14217", name:"Alabama Medicare DentaQuest Dental" },
         lob:{ code:"MCR", name:"Medicare" }
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
