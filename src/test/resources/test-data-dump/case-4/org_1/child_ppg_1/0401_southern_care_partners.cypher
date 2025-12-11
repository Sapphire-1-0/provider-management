// --- Match AL Networks ---
MATCH (net1:Network) WHERE net1.code = "3856"
MATCH (net2:Network) WHERE net2.code = "14744"
MATCH (net3:Network) WHERE net3.code = "14176"
MATCH (net4:Network) WHERE net4.code = "3857"
MATCH (net5:Network) WHERE net5.code = "14151"

// --- Organization ---
CREATE (org:Organization {
  name: "Southern Care Partners",
  aliasName: "Southern Partners",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "SCPAL9876543210"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "4890123456", startDate: date("2021-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "410007"})
CREATE (tin:Identifier:TIN {value: "555555555", legalName: "Southern Care Partners Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "5000101", state: "AL", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D5678901", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00008"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC7890", state:"AL", startDate: date("2021-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2021-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2021-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"500 Southern Pkwy", secondaryAddress:"Suite 200", city:"Montgomery", state:"AL", zipCode:"36104", county:"Montgomery", countyFIPS:"01101"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"334-555-2200", tty:"334-555-2201", fax:"334-555-2202", email:"billing@southernpartners.com", website:"https://www.southernpartners.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Margaret", middleName:"N", lastName:"Hughes", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)

// --- Create Role Networks ---
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

// --- Location 1: Birmingham ---
CREATE (loc1:Location {name:"Southern Care Clinic 1", code:"SCPLOC000AL001", streetAddress:"100 Lakeshore Dr", secondaryAddress:"Suite 101", city:"Birmingham", state:"AL", zipCode:"35209", county:"Jefferson", countyFIPS:"01073"})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc1:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"205-111101", tty:"205-111102", fax:"205-111103", email:"SCPLOC000AL001@example.com", website:"https://www.SCPLOC000AL001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Caroline", middleName:"A", lastName:"Reeves", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:75})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:110})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 2: Mobile ---
CREATE (loc2:Location {
  name:"Southern Care Clinic 2",
  code:"SCPLOC000AL002",
  streetAddress:"2400 Dauphin St",
  secondaryAddress:"Suite 300",
  city:"Mobile",
  state:"AL",
  zipCode:"36606",
  county:"Mobile",
  countyFIPS:"01097"
})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc2:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)

CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {
  phone:"251-555-1201", tty:"251-555-1202", fax:"251-555-1203",
  email:"SCPLOC000AL002@example.com", website:"https://www.SCPLOC000AL002.com"
})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Daniel", middleName:"E", lastName:"Porter", title:"Director"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL002"})
CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2)

CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:65})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:95})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:130})

CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 3: Huntsville ---
CREATE (loc3:Location {
  name:"Southern Care Clinic 3",
  code:"SCPLOC000AL003",
  streetAddress:"1500 Sparkman Dr NW",
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
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)

CREATE (acc3:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)

CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {
  phone:"256-555-1301", tty:"256-555-1302", fax:"256-555-1303",
  email:"SCPLOC000AL003@example.com", website:"https://www.SCPLOC000AL003.com"
})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (p3:Person {firstName:"Linda", middleName:"K", lastName:"Carter", title:"Director"})
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)

CREATE (osh3:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL003"})
CREATE (loc3)-[:HAS_OSHPD_ID]->(osh3)

CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:70})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:100})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:140})

CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 4: Tuscaloosa ---
CREATE (loc4:Location {
  name:"Southern Care Clinic 4",
  code:"SCPLOC000AL004",
  streetAddress:"620 Paul W Bryant Dr",
  secondaryAddress:"Suite 115",
  city:"Tuscaloosa",
  state:"AL",
  zipCode:"35401",
  county:"Tuscaloosa",
  countyFIPS:"01125"
})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc4:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)

CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {
  phone:"205-555-1401", tty:"205-555-1402", fax:"205-555-1403",
  email:"SCPLOC000AL004@example.com", website:"https://www.SCPLOC000AL004.com"
})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (p4:Person {firstName:"Raymond", middleName:"L", lastName:"Brewer", title:"Director"})
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)

CREATE (osh4:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL004"})
CREATE (loc4)-[:HAS_OSHPD_ID]->(osh4)

CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:60})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:105})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:145})

CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 5: Hoover ---
CREATE (loc5:Location {
  name:"Southern Care Clinic 5",
  code:"SCPLOC000AL005",
  streetAddress:"4900 Valleydale Rd",
  secondaryAddress:"Suite 400",
  city:"Hoover",
  state:"AL",
  zipCode:"35244",
  county:"Shelby",
  countyFIPS:"01117"
})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)

CREATE (acc5:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)

CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {
  phone:"205-555-1501", tty:"205-555-1502", fax:"205-555-1503",
  email:"SCPLOC000AL005@example.com", website:"https://www.SCPLOC000AL005.com"
})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (p5:Person {firstName:"Elizabeth", middleName:"J", lastName:"Coleman", title:"Director"})
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)

CREATE (osh5:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL005"})
CREATE (loc5)-[:HAS_OSHPD_ID]->(osh5)

CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:55})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:85})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:120})

CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 6: Auburn ---
CREATE (loc6:Location {
  name:"Southern Care Clinic 6",
  code:"SCPLOC000AL006",
  streetAddress:"2100 East University Dr",
  secondaryAddress:"Suite 150",
  city:"Auburn",
  state:"AL",
  zipCode:"36830",
  county:"Lee",
  countyFIPS:"01081"
})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc6:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc6)-[:ACCESSIBLE]->(acc6)

CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {
  phone:"334-555-1601", tty:"334-555-1602", fax:"334-555-1603",
  email:"SCPLOC000AL006@example.com", website:"https://www.SCPLOC000AL006.com"
})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (p6:Person {firstName:"Harold", middleName:"T", lastName:"Jennings", title:"Director"})
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)

CREATE (osh6:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL006"})
CREATE (loc6)-[:HAS_OSHPD_ID]->(osh6)

CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:50})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:75})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:110})

CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 7: Dothan ---
CREATE (loc7:Location {
  name:"Southern Care Clinic 7",
  code:"SCPLOC000AL007",
  streetAddress:"310 Healthwest Dr",
  secondaryAddress:"Suite 210",
  city:"Dothan",
  state:"AL",
  zipCode:"36303",
  county:"Houston",
  countyFIPS:"01069"
})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc7:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc7)-[:ACCESSIBLE]->(acc7)

CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {
  phone:"334-555-1701", tty:"334-555-1702", fax:"334-555-1703",
  email:"SCPLOC000AL007@example.com", website:"https://www.SCPLOC000AL007.com"
})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (p7:Person {firstName:"Sarah", middleName:"P", lastName:"Whitney", title:"Director"})
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)

CREATE (osh7:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL007"})
CREATE (loc7)-[:HAS_OSHPD_ID]->(osh7)

CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:55})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:80})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:125})

CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 8: Florence ---
CREATE (loc8:Location {
  name:"Southern Care Clinic 8",
  code:"SCPLOC000AL008",
  streetAddress:"420 Cox Creek Pkwy",
  secondaryAddress:"Suite 120",
  city:"Florence",
  state:"AL",
  zipCode:"35630",
  county:"Lauderdale",
  countyFIPS:"01077"
})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)

CREATE (acc8:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc8)-[:ACCESSIBLE]->(acc8)

CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {
  phone:"256-555-1801", tty:"256-555-1802", fax:"256-555-1803",
  email:"SCPLOC000AL008@example.com", website:"https://www.SCPLOC000AL008.com"
})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (p8:Person {firstName:"Jeffrey", middleName:"B", lastName:"Sanders", title:"Director"})
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)

CREATE (osh8:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL008"})
CREATE (loc8)-[:HAS_OSHPD_ID]->(osh8)

CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:65})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:90})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:135})

CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 9: Gadsden ---
CREATE (loc9:Location {
  name:"Southern Care Clinic 9",
  code:"SCPLOC000AL009",
  streetAddress:"120 East Meighan Blvd",
  secondaryAddress:"Suite 130",
  city:"Gadsden",
  state:"AL",
  zipCode:"35903",
  county:"Etowah",
  countyFIPS:"01055"
})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc9:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc9)-[:ACCESSIBLE]->(acc9)

CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {
  phone:"256-555-1901", tty:"256-555-1902", fax:"256-555-1903",
  email:"SCPLOC000AL009@example.com", website:"https://www.SCPLOC000AL009.com"
})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (p9:Person {firstName:"Nancy", middleName:"R", lastName:"Foster", title:"Director"})
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)

CREATE (osh9:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL009"})
CREATE (loc9)-[:HAS_OSHPD_ID]->(osh9)

CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:58})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:82})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:118})

CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


// --- Location 10: Decatur ---
CREATE (loc10:Location {
  name:"Southern Care Clinic 10",
  code:"SCPLOC000AL010",
  streetAddress:"2600 Danville Rd SW",
  secondaryAddress:"Suite 250",
  city:"Decatur",
  state:"AL",
  zipCode:"35601",
  county:"Morgan",
  countyFIPS:"01103"
})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn4)

CREATE (acc10:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc10)-[:ACCESSIBLE]->(acc10)

CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {
  phone:"256-555-2001", tty:"256-555-2002", fax:"256-555-2003",
  email:"SCPLOC000AL010@example.com", website:"https://www.SCPLOC000AL010.com"
})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (p10:Person {firstName:"Brian", middleName:"C", lastName:"Tucker", title:"Director"})
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)

CREATE (osh10:Identifier:OSHPD_ID {value:"OSHPD_SCPLOC000AL010"})
CREATE (loc10)-[:HAS_OSHPD_ID]->(osh10)

CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:68})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:98})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:140})

CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")
})


