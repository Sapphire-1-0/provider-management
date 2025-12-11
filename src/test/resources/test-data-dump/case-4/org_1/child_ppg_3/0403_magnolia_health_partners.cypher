// --- Match AL Networks ---
MATCH (net1:Network) WHERE net1.code = "3856"
MATCH (net2:Network) WHERE net2.code = "14744"
MATCH (net3:Network) WHERE net3.code = "14176"
MATCH (net4:Network) WHERE net4.code = "3857"
MATCH (net5:Network) WHERE net5.code = "14151"

// --- Organization ---
CREATE (org:Organization {
  name: "Magnolia Health Partners",
  aliasName: "Magnolia Partners",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "MHPAL9988776655"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value:"4987654321", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value:"420008"})
CREATE (tin:Identifier:TIN {value:"565656565", legalName:"Magnolia Health Partners Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value:"5000202", state:"AL", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value:"M6789012", startDate:date("2019-01-01"), endDate:date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value:"PPG00012"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC8899", state:"AL", startDate:date("2021-01-01"), endDate:date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate:date("2021-01-01"), endDate:date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate:date("2021-01-01"), endDate:date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"ADMIN"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {
  streetAddress:"1500 Magnolia Ave",
  secondaryAddress:"Suite 310",
  city:"Huntsville",
  state:"AL",
  zipCode:"35801",
  county:"Madison",
  countyFIPS:"01089"
})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {
  phone:"256-555-3000",
  tty:"256-555-3001",
  fax:"256-555-3002",
  email:"admin@magnoliapartners.com",
  website:"https://www.magnoliapartners.com"
})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Ellen", middleName:"R", lastName:"Woodard", title:"Administrator"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)

// --- Role Networks ---
CREATE (rn1:RoleNetwork)
CREATE (role)-[:SERVES]->(rn1)
CREATE (rn1)-[:NETWORK_IS]->(net1)

CREATE (rn2:RoleNetwork)
CREATE (role)-[:SERVES]->(rn2)
CREATE (rn2)-[:NETWORK_IS]->(net2)

CREATE (rn3:RoleNetwork)
CREATE (role)-[:SERVES]->(rn3)
CREATE (rn3)-[:NETWORK_IS]->(net3)

CREATE (rn4:RoleNetwork)
CREATE (role)-[:SERVES]->(rn4)
CREATE (rn4)-[:NETWORK_IS]->(net4)

CREATE (rn5:RoleNetwork)
CREATE (role)-[:SERVES]->(rn5)
CREATE (rn5)-[:NETWORK_IS]->(net5)

// -------------------------------------------------------------------
// ------------------------- LOCATIONS 1–10 ---------------------------
// -------------------------------------------------------------------

// --- Location 1 (Mobile) ---
CREATE (loc1:Location {
  name:"Magnolia Clinic Mobile",
  code:"MHPLOC000AL001",
  streetAddress:"240 Dauphin St",
  secondaryAddress:"Suite 120",
  city:"Mobile",
  state:"AL",
  zipCode:"36602",
  county:"Mobile",
  countyFIPS:"01097"
})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn5)

CREATE (acc1:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"251-111101", tty:"251-111102", fax:"251-111103", email:"MHPLOC000AL001@example.com", website:"https://www.MHPLOC000AL001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Derrick", middleName:"M", lastName:"Fowler", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:70})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:95})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:130})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --------------------------
// Locations 2–10
// --------------------------

WITH org, role, rn1, rn2, rn3, rn4, rn5
// --- Location 2 (Montgomery) ---
CREATE (loc2:Location {
  name:"Magnolia Clinic Montgomery",
  code:"MHPLOC000AL002",
  streetAddress:"600 Union St",
  secondaryAddress:"Suite 300",
  city:"Montgomery",
  state:"AL",
  zipCode:"36104",
  county:"Montgomery",
  countyFIPS:"01101"
})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)
CREATE (loc2)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true, adaInteriorBldg:true})
CREATE (loc2)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL002"})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:60})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:90})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:120})

// --- Location 3 (Tuscaloosa) ---
CREATE (loc3:Location {
  name:"Magnolia Clinic Tuscaloosa",
  code:"MHPLOC000AL003",
  streetAddress:"900 Queen City Ave",
  secondaryAddress:"Suite 130",
  city:"Tuscaloosa",
  state:"AL",
  zipCode:"35401",
  county:"Tuscaloosa",
  countyFIPS:"01125"
})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (loc3)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc3)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL003"})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:50})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:80})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:110})

// --- Location 4 (Auburn) ---
CREATE (loc4:Location {
  name:"Magnolia Clinic Auburn",
  code:"MHPLOC000AL004",
  streetAddress:"145 College St",
  secondaryAddress:"Suite 220",
  city:"Auburn",
  state:"AL",
  zipCode:"36830",
  county:"Lee",
  countyFIPS:"01081"
})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn2)
CREATE (loc4)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc4)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL004"})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:55})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:85})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:105})

// --- Location 5 (Dothan) ---
CREATE (loc5:Location {
  name:"Magnolia Clinic Dothan",
  code:"MHPLOC000AL005",
  streetAddress:"300 Main St",
  secondaryAddress:"Suite 400",
  city:"Dothan",
  state:"AL",
  zipCode:"36301",
  county:"Houston",
  countyFIPS:"01069"
})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn3)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn5)
CREATE (loc5)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc5)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL005"})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:65})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:92})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:118})

// ------------------
// Location 6–10
// ------------------

CREATE (loc6:Location {
  name:"Magnolia Clinic Hoover",
  code:"MHPLOC000AL006",
  streetAddress:"2100 Riverchase Pkwy",
  secondaryAddress:"Suite 310",
  city:"Hoover",
  state:"AL",
  zipCode:"35244",
  county:"Jefferson",
  countyFIPS:"01073"
})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn1)
CREATE (loc6)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc6)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL006"})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:48})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:82})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:104})

CREATE (loc7:Location {
  name:"Magnolia Clinic Florence",
  code:"MHPLOC000AL007",
  streetAddress:"410 Cox Creek Pkwy",
  secondaryAddress:"Suite 125",
  city:"Florence",
  state:"AL",
  zipCode:"35630",
  county:"Lauderdale",
  countyFIPS:"01077"
})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn2)
CREATE (loc7)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc7)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL007"})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:58})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:88})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:112})

CREATE (loc8:Location {
  name:"Magnolia Clinic Gadsden",
  code:"MHPLOC000AL008",
  streetAddress:"500 Rainbow Dr",
  secondaryAddress:"Suite 510",
  city:"Gadsden",
  state:"AL",
  zipCode:"35901",
  county:"Etowah",
  countyFIPS:"01055"
})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn3)
CREATE (loc8)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc8)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL008"})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:62})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:90})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:125})

CREATE (loc9:Location {
  name:"Magnolia Clinic Decatur",
  code:"MHPLOC000AL009",
  streetAddress:"290 Beltline Rd",
  secondaryAddress:"Suite 220",
  city:"Decatur",
  state:"AL",
  zipCode:"35601",
  county:"Morgan",
  countyFIPS:"01103"
})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn4)
CREATE (loc9)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc9)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL009"})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:54})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:83})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:102})

CREATE (loc10:Location {
  name:"Magnolia Clinic Madison",
  code:"MHPLOC000AL010",
  streetAddress:"100 Hughes Rd",
  secondaryAddress:"Suite 140",
  city:"Madison",
  state:"AL",
  zipCode:"35758",
  county:"Madison",
  countyFIPS:"01089"
})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"),endDate:date("4000-01-01")}]->(rn5)
CREATE (loc10)-[:ACCESSIBLE]->(:Accessibility {adaBasicAccess:true})
CREATE (loc10)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MHPLOC000AL010"})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:66})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:94})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:128})
