// --- Match AL Networks ---
MATCH (net1:Network) WHERE net1.code = "3856"
MATCH (net2:Network) WHERE net2.code = "14744"
MATCH (net3:Network) WHERE net3.code = "14176"
MATCH (net4:Network) WHERE net4.code = "3857"
MATCH (net5:Network) WHERE net5.code = "14151"

// --- Organization ---
CREATE (org:Organization {
  name: "Magnolia Wellness Partners",
  aliasName: "Magnolia Partners",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "MWPAL0099887766"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value:"4789012345", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value:"410022"})
CREATE (tin:Identifier:TIN {value:"544332211", legalName:"Magnolia Wellness Partners Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value:"5000112", state:"AL", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value:"M7788990", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value:"PPG00009"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC9022", state:"AL", startDate:date("2021-01-01"), endDate:date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate:date("2020-01-01"), endDate:date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_JCAHO", startDate:date("2019-01-01"), endDate:date("4000-01-01"), issuer:"JCAHO"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)

CREATE (addr:Address {streetAddress:"1100 Magnolia Ave", secondaryAddress:"Suite 300", city:"Montgomery", state:"AL", zipCode:"36106", county:"Montgomery", countyFIPS:"01101"})
CREATE (contact)-[:ADDRESS_IS]->(addr)

CREATE (tele:Telecom {phone:"334-555-3300", tty:"334-555-3301", fax:"334-555-3302", email:"billing@magnoliapartners.com", website:"https://www.magnoliapartners.com"})
CREATE (contact)-[:TELECOM_IS]->(tele)

CREATE (pers:Person {firstName:"Diane", middleName:"K", lastName:"Bishop", title:"Billing Director"})
CREATE (contact)-[:PERSON_IS]->(pers)

// --- Role Instance ---
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


// =====================================================
// ===================== LOCATIONS 1–10 =================
// =====================================================

// ---------- Location 1: Birmingham ----------
CREATE (loc1:Location {
  name:"Magnolia Clinic Birmingham",
  code:"MWPLOCAL001",
  streetAddress:"200 Highland Ave",
  secondaryAddress:"Suite 120",
  city:"Birmingham",
  state:"AL",
  zipCode:"35205",
  county:"Jefferson",
  countyFIPS:"01073"
})

CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)

CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate:date("2025-01-01"), endDate:date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc1:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true,
  adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true,
  adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true
})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"205-555-1001", fax:"205-555-1002", email:"birmingham@mwp.com"})
CREATE (p1:Person {firstName:"Sharon", middleName:"M", lastName:"Conley", title:"Clinic Director"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (loc1)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL001"})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:60})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:90})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:120})

CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 2: Mobile ----------
CREATE (loc2:Location {
  name:"Magnolia Clinic Mobile",
  code:"MWPLOCAL002",
  streetAddress:"1500 Dauphin St",
  secondaryAddress:"Suite 210",
  city:"Mobile",
  state:"AL",
  zipCode:"36604",
  county:"Mobile",
  countyFIPS:"01097"
})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)

CREATE (rl2)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl2)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc2:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)

CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"251-555-2001", fax:"251-555-2002", email:"mobile@mwp.com"})
CREATE (p2:Person {firstName:"Daniel", middleName:"R", lastName:"Whitman", title:"Director"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (loc2)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL002"})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:50})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:80})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:110})

CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 3: Huntsville ----------
CREATE (loc3:Location {
  name:"Magnolia Clinic Huntsville",
  code:"MWPLOCAL003",
  streetAddress:"2200 Whitesburg Dr",
  secondaryAddress:"Suite 400",
  city:"Huntsville",
  state:"AL",
  zipCode:"35801",
  county:"Madison",
  countyFIPS:"01089"
})

CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)

CREATE (rl3)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl3)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl3)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc3:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)

CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {phone:"256-555-3001", fax:"256-555-3002", email:"huntsville@mwp.com"})
CREATE (p3:Person {firstName:"Elena", middleName:"P", lastName:"Douglas", title:"Director"})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)

CREATE (loc3)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL003"})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:45})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:70})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:100})

CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 4: Tuscaloosa ----------
CREATE (loc4:Location {
  name:"Magnolia Clinic Tuscaloosa",
  code:"MWPLOCAL004",
  streetAddress:"900 McFarland Blvd",
  secondaryAddress:"Suite 220",
  city:"Tuscaloosa",
  state:"AL",
  zipCode:"35401",
  county:"Tuscaloosa",
  countyFIPS:"01125"
})

CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)

CREATE (rl4)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl4)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl4)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc4:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)

CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {phone:"205-555-4001", fax:"205-555-4002", email:"tuscaloosa@mwp.com"})
CREATE (p4:Person {firstName:"Harold", middleName:"T", lastName:"Jackson", title:"Director"})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)

CREATE (loc4)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL004"})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:55})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:85})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:115})

CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 5: Dothan ----------
CREATE (loc5:Location {
  name:"Magnolia Clinic Dothan",
  code:"MWPLOCAL005",
  streetAddress:"1800 Westgate Pkwy",
  secondaryAddress:"Suite 130",
  city:"Dothan",
  state:"AL",
  zipCode:"36303",
  county:"Houston",
  countyFIPS:"01069"
})

CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)

CREATE (rl5)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl5)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl5)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl5)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl5)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc5:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)

CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {phone:"334-555-5001", fax:"334-555-5002", email:"dothan@mwp.com"})
CREATE (p5:Person {firstName:"Kristen", middleName:"L", lastName:"Howard", title:"Director"})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)

CREATE (loc5)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL005"})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:40})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:65})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:95})

CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 6: Auburn ----------
CREATE (loc6:Location {
  name:"Magnolia Clinic Auburn",
  code:"MWPLOCAL006",
  streetAddress:"700 South College St",
  secondaryAddress:"Suite 220",
  city:"Auburn",
  state:"AL",
  zipCode:"36830",
  county:"Lee",
  countyFIPS:"01081"
})

CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)

CREATE (rl6)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl6)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl6)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl6)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl6)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc6:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc6)-[:ACCESSIBLE]->(acc6)

CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {phone:"334-555-6001", fax:"334-555-6002", email:"auburn@mwp.com"})
CREATE (p6:Person {firstName:"Logan", middleName:"C", lastName:"Reynolds", title:"Director"})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)

CREATE (loc6)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL006"})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:42})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:68})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:100})

CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 7: Florence ----------
CREATE (loc7:Location {
  name:"Magnolia Clinic Florence",
  code:"MWPLOCAL007",
  streetAddress:"310 Cox Creek Pkwy",
  secondaryAddress:"Suite 150",
  city:"Florence",
  state:"AL",
  zipCode:"35630",
  county:"Lauderdale",
  countyFIPS:"01077"
})

CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)

CREATE (rl7)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl7)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl7)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl7)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl7)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc7:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc7)-[:ACCESSIBLE]->(acc7)

CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {phone:"256-555-7001", fax:"256-555-7002", email:"florence@mwp.com"})
CREATE (p7:Person {firstName:"Michelle", middleName:"R", lastName:"Gaines", title:"Director"})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)

CREATE (loc7)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL007"})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:38})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:60})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:90})

CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 8: Gadsden ----------
CREATE (loc8:Location {
  name:"Magnolia Clinic Gadsden",
  code:"MWPLOCAL008",
  streetAddress:"1400 Rainbow Dr",
  secondaryAddress:"Suite 180",
  city:"Gadsden",
  state:"AL",
  zipCode:"35901",
  county:"Etowah",
  countyFIPS:"01055"
})

CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)

CREATE (rl8)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl8)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl8)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl8)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl8)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc8:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc8)-[:ACCESSIBLE]->(acc8)

CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {phone:"256-555-8001", fax:"256-555-8002", email:"gadsden@mwp.com"})
CREATE (p8:Person {firstName:"Alyssa", middleName:"N", lastName:"Tucker", title:"Director"})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)

CREATE (loc8)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL008"})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:48})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:72})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:105})

CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 9: Decatur ----------
CREATE (loc9:Location {
  name:"Magnolia Clinic Decatur",
  code:"MWPLOCAL009",
  streetAddress:"1000 Beltline Rd",
  secondaryAddress:"Suite 260",
  city:"Decatur",
  state:"AL",
  zipCode:"35601",
  county:"Morgan",
  countyFIPS:"01103"
})

CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)

CREATE (rl9)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl9)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl9)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl9)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl9)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc9:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc9)-[:ACCESSIBLE]->(acc9)

CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {phone:"256-555-9001", fax:"256-555-9002", email:"decatur@mwp.com"})
CREATE (p9:Person {firstName:"Nathan", middleName:"G", lastName:"Bowers", title:"Director"})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)

CREATE (loc9)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL009"})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:52})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:78})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:112})

CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})


// ---------- Location 10: Hoover ----------
CREATE (loc10:Location {
  name:"Magnolia Clinic Hoover",
  code:"MWPLOCAL010",
  streetAddress:"3500 John Hawkins Pkwy",
  secondaryAddress:"Suite 170",
  city:"Hoover",
  state:"AL",
  zipCode:"35244",
  county:"Jefferson",
  countyFIPS:"01073"
})

CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)

CREATE (rl10)-[:ROLE_LOCATION_SERVES]->(rn1)
CREATE (rl10)-[:ROLE_LOCATION_SERVES]->(rn2)
CREATE (rl10)-[:ROLE_LOCATION_SERVES]->(rn3)
CREATE (rl10)-[:ROLE_LOCATION_SERVES]->(rn4)
CREATE (rl10)-[:ROLE_LOCATION_SERVES]->(rn5)

CREATE (acc10:Accessibility {
  adaBasicAccess:true, adaParking:true, adaExamRoom:true,
  adaRestRoom:true, adaExteriorBldg:true
})
CREATE (loc10)-[:ACCESSIBLE]->(acc10)

CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {phone:"205-555-9101", fax:"205-555-9102", email:"hoover@mwp.com"})
CREATE (p10:Person {firstName:"Jeffrey", middleName:"Q", lastName:"Miller", title:"Director"})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)

CREATE (loc10)-[:HAS_OSHPD_ID]->(:Identifier:OSHPD_ID {value:"OSHPD_MWPLOCAL010"})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:58})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:87})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:118})

CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate:date("2020-01-01"),
  endDate:date("4000-01-01")
})
