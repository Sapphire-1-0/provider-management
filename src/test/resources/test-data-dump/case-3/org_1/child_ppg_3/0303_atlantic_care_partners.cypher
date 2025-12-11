// --- Match FL Networks ---
MATCH (net1:Network) WHERE net1.code = "1836"
MATCH (net2:Network) WHERE net2.code = "379"
MATCH (net3:Network) WHERE net3.code = "2897"
MATCH (net4:Network) WHERE net4.code = "803"
MATCH (net5:Network) WHERE net5.code = "801"

// --- Organization ---
CREATE (org:Organization {
  name: "Atlantic Care Partners",
  aliasName: "Atlantic Partners",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "ACPFL9876543210"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "5678901234", startDate: date("2021-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "300008"})
CREATE (tin:Identifier:TIN {value: "444444444", legalName: "Atlantic Care Partners Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "4000008", state: "FL", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D5678901", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00008"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC5678", state:"FL", startDate: date("2021-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2021-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2021-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {
  streetAddress:"1500 Atlantic Blvd",
  secondaryAddress:"Suite 200",
  city:"Jacksonville",
  state:"FL",
  zipCode:"32207",
  county:"Duval",
  countyFIPS:"12031"
})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {
  phone:"904-555-4321",
  tty:"904-555-4322",
  fax:"904-555-4323",
  email:"billing@AtlanticPartners.com",
  website:"https://www.AtlanticPartners.com"
})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {
  firstName:"Michael",
  middleName:"J",
  lastName:"Anderson",
  title:"Billing Manager"
})
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

// --- Locations + RoleLocations 1–10 ---

//////////////////////////////////////////////////////////////////////////
// --- Location 1: Miami ---
//////////////////////////////////////////////////////////////////////////

CREATE (loc1:Location {
  name:"Atlantic Clinic 1",
  code:"ACPLOC000FL001",
  streetAddress:"1201 Biscayne Blvd",
  secondaryAddress:"Suite 101",
  city:"Miami",
  state:"FL",
  zipCode:"33132",
  county:"Miami-Dade",
  countyFIPS:"08601"
})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)

CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc1:Accessibility {
  adaBasicAccess:true,
  adaParking:true,
  adaExamRoom:true,
  adaRestRoom:true,
  adaLimitedAccess:true,
  adaExteriorBldg:true,
  adaInteriorBldg:true,
  adaPatientArea:true,
  adaPatientDiagnostic:true,
  adaNumberOfSpaces:true
})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"305-00101", tty:"305-00102", fax:"305-00103", email:"ACPLOC000FL001@example.com", website:"https://www.ACPLOC000FL001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Ella", middleName:"K", lastName:"Hughes", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

//////////////////////////////////////////////////////////////////////////
// Locations 2–10 (Tampa, Orlando, Jacksonville, Tallahassee,
// Fort Lauderdale, St. Petersburg, Naples, Sarasota, Gainesville)
//////////////////////////////////////////////////////////////////////////

// --- Location 2: Tampa ---
CREATE (loc2:Location {
  name:"Atlantic Clinic 2",
  code:"ACPLOC000FL002",
  streetAddress:"2202 Bayshore Blvd",
  secondaryAddress:"Suite 202",
  city:"Tampa",
  state:"FL",
  zipCode:"33606",
  county:"Hillsborough",
  countyFIPS:"12057"
})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc2:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)

CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"813-00202", tty:"813-00203", fax:"813-00204", email:"ACPLOC000FL002@example.com", website:"https://www.ACPLOC000FL002.com"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Mila", middleName:"L", lastName:"Reed", title:"Director"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL002"})
CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2)
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:120})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:160})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:220})
CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 3: Orlando ---
CREATE (loc3:Location {
  name:"Atlantic Clinic 3",
  code:"ACPLOC000FL003",
  streetAddress:"3303 Lake Ave",
  secondaryAddress:"Suite 303",
  city:"Orlando",
  state:"FL",
  zipCode:"32801",
  county:"Orange",
  countyFIPS:"12095"
})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc3:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)

CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {phone:"407-00303", tty:"407-00304", fax:"407-00305", email:"ACPLOC000FL003@example.com", website:"https://www.ACPLOC000FL003.com"})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (p3:Person {firstName:"Ethan", middleName:"M", lastName:"Green", title:"Director"})
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)

CREATE (osh3:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL003"})
CREATE (loc3)-[:HAS_OSHPD_ID]->(osh3)
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:110})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:140})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:210})
CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 4: Jacksonville ---
CREATE (loc4:Location {
  name:"Atlantic Clinic 4",
  code:"ACPLOC000FL004",
  streetAddress:"4404 River St",
  secondaryAddress:"Suite 404",
  city:"Jacksonville",
  state:"FL",
  zipCode:"32202",
  county:"Duval",
  countyFIPS:"12031"
})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc4:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)

CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {phone:"904-00404", tty:"904-00405", fax:"904-00406", email:"ACPLOC000FL004@example.com", website:"https://www.ACPLOC000FL004.com"})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (p4:Person {firstName:"Grace", middleName:"N", lastName:"Turner", title:"Director"})
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)

CREATE (osh4:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL004"})
CREATE (loc4)-[:HAS_OSHPD_ID]->(osh4)
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:130})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:170})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:230})
CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 5: Tallahassee ---
CREATE (loc5:Location {
  name:"Atlantic Clinic 5",
  code:"ACPLOC000FL005",
  streetAddress:"5505 Capital Cir",
  secondaryAddress:"Suite 505",
  city:"Tallahassee",
  state:"FL",
  zipCode:"32301",
  county:"Leon",
  countyFIPS:"12073"
})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc5:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)

CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {phone:"850-00505", tty:"850-00506", fax:"850-00507", email:"ACPLOC000FL005@example.com", website:"https://www.ACPLOC000FL005.com"})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (p5:Person {firstName:"Logan", middleName:"O", lastName:"Bennett", title:"Director"})
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)

CREATE (osh5:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL005"})
CREATE (loc5)-[:HAS_OSHPD_ID]->(osh5)
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:90})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:180})
CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 6: Fort Lauderdale ---
CREATE (loc6:Location {
  name:"Atlantic Clinic 6",
  code:"ACPLOC000FL006",
  streetAddress:"6606 Ocean Blvd",
  secondaryAddress:"Suite 606",
  city:"Fort Lauderdale",
  state:"FL",
  zipCode:"33301",
  county:"Broward",
  countyFIPS:"12011"
})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc6:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc6)-[:ACCESSIBLE]->(acc6)

CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {phone:"954-00606", tty:"954-00607", fax:"954-00608", email:"ACPLOC000FL006@example.com", website:"https://www.ACPLOC000FL006.com"})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (p6:Person {firstName:"Ava", middleName:"P", lastName:"Lopez", title:"Director"})
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)

CREATE (osh6:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL006"})
CREATE (loc6)-[:HAS_OSHPD_ID]->(osh6)
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:130})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:190})
CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 7: St. Petersburg ---
CREATE (loc7:Location {
  name:"Atlantic Clinic 7",
  code:"ACPLOC000FL007",
  streetAddress:"7707 Central Ave",
  secondaryAddress:"Suite 707",
  city:"St. Petersburg",
  state:"FL",
  zipCode:"33701",
  county:"Pinellas",
  countyFIPS:"12103"
})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc7:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc7)-[:ACCESSIBLE]->(acc7)

CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {phone:"727-00707", tty:"727-00708", fax:"727-00709", email:"ACPLOC000FL007@example.com", website:"https://www.ACPLOC000FL007.com"})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (p7:Person {firstName:"Isabella", middleName:"Q", lastName:"Ford", title:"Director"})
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)

CREATE (osh7:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL007"})
CREATE (loc7)-[:HAS_OSHPD_ID]->(osh7)
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:95})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:140})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 8: Naples ---
CREATE (loc8:Location {
  name:"Atlantic Clinic 8",
  code:"ACPLOC000FL008",
  streetAddress:"8808 Gulf Shore Dr",
  secondaryAddress:"Suite 808",
  city:"Naples",
  state:"FL",
  zipCode:"34102",
  county:"Collier",
  countyFIPS:"12021"
})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc8:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc8)-[:ACCESSIBLE]->(acc8)

CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {phone:"239-00808", tty:"239-00809", fax:"239-00810", email:"ACPLOC000FL008@example.com", website:"https://www.ACPLOC000FL008.com"})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (p8:Person {firstName:"James", middleName:"R", lastName:"Clark", title:"Director"})
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)

CREATE (osh8:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL008"})
CREATE (loc8)-[:HAS_OSHPD_ID]->(osh8)
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:105})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:145})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:195})
CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 9: Sarasota ---
CREATE (loc9:Location {
  name:"Atlantic Clinic 9",
  code:"ACPLOC000FL009",
  streetAddress:"9909 Palm Ave",
  secondaryAddress:"Suite 909",
  city:"Sarasota",
  state:"FL",
  zipCode:"34236",
  county:"Sarasota",
  countyFIPS:"12115"
})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc9:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc9)-[:ACCESSIBLE]->(acc9)

CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {phone:"941-00909", tty:"941-00910", fax:"941-00911", email:"ACPLOC000FL009@example.com", website:"https://www.ACPLOC000FL009.com"})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (p9:Person {firstName:"Harper", middleName:"S", lastName:"Diaz", title:"Director"})
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)

CREATE (osh9:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL009"})
CREATE (loc9)-[:HAS_OSHPD_ID]->(osh9)
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:115})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:155})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:205})
CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 10: Gainesville ---
CREATE (loc10:Location {
  name:"Atlantic Clinic 10",
  code:"ACPLOC000FL010",
  streetAddress:"1010 Gator Blvd",
  secondaryAddress:"Suite 1010",
  city:"Gainesville",
  state:"FL",
  zipCode:"32601",
  county:"Alachua",
  countyFIPS:"12001"
})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn2)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn3)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn4)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate:date("2024-01-01"), endDate:date("4000-01-01")}]->(rn5)

CREATE (acc10:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc10)-[:ACCESSIBLE]->(acc10)

CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {phone:"352-01010", tty:"352-01011", fax:"352-01012", email:"ACPLOC000FL010@example.com", website:"https://www.ACPLOC000FL010.com"})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (p10:Person {firstName:"Lucas", middleName:"T", lastName:"Evans", title:"Director"})
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)

CREATE (osh10:Identifier:OSHPD_ID {value:"OSHPD_ACPLOC000FL010"})
CREATE (loc10)-[:HAS_OSHPD_ID]->(osh10)
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:125})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:165})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:215})
CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
