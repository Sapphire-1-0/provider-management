// --- Match MA Networks ---
MATCH (net1:Network) WHERE net1.code = "7357"
MATCH (net2:Network) WHERE net2.code = "7358"
MATCH (net3:Network) WHERE net3.code = "7359"
MATCH (net4:Network) WHERE net4.code = "7360"
MATCH (net5:Network) WHERE net5.code = "7361"

MATCH (parent_org:Organization) where parent_org.code="BHPMA1234567890"

MATCH (loc6:Location) where loc6.code = "BAYLOC000MA006"
MATCH (loc7:Location) where loc7.code = "BAYLOC000MA007"
MATCH (loc8:Location) where loc8.code = "BAYLOC000MA008"
MATCH (loc9:Location) where loc9.code = "BAYLOC000MA009"
MATCH (loc10:Location) where loc10.code = "BAYLOC000MA010"

// --- Organization ---
CREATE (org:Organization {
  name: "Metro Health Alliance",
  aliasName: "Metro Health",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "MHA1234567890"
})

CREATE (org)-[:PART_OF]->(parent_org)

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
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"B", startDate: date("2022-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2022-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"500 Metro Blvd", secondaryAddress:"Suite 300", city:"Boston", state:"MA", zipCode:"02110", county:"Suffolk", countyFIPS:"25025"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"617-555-6789", tty:"617-555-6790", fax:"617-555-6791", email:"billing@MetroHealth.com", website:"https://www.MetroHealth.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Laura", middleName:"K", lastName:"Green", title:"Billing Manager"})
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

// --- Locations and RoleLocations 1-5 ---
CREATE (loc1:Location {name:"Metro Clinic 1", code:"METLOC000MA001", streetAddress:"10 Liberty St", secondaryAddress:"Suite 101", city:"Boston", state:"MA", zipCode:"02110", county:"Suffolk", countyFIPS:"25025"})
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
CREATE (t1:Telecom {phone:"617-10101", tty:"617-10102", fax:"617-10103", email:"METLOC000MA001@example.com", website:"https://www.METLOC000MA001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Ethan", middleName:"L", lastName:"Clark", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_METLOC000MA001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:90})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:130})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:160})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2022-01-01"), endDate:date("4000-01-01")})

// --- Location 2: Cambridge ---
CREATE (loc2:Location {name:"Metro Clinic 2", code:"METLOC000MA002", streetAddress:"20 Harvard St", secondaryAddress:"Suite 202", city:"Cambridge", state:"MA", zipCode:"02139", county:"Middlesex", countyFIPS:"25017"})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

CREATE (acc2:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)

CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"617-20202", tty:"617-20203", fax:"617-20204", email:"METLOC000MA002@example.com", website:"https://www.METLOC000MA002.com"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Sophia", middleName:"M", lastName:"Reed", title:"Director"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_METLOC000MA002"})
CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2)
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:85})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:125})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2022-01-01"), endDate:date("4000-01-01")})

// --- Location 3: Worcester ---
CREATE (loc3:Location {name:"Metro Clinic 3", code:"METLOC000MA003", streetAddress:"30 Main St", secondaryAddress:"Suite 303", city:"Worcester", state:"MA", zipCode:"01608", county:"Worcester", countyFIPS:"25225"})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

CREATE (acc3:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)

CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {phone:"508-30303", tty:"508-30304", fax:"508-30305", email:"METLOC000MA003@example.com", website:"https://www.METLOC000MA003.com"})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (p3:Person {firstName:"Liam", middleName:"N", lastName:"Parker", title:"Director"})
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)

CREATE (osh3:Identifier:OSHPD_ID {value:"OSHPD_METLOC000MA003"})
CREATE (loc3)-[:HAS_OSHPD_ID]->(osh3)
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:90})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:130})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:160})
CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2022-01-01"), endDate:date("4000-01-01")})

// --- Location 4: Springfield ---
CREATE (loc4:Location {name:"Metro Clinic 4", code:"METLOC000MA004", streetAddress:"40 Elm St", secondaryAddress:"Suite 404", city:"Springfield", state:"MA", zipCode:"01103", county:"Hampden", countyFIPS:"25013"})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

CREATE (acc4:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)

CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {phone:"413-40404", tty:"413-40405", fax:"413-40406", email:"METLOC000MA004@example.com", website:"https://www.METLOC000MA004.com"})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (p4:Person {firstName:"Olivia", middleName:"P", lastName:"James", title:"Director"})
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)

CREATE (osh4:Identifier:OSHPD_ID {value:"OSHPD_METLOC000MA004"})
CREATE (loc4)-[:HAS_OSHPD_ID]->(osh4)
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:85})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:125})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2022-01-01"), endDate:date("4000-01-01")})

// --- Location 5: Lowell ---
CREATE (loc5:Location {name:"Metro Clinic 5", code:"METLOC000MA005", streetAddress:"50 River St", secondaryAddress:"Suite 505", city:"Lowell", state:"MA", zipCode:"01850", county:"Middlesex", countyFIPS:"25017"})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

CREATE (acc5:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)

CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {phone:"978-50505", tty:"978-50506", fax:"978-50507", email:"METLOC000MA005@example.com", website:"https://www.METLOC000MA005.com"})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (p5:Person {firstName:"Noah", middleName:"R", lastName:"Scott", title:"Director"})
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)

CREATE (osh5:Identifier:OSHPD_ID {value:"OSHPD_METLOC000MA005"})
CREATE (loc5)-[:HAS_OSHPD_ID]->(osh5)
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:80})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:120})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:150})
CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2022-01-01"), endDate:date("4000-01-01")})

// --- Location 6:
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

// --- Location 7:
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

// --- Location 8:
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

// --- Location 9:
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

// --- Location 10:

CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
FOREACH (rn IN [rn1,rn2,rn3,rn4,rn5] | CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn))

