// --- Match AL Networks ---
MATCH (net1:Network) WHERE net1.code = "3856"
MATCH (net2:Network) WHERE net2.code = "14744"
MATCH (net3:Network) WHERE net3.code = "14176"
MATCH (net4:Network) WHERE net4.code = "3857"
MATCH (net5:Network) WHERE net5.code = "14151"

// --- Organization ---
CREATE (org:Organization {
  name: "Riverbend General Hospital",
  aliasName: "Riverbend Hospital",
  type: "HOSPITAL",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "RBHOSPAL449922"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value:"5678901234", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
CREATE (tin:Identifier:TIN {value:"606060606", legalName:"Riverbend General Hospital Legal Entity"})
CREATE (medicare:Identifier:MedicareID {value:"990321"})
CREATE (medicaid:Identifier:MedicaidID {value:"7711203", state:"AL", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value:"R9012345", startDate:date("2019-01-01"), endDate:date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value:"PPG00025"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"HOSP_LIC", value:"HOSP9988", state:"AL", status:"ACTIVE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_JCAHO", issuer:"Joint Commission", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)

// --- Organization Contact ---
CREATE (contact:Contact {use:"ADMIN"})
CREATE (org)-[:HAS_ORGANIZATION_CONTACT]->(contact)

CREATE (addr:Address {
  streetAddress:"500 Riverbend Dr",
  secondaryAddress:"Suite 500",
  city:"Birmingham",
  state:"AL",
  zipCode:"35203",
  county:"Jefferson",
  countyFIPS:"01073"
})
CREATE (contact)-[:ADDRESS_IS]->(addr)

CREATE (tele:Telecom {
  phone:"205-555-8800",
  tty:"205-555-8801",
  fax:"205-555-8802",
  email:"admin@riverbendhospital.com",
  website:"https://www.riverbendhospital.com"
})
CREATE (contact)-[:TELECOM_IS]->(tele)

CREATE (p:Person {firstName:"Linda", middleName:"M", lastName:"Carpenter", title:"Hospital Director"})
CREATE (contact)-[:PERSON_IS]->(p)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)

// --- Role Networks ---
CREATE (rn1:RoleNetwork) CREATE (role)-[:SERVES]->(rn1) CREATE (rn1)-[:NETWORK_IS]->(net1)
CREATE (rn2:RoleNetwork) CREATE (role)-[:SERVES]->(rn2) CREATE (rn2)-[:NETWORK_IS]->(net2)
CREATE (rn3:RoleNetwork) CREATE (role)-[:SERVES]->(rn3) CREATE (rn3)-[:NETWORK_IS]->(net3)
CREATE (rn4:RoleNetwork) CREATE (role)-[:SERVES]->(rn4) CREATE (rn4)-[:NETWORK_IS]->(net4)
CREATE (rn5:RoleNetwork) CREATE (role)-[:SERVES]->(rn5) CREATE (rn5)-[:NETWORK_IS]->(net5)

// -----------------------------------------------------------
// --------------------------- LOCATIONS ----------------------
// -----------------------------------------------------------

// --- Location 1 ---
CREATE (loc1:Location {
  name:"Riverbend Hospital Main Campus",
  code:"RBHLOC000AL001",
  streetAddress:"500 Riverbend Dr",
  secondaryAddress:"Building A",
  city:"Birmingham",
  state:"AL",
  zipCode:"35203",
  county:"Jefferson",
  countyFIPS:"01073"
})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)

CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc1:Accessibility {adaBasicAccess:true, adaInteriorBldg:true, adaPatientDiagnostic:true})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)

CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:180})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:230})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:260})

// --- Location 1 Contact ---
CREATE (c1:Contact {use:"DIR"})
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)
CREATE (t1:Telecom {
  phone:"205-888-1101",
  tty:"205-888-1102",
  fax:"205-888-1103",
  email:"RBHLOC000AL001@example.com",
  website:"https://www.RBHLOC000AL001.com"
})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Thomas", middleName:"G", lastName:"Pickett", title:"Campus Director"})
CREATE (c1)-[:PERSON_IS]->(p1)

// -------------------------------------------------------------------
// ------------------------------ LOC 2–10 ----------------------------
// -------------------------------------------------------------------

WITH org, role, rn1, rn2, rn3, rn4, rn5

// --- Location 2 ---
CREATE (loc2:Location {
  name:"Riverbend Hospital South Clinic",
  code:"RBHLOC000AL002",
  streetAddress:"200 Southlake Park",
  secondaryAddress:"Suite 150",
  city:"Hoover",
  state:"AL",
  zipCode:"35244",
  county:"Shelby",
  countyFIPS:"01117"
})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (loc2)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc2)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL002"})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:90})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})

// --- Location 3 ---
CREATE (loc3:Location {
  name:"Riverbend Hospital North Clinic",
  code:"RBHLOC000AL003",
  streetAddress:"3100 University Dr NW",
  secondaryAddress:"Suite 210",
  city:"Huntsville",
  state:"AL",
  zipCode:"35816",
  county:"Madison",
  countyFIPS:"01089"
})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (loc3)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc3)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL003"})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:75})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:110})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:135})

// --- Location 4 ---
CREATE (loc4:Location {
  name:"Riverbend Hospital East Clinic",
  code:"RBHLOC000AL004",
  streetAddress:"1500 Cobbs Ford Rd",
  secondaryAddress:"Suite 120",
  city:"Prattville",
  state:"AL",
  zipCode:"36066",
  county:"Autauga",
  countyFIPS:"01001"
})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (loc4)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc4)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL004"})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:82})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:115})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:145})

// --- Location 5 ---
CREATE (loc5:Location {
  name:"Riverbend Hospital West Clinic",
  code:"RBHLOC000AL005",
  streetAddress:"2101 11th Ave S",
  secondaryAddress:"Suite 300",
  city:"Birmingham",
  state:"AL",
  zipCode:"35205",
  county:"Jefferson",
  countyFIPS:"01073"
})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn4)
CREATE (loc5)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc5)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL005"})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:78})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:108})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:138})

// --- Location 6 ---
CREATE (loc6:Location {
  name:"Riverbend Hospital Tuscaloosa Clinic",
  code:"RBHLOC000AL006",
  streetAddress:"1200 McFarland Blvd E",
  secondaryAddress:"Suite 410",
  city:"Tuscaloosa",
  state:"AL",
  zipCode:"35404",
  county:"Tuscaloosa",
  countyFIPS:"01125"
})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn5)
CREATE (loc6)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc6)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL006"})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:70})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:100})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:130})

// --- Location 7 ---
CREATE (loc7:Location {
  name:"Riverbend Hospital Foley Clinic",
  code:"RBHLOC000AL007",
  streetAddress:"2500 S McKenzie St",
  secondaryAddress:"Suite 260",
  city:"Foley",
  state:"AL",
  zipCode:"36535",
  county:"Baldwin",
  countyFIPS:"01003"
})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn3)
CREATE (loc7)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc7)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL007"})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:58})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:92})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:118})

// --- Location 8 ---
CREATE (loc8:Location {
  name:"Riverbend Hospital Gadsden Clinic",
  code:"RBHLOC000AL008",
  streetAddress:"400 E Meighan Blvd",
  secondaryAddress:"Suite 250",
  city:"Gadsden",
  state:"AL",
  zipCode:"35903",
  county:"Etowah",
  countyFIPS:"01055"
})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn2)
CREATE (loc8)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc8)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL008"})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:66})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:96})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:128})

// --- Location 9 ---
CREATE (loc9:Location {
  name:"Riverbend Hospital Florence Clinic",
  code:"RBHLOC000AL009",
  streetAddress:"1800 Darby Dr",
  secondaryAddress:"Suite 300",
  city:"Florence",
  state:"AL",
  zipCode:"35630",
  county:"Lauderdale",
  countyFIPS:"01077"
})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn4)
CREATE (loc9)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc9)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL009"})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:64})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:95})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:120})

// --- Location 10 ---
CREATE (loc10:Location {
  name:"Riverbend Hospital Opelika Clinic",
  code:"RBHLOC000AL010",
  streetAddress:"1900 Pepperell Pkwy",
  secondaryAddress:"Suite 410",
  city:"Opelika",
  state:"AL",
  zipCode:"36801",
  county:"Lee",
  countyFIPS:"01081"
})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn5)
CREATE (loc10)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc10)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_RBHLOC000AL010"})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:68})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:98})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:132})
