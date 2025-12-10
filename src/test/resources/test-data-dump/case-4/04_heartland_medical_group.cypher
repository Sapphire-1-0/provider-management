// --- Match AL Networks ---
MATCH (net1:Network) WHERE net1.code = "3856"
MATCH (net2:Network) WHERE net2.code = "14744"
MATCH (net3:Network) WHERE net3.code = "14176"
MATCH (net4:Network) WHERE net4.code = "3857"
MATCH (net5:Network) WHERE net5.code = "14151"

// --- Organization ---
CREATE (org:Organization {
  name: "Heartland Health Partners",
  aliasName: "Heartland Partners",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "HHPAL1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "4567890123", startDate: date("2021-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "400003"})
CREATE (tin:Identifier:TIN {value: "444444444", legalName: "Heartland Health Partners Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "5000001", state: "AL", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D4567890", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00004"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC4567", state:"AL", startDate: date("2021-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2021-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2021-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"100 Heartland Blvd", secondaryAddress:"Suite 100", city:"Birmingham", state:"AL", zipCode:"35203", county:"Jefferson", countyFIPS:"01073"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"205-555-1234", tty:"205-555-1235", fax:"205-555-1236", email:"billing@HeartlandPartners.com", website:"https://www.HeartlandPartners.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Alice", middleName:"B", lastName:"Johnson", title:"Billing Manager"})
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
CREATE (loc1:Location {name:"Heartland Clinic 1", code:"HHLOC000AL001", streetAddress:"101 River St", secondaryAddress:"Suite 101", city:"Birmingham", state:"AL", zipCode:"35203", county:"Jefferson", countyFIPS:"01073"})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc1:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)

CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"205-00101", tty:"205-00102", fax:"205-00103", email:"HHLOC000AL001@example.com", website:"https://www.HHLOC000AL001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Emma", middleName:"C", lastName:"Brown", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_HHLOC000AL001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:80})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// Repeat similar blocks for loc2 → loc10 with AL cities: Montgomery, Mobile, Huntsville, Tuscaloosa, Hoover, Dothan, Auburn, Decatur, Madison
// --- Location 2: Birmingham ---
CREATE (loc2:Location {name:"Sunshine Clinic 2", code:"SHLOC000AL002", streetAddress:"202 1st Ave N", secondaryAddress:"Suite 202", city:"Birmingham", state:"AL", zipCode:"35203", county:"Jefferson", countyFIPS:"01073"})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc2:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)

CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"205-00202", tty:"205-00203", fax:"205-00204", email:"SHLOC000AL002@example.com", website:"https://www.SHLOC000AL002.com"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Olivia", middleName:"L", lastName:"Smith", title:"Director"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL002"})
CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2)
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:120})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:160})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:220})
CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 3: Montgomery ---
CREATE (loc3:Location {name:"Sunshine Clinic 3", code:"SHLOC000AL003", streetAddress:"303 Dexter Ave", secondaryAddress:"Suite 303", city:"Montgomery", state:"AL", zipCode:"36104", county:"Montgomery", countyFIPS:"01101"})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc3:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)

CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {phone:"334-00303", tty:"334-00304", fax:"334-00305", email:"SHLOC000AL003@example.com", website:"https://www.SHLOC000AL003.com"})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (p3:Person {firstName:"Liam", middleName:"M", lastName:"Johnson", title:"Director"})
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)

CREATE (osh3:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL003"})
CREATE (loc3)-[:HAS_OSHPD_ID]->(osh3)
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:110})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:140})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:210})
CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 4: Huntsville ---
CREATE (loc4:Location {name:"Sunshine Clinic 4", code:"SHLOC000AL004", streetAddress:"404 University Dr NW", secondaryAddress:"Suite 404", city:"Huntsville", state:"AL", zipCode:"35801", county:"Madison", countyFIPS:"01089"})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc4:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)

CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {phone:"256-00404", tty:"256-00405", fax:"256-00406", email:"SHLOC000AL004@example.com", website:"https://www.SHLOC000AL004.com"})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (p4:Person {firstName:"Sophia", middleName:"N", lastName:"Williams", title:"Director"})
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)

CREATE (osh4:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL004"})
CREATE (loc4)-[:HAS_OSHPD_ID]->(osh4)
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:130})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:170})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:230})
CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 5: Tuscaloosa ---
CREATE (loc5:Location {name:"Sunshine Clinic 5", code:"SHLOC000AL005", streetAddress:"505 University Blvd E", secondaryAddress:"Suite 505", city:"Tuscaloosa", state:"AL", zipCode:"35401", county:"Tuscaloosa", countyFIPS:"01129"})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc5:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)

CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {phone:"205-00505", tty:"205-00506", fax:"205-00507", email:"SHLOC000AL005@example.com", website:"https://www.SHLOC000AL005.com"})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (p5:Person {firstName:"Ethan", middleName:"O", lastName:"Brown", title:"Director"})
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)

CREATE (osh5:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL005"})
CREATE (loc5)-[:HAS_OSHPD_ID]->(osh5)
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 6: Mobile ---
CREATE (loc6:Location {name:"Sunshine Clinic 6", code:"SHLOC000AL006", streetAddress:"606 Dauphin St", secondaryAddress:"Suite 606", city:"Mobile", state:"AL", zipCode:"36602", county:"Mobile", countyFIPS:"01097"})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc6:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc6)-[:ACCESSIBLE]->(acc6)

CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {phone:"251-00606", tty:"251-00607", fax:"251-00608", email:"SHLOC000AL006@example.com", website:"https://www.SHLOC000AL006.com"})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (p6:Person {firstName:"Ava", middleName:"P", lastName:"Davis", title:"Director"})
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)

CREATE (osh6:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL006"})
CREATE (loc6)-[:HAS_OSHPD_ID]->(osh6)
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:140})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:180})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:250})
CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 7: Hoover ---
CREATE (loc7:Location {name:"Sunshine Clinic 7", code:"SHLOC000AL007", streetAddress:"707 John Hawkins Pkwy", secondaryAddress:"Suite 707", city:"Hoover", state:"AL", zipCode:"35244", county:"Jefferson", countyFIPS:"01073"})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc7:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc7)-[:ACCESSIBLE]->(acc7)

CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {phone:"205-00707", tty:"205-00708", fax:"205-00709", email:"SHLOC000AL007@example.com", website:"https://www.SHLOC000AL007.com"})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (p7:Person {firstName:"Mia", middleName:"Q", lastName:"Martinez", title:"Director"})
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)

CREATE (osh7:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL007"})
CREATE (loc7)-[:HAS_OSHPD_ID]->(osh7)
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:125})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:160})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:210})
CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 8: Decatur ---
CREATE (loc8:Location {name:"Sunshine Clinic 8", code:"SHLOC000AL008", streetAddress:"808 Bank St SW", secondaryAddress:"Suite 808", city:"Decatur", state:"AL", zipCode:"35601", county:"Morgan", countyFIPS:"01117"})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc8:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc8)-[:ACCESSIBLE]->(acc8)

CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {phone:"256-00808", tty:"256-00809", fax:"256-00810", email:"SHLOC000AL008@example.com", website:"https://www.SHLOC000AL008.com"})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (p8:Person {firstName:"Noah", middleName:"R", lastName:"Garcia", title:"Director"})
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)

CREATE (osh8:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL008"})
CREATE (loc8)-[:HAS_OSHPD_ID]->(osh8)
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:105})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:190})
CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 9: Dothan ---
CREATE (loc9:Location {name:"Sunshine Clinic 9", code:"SHLOC000AL009", streetAddress:"909 Ross Clark Cir", secondaryAddress:"Suite 909", city:"Dothan", state:"AL", zipCode:"36301", county:"Houston", countyFIPS:"01075"})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc9:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc9)-[:ACCESSIBLE]->(acc9)

CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {phone:"334-00909", tty:"334-00910", fax:"334-00911", email:"SHLOC000AL009@example.com", website:"https://www.SHLOC000AL009.com"})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (p9:Person {firstName:"Isabella", middleName:"S", lastName:"Rodriguez", title:"Director"})
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)

CREATE (osh9:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL009"})
CREATE (loc9)-[:HAS_OSHPD_ID]->(osh9)
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:115})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:155})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:205})
CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})

// --- Location 10: Auburn ---
CREATE (loc10:Location {name:"Sunshine Clinic 10", code:"SHLOC000AL010", streetAddress:"1010 E Glenn Ave", secondaryAddress:"Suite 1010", city:"Auburn", state:"AL", zipCode:"36830", county:"Lee", countyFIPS:"01085"})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (acc10:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc10)-[:ACCESSIBLE]->(acc10)

CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {phone:"334-01010", tty:"334-01011", fax:"334-01012", email:"SHLOC000AL010@example.com", website:"https://www.SHLOC000AL010.com"})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (p10:Person {firstName:"James", middleName:"T", lastName:"Wilson", title:"Director"})
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)

CREATE (osh10:Identifier:OSHPD_ID {value:"OSHPD_SHLOC000AL010"})
CREATE (loc10)-[:HAS_OSHPD_ID]->(osh10)
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:120})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:160})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:220})
CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
