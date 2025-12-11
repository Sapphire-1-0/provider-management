MATCH (net1:Network) WHERE net1.code = "2650"
MATCH (net2:Network) WHERE net2.code = "5296"
MATCH (net3:Network) WHERE net3.code = "8956"
MATCH (net4:Network) WHERE net4.code = "14347"
MATCH (net5:Network) WHERE net5.code = "14593"

MATCH (parent_org:Organization) where parent_org.code="RVMP1234567890"

MATCH (loc1:Location) where loc1.code = "RVLOC000CA001"
MATCH (loc2:Location) where loc2.code = "RVLOC000CA002"
MATCH (loc3:Location) where loc3.code = "RVLOC000CA003"
MATCH (loc4:Location) where loc4.code = "RVLOC000CA004"
MATCH (loc5:Location) where loc5.code = "RVLOC000CA005"


CREATE (org:Organization {
  name: "Golden State Health Alliance",
  aliasName: "Golden State Alliance",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "GSHA1234567890"
})

CREATE (org)-[:PART_OF]->(parent_org)


CREATE (npi:Identifier:NPI {value: "9876543210", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "100010"})
MERGE (tin:Identifier:TIN {value: "111111111"}) ON CREATE SET tin.legalName = "River Valley Medical Partners Legal Entity"
CREATE (medicaid:Identifier:MedicaidID {value: "2000010", state: "CA", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "G1234567", startDate: date("2018-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00002"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)


CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LICGSHA1234", state:"CA", startDate: date("2020-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)


CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"123 Main St", secondaryAddress:"Suite 100", city:"Los Angeles", state:"CA", zipCode:"90001", county:"Los Angeles", countyFIPS:"06037"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"555-123-4567", tty:"555-123-4568", fax:"555-123-4569", email:"billing@GoldenStateAlliance.com", website:"https://www.GoldenStateAlliance.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"John", middleName:"A", lastName:"Doe", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)


CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)


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


CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl3)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl4)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl5)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc6:Location {name:"Golden State Clinic 6", code:"GSHALOC000CA006", streetAddress:"106 Main St", secondaryAddress:"Suite 106", city:"Oakland", state:"CA", zipCode:"94607", county:"Alameda", countyFIPS:"06001"})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl6)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc7:Location {name:"Golden State Clinic 7", code:"GSHALOC000CA007", streetAddress:"107 Main St", secondaryAddress:"Suite 107", city:"San Jose", state:"CA", zipCode:"95112", county:"Santa Clara", countyFIPS:"06085"})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl7)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc8:Location {name:"Golden State Clinic 8", code:"GSHALOC000CA008", streetAddress:"108 Main St", secondaryAddress:"Suite 108", city:"Long Beach", state:"CA", zipCode:"90802", county:"Los Angeles", countyFIPS:"06037"})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl8)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc9:Location {name:"Golden State Clinic 9", code:"GSHALOC000CA009", streetAddress:"109 Main St", secondaryAddress:"Suite 109", city:"Bakersfield", state:"CA", zipCode:"93301", county:"Kern", countyFIPS:"06029"})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl9)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

CREATE (loc10:Location {name:"Golden State Clinic 10", code:"GSHALOC000CA010", streetAddress:"110 Main St", secondaryAddress:"Suite 110", city:"Anaheim", state:"CA", zipCode:"92805", county:"Orange", countyFIPS:"06059"})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl10)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)


CREATE (acc6:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc6)-[:ACCESSIBLE]->(acc6)
CREATE (acc7:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc7)-[:ACCESSIBLE]->(acc7)
CREATE (acc8:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc8)-[:ACCESSIBLE]->(acc8)
CREATE (acc9:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc9)-[:ACCESSIBLE]->(acc9)
CREATE (acc10:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc10)-[:ACCESSIBLE]->(acc10)


CREATE (c6:Contact {use:"DIR"})
CREATE (t6:Telecom {
  phone:"555-00601", tty:"555-00602", fax:"555-00603",
  email:"GSHALOC000CA006@example.com", website:"https://www.GSHALOC000CA006.com"
})
CREATE (c6)-[:TELECOM_IS]->(t6)
CREATE (p6:Person {firstName:"Daniel", middleName:"K", lastName:"Patel", title:"Administrator"})
CREATE (c6)-[:PERSON_IS]->(p6)
CREATE (rl6)-[:HAS_LOCATION_CONTACT]->(c6)
CREATE (osh6:Identifier:OSHPD_ID {value:"OSHPD_GSHALOC000CA006"})
CREATE (loc6)-[:HAS_OSHPD_ID]->(osh6)
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc6)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc6)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")
})

CREATE (c7:Contact {use:"DIR"})
CREATE (t7:Telecom {
  phone:"555-00701", tty:"555-00702", fax:"555-00703",
  email:"GSHALOC000CA007@example.com", website:"https://www.GSHALOC000CA007.com"
})
CREATE (c7)-[:TELECOM_IS]->(t7)
CREATE (p7:Person {firstName:"Ava", middleName:"J", lastName:"Hernandez", title:"Operations Director"})
CREATE (c7)-[:PERSON_IS]->(p7)
CREATE (rl7)-[:HAS_LOCATION_CONTACT]->(c7)
CREATE (osh7:Identifier:OSHPD_ID {value:"OSHPD_GSHALOC000CA007"})
CREATE (loc7)-[:HAS_OSHPD_ID]->(osh7)
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc7)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc7)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")
})

CREATE (c8:Contact {use:"DIR"})
CREATE (t8:Telecom {
  phone:"555-00801", tty:"555-00802", fax:"555-00803",
  email:"GSHALOC000CA008@example.com", website:"https://www.GSHALOC000CA008.com"
})
CREATE (c8)-[:TELECOM_IS]->(t8)
CREATE (p8:Person {firstName:"Liam", middleName:"G", lastName:"Johnson", title:"Director"})
CREATE (c8)-[:PERSON_IS]->(p8)
CREATE (rl8)-[:HAS_LOCATION_CONTACT]->(c8)
CREATE (osh8:Identifier:OSHPD_ID {value:"OSHPD_GSHALOC000CA008"})
CREATE (loc8)-[:HAS_OSHPD_ID]->(osh8)
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc8)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc8)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")
})

CREATE (c9:Contact {use:"DIR"})
CREATE (t9:Telecom {
  phone:"555-00901", tty:"555-00902", fax:"555-00903",
  email:"GSHALOC000CA009@example.com", website:"https://www.GSHALOC000CA009.com"
})
CREATE (c9)-[:TELECOM_IS]->(t9)
CREATE (p9:Person {firstName:"Maya", middleName:"S", lastName:"Ramirez", title:"Director"})
CREATE (c9)-[:PERSON_IS]->(p9)
CREATE (rl9)-[:HAS_LOCATION_CONTACT]->(c9)
CREATE (osh9:Identifier:OSHPD_ID {value:"OSHPD_GSHALOC000CA009"})
CREATE (loc9)-[:HAS_OSHPD_ID]->(osh9)
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc9)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc9)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")
})

CREATE (c10:Contact {use:"DIR"})
CREATE (t10:Telecom {
  phone:"555-01001", tty:"555-01002", fax:"555-01003",
  email:"GSHALOC000CA010@example.com", website:"https://www.GSHALOC000CA010.com"
})
CREATE (c10)-[:TELECOM_IS]->(t10)
CREATE (p10:Person {firstName:"Ethan", middleName:"P", lastName:"Williams", title:"Director"})
CREATE (c10)-[:PERSON_IS]->(p10)
CREATE (rl10)-[:HAS_LOCATION_CONTACT]->(c10)
CREATE (osh10:Identifier:OSHPD_ID {value:"OSHPD_GSHALOC000CA010"})
CREATE (loc10)-[:HAS_OSHPD_ID]->(osh10)
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc10)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc10)-[:HAS_QUALIFICATION]->(:Qualification:Certification {
  type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")
})
