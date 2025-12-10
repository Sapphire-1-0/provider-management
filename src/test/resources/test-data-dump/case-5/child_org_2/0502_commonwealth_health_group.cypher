// --- Match MA Networks ---
MATCH (net1:Network) WHERE net1.code = "7362"
MATCH (net2:Network) WHERE net2.code = "7363"
MATCH (net3:Network) WHERE net3.code = "7364"
MATCH (net4:Network) WHERE net4.code = "7365"
MATCH (net5:Network) WHERE net5.code = "7366"

// --- Organization ---
CREATE (org:Organization {
  name: "Commonwealth Health Group",
  aliasName: "Commonwealth Group",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "CHGMA1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "6789012345", startDate: date("2022-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "500005"})
MERGE (tin:Identifier:TIN {value: "555555555"}) ON CREATE SET tin.legalName = "Baystate Health Partners Legal Entity"
CREATE (medicaid:Identifier:MedicaidID {value: "6000002", state: "MA", startDate: date("2021-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D6789012", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00006"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC6789", state:"MA", startDate: date("2022-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2022-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2022-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"500 Commonwealth Ave", secondaryAddress:"Suite 500", city:"Boston", state:"MA", zipCode:"02115", county:"Suffolk", countyFIPS:"25025"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"617-555-5000", tty:"617-555-5001", fax:"617-555-5002", email:"billing@CommonwealthGroup.com", website:"https://www.CommonwealthGroup.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Daniel", middleName:"K", lastName:"Smith", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)

// --- Create Role Networks ---
CREATE (rn1: RoleNetwork)
CREATE (role)-[:SERVES]->(rn1)
CREATE (rn1)-[:NETWORK_IS]->(net1)

CREATE (rn2: RoleNetwork)
CREATE (role)-[:SERVES]->(rn2)
CREATE (rn2)-[:NETWORK_IS]->(net2)

CREATE (rn3: RoleNetwork)
CREATE (role)-[:SERVES]->(rn3)
CREATE (rn3)-[:NETWORK_IS]->(net3)

CREATE (rn4: RoleNetwork)
CREATE (role)-[:SERVES]->(rn4)
CREATE (rn4)-[:NETWORK_IS]->(net4)

CREATE (rn5: RoleNetwork)
CREATE (role)-[:SERVES]->(rn5)
CREATE (rn5)-[:NETWORK_IS]->(net5)

// --- Locations and RoleLocations 1-10 ---
CREATE (loc1:Location {name:"Commonwealth Clinic 1", code:"CHGLOC000MA001", streetAddress:"10 Beacon St", secondaryAddress:"Suite 101", city:"Boston", state:"MA", zipCode:"02108", county:"Suffolk", countyFIPS:"25025"})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

CREATE (acc1:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"617-00101", tty:"617-00102", fax:"617-00103", email:"CHGLOC000MA001@example.com", website:"https://www.CHGLOC000MA001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Ethan", middleName:"L", lastName:"White", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:85})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:125})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:155})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2022-01-01"), endDate:date("4000-01-01")})

// --- Location 2: Boston ---
CREATE (loc2:Location {name:"Commonwealth Health Clinic 2", code:"CHGLOC000MA002", streetAddress:"101 Beacon St", secondaryAddress:"Suite 210", city:"Boston", state:"MA", zipCode:"02108", county:"Suffolk", countyFIPS:"25025"})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

CREATE (acc2:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)

CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"617-00202", tty:"617-00203", fax:"617-00204", email:"CHGLOC000MA002@example.com", website:"https://www.CHGLOC000MA002.com"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Hannah", middleName:"A", lastName:"Lee", title:"Director"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA002"})
CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2)
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:80})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 3: Worcester ---
CREATE (loc3:Location {name:"Commonwealth Health Clinic 3", code:"CHGLOC000MA003", streetAddress:"202 Main St", secondaryAddress:"Suite 211", city:"Worcester", state:"MA", zipCode:"01608", county:"Worcester", countyFIPS:"25225"})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc3:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)
CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {phone:"508-00303", tty:"508-00304", fax:"508-00305", email:"CHGLOC000MA003@example.com", website:"https://www.CHGLOC000MA003.com"})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (p3:Person {firstName:"Ethan", middleName:"B", lastName:"Clark", title:"Director"})
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)
CREATE (osh3:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA003"})
CREATE (loc3)-[:HAS_OSHPD_ID]->(osh3)
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:85})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:125})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:155})
CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 4: Springfield ---
CREATE (loc4:Location {name:"Commonwealth Health Clinic 4", code:"CHGLOC000MA004", streetAddress:"303 Elm St", secondaryAddress:"Suite 212", city:"Springfield", state:"MA", zipCode:"01103", county:"Hampden", countyFIPS:"25013"})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc4:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)
CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {phone:"413-00404", tty:"413-00405", fax:"413-00406", email:"CHGLOC000MA004@example.com", website:"https://www.CHGLOC000MA004.com"})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (p4:Person {firstName:"Olivia", middleName:"C", lastName:"Martinez", title:"Director"})
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)
CREATE (osh4:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA004"})
CREATE (loc4)-[:HAS_OSHPD_ID]->(osh4)
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:80})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 5: Lowell ---
CREATE (loc5:Location {name:"Commonwealth Health Clinic 5", code:"CHGLOC000MA005", streetAddress:"404 River St", secondaryAddress:"Suite 213", city:"Lowell", state:"MA", zipCode:"01850", county:"Middlesex", countyFIPS:"25017"})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc5:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)
CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {phone:"978-00505", tty:"978-00506", fax:"978-00507", email:"CHGLOC000MA005@example.com", website:"https://www.CHGLOC000MA005.com"})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (p5:Person {firstName:"Liam", middleName:"D", lastName:"Clark", title:"Director"})
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)
CREATE (osh5:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA005"})
CREATE (loc5)-[:HAS_OSHPD_ID]->(osh5)
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:75})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:115})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:145})
CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 6: Brockton ---
CREATE (loc6:Location {name:"Commonwealth Health Clinic 6", code:"CHGLOC000MA006", streetAddress:"505 Oak St", secondaryAddress:"Suite 214", city:"Brockton", state:"MA", zipCode:"02301", county:"Plymouth", countyFIPS:"25023"})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc6:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc6)-[:ACCESSIBLE]->(acc6)
CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {phone:"508-00606", tty:"508-00607", fax:"508-00608", email:"CHGLOC000MA006@example.com", website:"https://www.CHGLOC000MA006.com"})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (p6:Person {firstName:"Ava", middleName:"E", lastName:"Lewis", title:"Director"})
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)
CREATE (osh6:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA006"})
CREATE (loc6)-[:HAS_OSHPD_ID]->(osh6)
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:80})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 7: Quincy ---
CREATE (loc7:Location {name:"Commonwealth Health Clinic 7", code:"CHGLOC000MA007", streetAddress:"606 Maple St", secondaryAddress:"Suite 215", city:"Quincy", state:"MA", zipCode:"02169", county:"Norfolk", countyFIPS:"25021"})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc7:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc7)-[:ACCESSIBLE]->(acc7)
CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {phone:"617-00707", tty:"617-00708", fax:"617-00709", email:"CHGLOC000MA007@example.com", website:"https://www.CHGLOC000MA007.com"})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (p7:Person {firstName:"William", middleName:"F", lastName:"Harris", title:"Director"})
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)
CREATE (osh7:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA007"})
CREATE (loc7)-[:HAS_OSHPD_ID]->(osh7)
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:85})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:125})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:155})
CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 8: Lynn ---
CREATE (loc8:Location {name:"Commonwealth Health Clinic 8", code:"CHGLOC000MA008", streetAddress:"707 Pine St", secondaryAddress:"Suite 216", city:"Lynn", state:"MA", zipCode:"01901", county:"Essex", countyFIPS:"25009"})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc8:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc8)-[:ACCESSIBLE]->(acc8)
CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {phone:"781-00808", tty:"781-00809", fax:"781-00810", email:"CHGLOC000MA008@example.com", website:"https://www.CHGLOC000MA008.com"})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (p8:Person {firstName:"Ava", middleName:"G", lastName:"Moore", title:"Director"})
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)
CREATE (osh8:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA008"})
CREATE (loc8)-[:HAS_OSHPD_ID]->(osh8)
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:80})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 9: Newton ---
CREATE (loc9:Location {name:"Commonwealth Health Clinic 9", code:"CHGLOC000MA009", streetAddress:"808 Cedar St", secondaryAddress:"Suite 217", city:"Newton", state:"MA", zipCode:"02458", county:"Middlesex", countyFIPS:"25017"})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc9:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc9)-[:ACCESSIBLE]->(acc9)
CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {phone:"617-00909", tty:"617-00910", fax:"617-00911", email:"CHGLOC000MA009@example.com", website:"https://www.CHGLOC000MA009.com"})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (p9:Person {firstName:"James", middleName:"H", lastName:"Taylor", title:"Director"})
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)
CREATE (osh9:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA009"})
CREATE (loc9)-[:HAS_OSHPD_ID]->(osh9)
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:85})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:125})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:155})
CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

// --- Location 10: Cambridge ---
CREATE (loc10:Location {name:"Commonwealth Health Clinic 10", code:"CHGLOC000MA010", streetAddress:"909 Birch St", secondaryAddress:"Suite 218", city:"Cambridge", state:"MA", zipCode:"02139", county:"Middlesex", countyFIPS:"25017"})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))
CREATE (acc10:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc10)-[:ACCESSIBLE]->(acc10)
CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {phone:"617-01010", tty:"617-01011", fax:"617-01012", email:"CHGLOC000MA010@example.com", website:"https://www.CHGLOC000MA010.com"})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (p10:Person {firstName:"Mia", middleName:"I", lastName:"Anderson", title:"Director"})
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)
CREATE (osh10:Identifier:OSHPD_ID {value:"OSHPD_CHGLOC000MA010"})
CREATE (loc10)-[:HAS_OSHPD_ID]->(osh10)
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:90})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:130})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:160})
CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2023-01-01"), endDate:date("4000-01-01")})

