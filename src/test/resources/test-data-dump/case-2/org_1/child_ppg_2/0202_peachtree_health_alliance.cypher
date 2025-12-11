// --- Match GA Networks ---
MATCH (net1:Network) WHERE net1.code = "2432"
MATCH (net2:Network) WHERE net2.code = "1017"
MATCH (net3:Network) WHERE net3.code = "6556"
MATCH (net4:Network) WHERE net4.code = "47"
MATCH (net5:Network) WHERE net5.code = "9317"

// --- Organization ---
CREATE (org:Organization {
  name: "Peachtree Health Alliance",
  aliasName: "Peachtree Alliance",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "PHA1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "4567890123", startDate: date("2022-03-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "220001"})
CREATE (tin:Identifier:TIN {value: "444444444", legalName: "Peachtree Health Alliance Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "3200001", state: "GA", startDate: date("2021-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D4567890", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00004"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC4567", state:"GA", startDate: date("2022-03-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2022-03-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2022-03-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"300 Peachtree Blvd", secondaryAddress:"Suite 400", city:"Atlanta", state:"GA", zipCode:"30309", county:"Fulton", countyFIPS:"13121"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"404-555-6789", tty:"404-555-6790", fax:"404-555-6791", email:"billing@peachtreealliance.com", website:"https://www.peachtreealliance.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Emily", middleName:"K", lastName:"Walker", title:"Billing Manager"})
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
CREATE (loc1:Location {name:"Peachtree Clinic 1", code:"PHLOC000GA001", streetAddress:"301 Peachtree Blvd", secondaryAddress:"Suite 101", city:"Atlanta", state:"GA", zipCode:"30309", county:"Fulton", countyFIPS:"13121"})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

// --- Locations and RoleLocations 2-10 ---
CREATE (loc2:Location {name:"Peachtree Clinic 2", code:"PHLOC000GA002", streetAddress:"402 Peachtree Blvd", secondaryAddress:"Suite 102", city:"Atlanta", state:"GA", zipCode:"30310", county:"Fulton", countyFIPS:"13121"})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc3:Location {name:"Peachtree Clinic 3", code:"PHLOC000GA003", streetAddress:"503 Peachtree Blvd", secondaryAddress:"Suite 103", city:"Atlanta", state:"GA", zipCode:"30311", county:"Fulton", countyFIPS:"13121"})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc4:Location {name:"Peachtree Clinic 4", code:"PHLOC000GA004", streetAddress:"604 Peachtree Blvd", secondaryAddress:"Suite 104", city:"Atlanta", state:"GA", zipCode:"30312", county:"Fulton", countyFIPS:"13121"})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc5:Location {name:"Peachtree Clinic 5", code:"PHLOC000GA005", streetAddress:"705 Peachtree Blvd", secondaryAddress:"Suite 105", city:"Atlanta", state:"GA", zipCode:"30313", county:"Fulton", countyFIPS:"13121"})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc6:Location {name:"Peachtree Clinic 6", code:"PHLOC000GA006", streetAddress:"806 Peachtree Blvd", secondaryAddress:"Suite 106", city:"Atlanta", state:"GA", zipCode:"30314", county:"Fulton", countyFIPS:"13121"})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc7:Location {name:"Peachtree Clinic 7", code:"PHLOC000GA007", streetAddress:"907 Peachtree Blvd", secondaryAddress:"Suite 107", city:"Atlanta", state:"GA", zipCode:"30315", county:"Fulton", countyFIPS:"13121"})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc8:Location {name:"Peachtree Clinic 8", code:"PHLOC000GA008", streetAddress:"1008 Peachtree Blvd", secondaryAddress:"Suite 108", city:"Atlanta", state:"GA", zipCode:"30316", county:"Fulton", countyFIPS:"13121"})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc9:Location {name:"Peachtree Clinic 9", code:"PHLOC000GA009", streetAddress:"1109 Peachtree Blvd", secondaryAddress:"Suite 109", city:"Atlanta", state:"GA", zipCode:"30317", county:"Fulton", countyFIPS:"13121"})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc10:Location {name:"Peachtree Clinic 10", code:"PHLOC000GA010", streetAddress:"1210 Peachtree Blvd", secondaryAddress:"Suite 110", city:"Atlanta", state:"GA", zipCode:"30318", county:"Fulton", countyFIPS:"13121"})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2025-01-01"), endDate: date("4000-01-01")}]->(rn5)

// --- Location 1: Peachtree Clinic 1 ---
CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"404-555-1111", tty:"404-555-1112", fax:"404-555-1113", email:"PHLOC000GA001_new@example.com", website:"https://www.PHLOC000GA001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Emma", middleName:"L", lastName:"Taylor", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)
CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_PHLOC000GA001"})
CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})


// --- Location 2: Cedarwood Clinic 2 ---
CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"912-555-2020", tty:"912-555-2021", fax:"912-555-2022", email:"CWLOC000GA002@example.com", website:"https://www.CWLOC000GA002.com"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Liam", middleName:"J", lastName:"Smith", title:"Director"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)
CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA002"})
CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2)
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:120})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:160})
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:210})
CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 3: Cedarwood Clinic 3 ---
CREATE (c3:Contact {use:"DIR"})
CREATE (t3:Telecom {phone:"706-555-3030", tty:"706-555-3031", fax:"706-555-3032", email:"CWLOC000GA003@example.com", website:"https://www.CWLOC000GA003.com"})
CREATE (c3)-[:TELECOM_IS]->(t3)
CREATE (p3:Person {firstName:"Olivia", middleName:"R", lastName:"Johnson", title:"Director"})
CREATE (c3)-[:PERSON_IS]->(p3)
CREATE (rl3)-[:HAS_LOCATION_CONTACT]->(c3)
CREATE (osh3:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA003"})
CREATE (loc3)-[:HAS_OSHPD_ID]->(osh3)
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:110})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:155})
CREATE (loc3)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:205})
CREATE (loc3)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 4: Cedarwood Clinic 4 ---
CREATE (c4:Contact {use:"DIR"})
CREATE (t4:Telecom {phone:"478-555-4040", tty:"478-555-4041", fax:"478-555-4042", email:"CWLOC000GA004@example.com", website:"https://www.CWLOC000GA004.com"})
CREATE (c4)-[:TELECOM_IS]->(t4)
CREATE (p4:Person {firstName:"Noah", middleName:"M", lastName:"Williams", title:"Director"})
CREATE (c4)-[:PERSON_IS]->(p4)
CREATE (rl4)-[:HAS_LOCATION_CONTACT]->(c4)
CREATE (osh4:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA004"})
CREATE (loc4)-[:HAS_OSHPD_ID]->(osh4)
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:115})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:165})
CREATE (loc4)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:215})
CREATE (loc4)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 5: Cedarwood Clinic 5 ---
CREATE (c5:Contact {use:"DIR"})
CREATE (t5:Telecom {phone:"706-555-5050", tty:"706-555-5051", fax:"706-555-5052", email:"CWLOC000GA005@example.com", website:"https://www.CWLOC000GA005.com"})
CREATE (c5)-[:TELECOM_IS]->(t5)
CREATE (p5:Person {firstName:"Ava", middleName:"S", lastName:"Brown", title:"Director"})
CREATE (c5)-[:PERSON_IS]->(p5)
CREATE (rl5)-[:HAS_LOCATION_CONTACT]->(c5)
CREATE (osh5:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA005"})
CREATE (loc5)-[:HAS_OSHPD_ID]->(osh5)
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:125})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:170})
CREATE (loc5)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:220})
CREATE (loc5)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 6: Cedarwood Clinic 6 ---
CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {phone:"706-555-6060", tty:"706-555-6061", fax:"706-555-6062", email:"CWLOC000GA006@example.com", website:"https://www.CWLOC000GA006.com"})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (p6:Person {firstName:"William", middleName:"T", lastName:"Jones", title:"Director"})
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)
CREATE (osh6:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA006"})
CREATE (loc6)-[:HAS_OSHPD_ID]->(osh6)
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:130})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:175})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:225})
CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 7: Cedarwood Clinic 7 ---
CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {phone:"229-555-7070", tty:"229-555-7071", fax:"229-555-7072", email:"CWLOC000GA007@example.com", website:"https://www.CWLOC000GA007.com"})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (p7:Person {firstName:"Sophia", middleName:"L", lastName:"Davis", title:"Director"})
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)
CREATE (osh7:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA007"})
CREATE (loc7)-[:HAS_OSHPD_ID]->(osh7)
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:135})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:180})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:230})
CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 8: Cedarwood Clinic 8 ---
CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {phone:"770-555-8080", tty:"770-555-8081", fax:"770-555-8082", email:"CWLOC000GA008@example.com", website:"https://www.CWLOC000GA008.com"})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (p8:Person {firstName:"James", middleName:"R", lastName:"Wilson", title:"Director"})
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)
CREATE (osh8:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA008"})
CREATE (loc8)-[:HAS_OSHPD_ID]->(osh8)
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:140})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:185})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:235})
CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 9: Cedarwood Clinic 9 ---
CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {phone:"678-555-9090", tty:"678-555-9091", fax:"678-555-9092", email:"CWLOC000GA009@example.com", website:"https://www.CWLOC000GA009.com"})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (p9:Person {firstName:"Isabella", middleName:"N", lastName:"Martinez", title:"Director"})
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)
CREATE (osh9:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA009"})
CREATE (loc9)-[:HAS_OSHPD_ID]->(osh9)
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:145})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:190})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:240})
CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})

// --- Location 10: Cedarwood Clinic 10 ---
CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {phone:"404-555-1010", tty:"404-555-1011", fax:"404-555-1012", email:"CWLOC000GA010@example.com", website:"https://www.CWLOC000GA010.com"})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (p10:Person {firstName:"Mia", middleName:"Q", lastName:"Garcia", title:"Director"})
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)
CREATE (osh10:Identifier:OSHPD_ID {value:"OSHPD_CWLOC000GA010"})
CREATE (loc10)-[:HAS_OSHPD_ID]->(osh10)
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:150})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:200})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:250})
CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2021-01-01"), endDate:date("4000-01-01")})
