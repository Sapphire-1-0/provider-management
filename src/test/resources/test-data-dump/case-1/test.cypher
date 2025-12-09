MATCH (net1:Network {code:"2650"}),
      (net2:Network {code:"5296"}),
      (net3:Network {code:"8956"}),
      (net4:Network {code:"14347"}),
      (net5:Network {code:"14593"})
// --- Organization ---
CREATE (org:Organization {
  name: "River Valley Medical Partners",
  aliasName: "River Valley Partners",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "RVMP1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "1234567890", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "100001"})
CREATE (tin:Identifier:TIN {value: "111111111", legalName: "River Valley Medical Partners Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "2000001", state: "CA", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D1234567", startDate: date("2018-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00001"})

CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC1234", state:"CA", startDate: date("2020-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})

CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)<-[:HAS_ORGANIZATION_CONTACT]-(org)
CREATE (addr:Address {streetAddress:"123 Main St", secondaryAddress:"Suite 100", city:"Los Angeles", state:"CA", zipCode:"90001", county:"Los Angeles", countyFIPS:"06037"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"555-123-4567", tty:"555-123-4568", fax:"555-123-4569", email:"billing@RiverValleyPartners.com", website:"https://www.RiverValleyPartners.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"John", middleName:"A", lastName:"Doe", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)


// --- Locations and RoleLocations 1-10 ---
CREATE (loc1:Location {name:"River Valley Clinic 1", code:"RVLOC000CA001", streetAddress:"101 Main St", secondaryAddress:"Suite 101", city:"Los Angeles", state:"CA", zipCode:"90001", county:"Los Angeles", countyFIPS:"06037"})
CREATE (rl1:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(loc1)

CREATE (loc2:Location {name:"River Valley Clinic 2", code:"RVLOC000CA002", streetAddress:"102 Main St", secondaryAddress:"Suite 102", city:"San Diego", state:"CA", zipCode:"92101", county:"San Diego", countyFIPS:"06073"})
CREATE (rl2:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(loc2)

CREATE (loc3:Location {name:"River Valley Clinic 3", code:"RVLOC000CA003", streetAddress:"103 Main St", secondaryAddress:"Suite 103", city:"San Francisco", state:"CA", zipCode:"94102", county:"San Francisco", countyFIPS:"06075"})
CREATE (rl3:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl3)
CREATE (rl3)-[:LOCATION_IS]->(loc3)

CREATE (loc4:Location {name:"River Valley Clinic 4", code:"RVLOC000CA004", streetAddress:"104 Main St", secondaryAddress:"Suite 104", city:"Sacramento", state:"CA", zipCode:"95814", county:"Sacramento", countyFIPS:"06067"})
CREATE (rl4:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl4)
CREATE (rl4)-[:LOCATION_IS]->(loc4)

CREATE (loc5:Location {name:"River Valley Clinic 5", code:"RVLOC000CA005", streetAddress:"105 Main St", secondaryAddress:"Suite 105", city:"Fresno", state:"CA", zipCode:"93721", county:"Fresno", countyFIPS:"06019"})
CREATE (rl5:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl5)
CREATE (rl5)-[:LOCATION_IS]->(loc5)

CREATE (loc6:Location {name:"River Valley Clinic 6", code:"RVLOC000CA006", streetAddress:"106 Main St", secondaryAddress:"Suite 106", city:"Oakland", state:"CA", zipCode:"94607", county:"Alameda", countyFIPS:"06001"})
CREATE (rl6:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl6)
CREATE (rl6)-[:LOCATION_IS]->(loc6)

CREATE (loc7:Location {name:"River Valley Clinic 7", code:"RVLOC000CA007", streetAddress:"107 Main St", secondaryAddress:"Suite 107", city:"San Jose", state:"CA", zipCode:"95112", county:"Santa Clara", countyFIPS:"06085"})
CREATE (rl7:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl7)
CREATE (rl7)-[:LOCATION_IS]->(loc7)

CREATE (loc8:Location {name:"River Valley Clinic 8", code:"RVLOC000CA008", streetAddress:"108 Main St", secondaryAddress:"Suite 108", city:"Long Beach", state:"CA", zipCode:"90802", county:"Los Angeles", countyFIPS:"06037"})
CREATE (rl8:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl8)
CREATE (rl8)-[:LOCATION_IS]->(loc8)

CREATE (loc9:Location {name:"River Valley Clinic 9", code:"RVLOC000CA009", streetAddress:"109 Main St", secondaryAddress:"Suite 109", city:"Bakersfield", state:"CA", zipCode:"93301", county:"Kern", countyFIPS:"06029"})
CREATE (rl9:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl9)
CREATE (rl9)-[:LOCATION_IS]->(loc9)

CREATE (loc10:Location {name:"River Valley Clinic 10", code:"RVLOC000CA010", streetAddress:"110 Main St", secondaryAddress:"Suite 110", city:"Anaheim", state:"CA", zipCode:"92805", county:"Orange", countyFIPS:"06059"})
CREATE (rl10:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(rl10)
CREATE (rl10)-[:LOCATION_IS]->(loc10)

// --- Accessibility ---
CREATE (acc1:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc1)-[:ACCESSIBLE]->(acc1)
CREATE (acc2:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc2)-[:ACCESSIBLE]->(acc2)
CREATE (acc3:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc3)-[:ACCESSIBLE]->(acc3)
CREATE (acc4:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc4)-[:ACCESSIBLE]->(acc4)
CREATE (acc5:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true, adaRestRoom:true, adaLimitedAccess:true, adaExteriorBldg:true, adaInteriorBldg:true, adaPatientArea:true, adaPatientDiagnostic:true, adaNumberOfSpaces:true})
CREATE (loc5)-[:ACCESSIBLE]->(acc5)
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

// --- DIR Contacts, OSHPD IDs, Capacities, Qualifications, RoleLocation connections to Networks ---
// Loc1
CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"555-00101", tty:"555-00102", fax:"555-00103", email:"RVLOC000CA001@example.com", website:"https://www.RVLOC000CA001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"FirstName1", middleName:"M", lastName:"LastName1", title:"Director"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)
CREATE (osh1:Identifier:OSHPD_ID {value:"OSHPD_RVLOC000CA001"}); CREATE (loc1)-[:HAS_OSHPD_ID]->(osh1)
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150})
CREATE (loc1)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200})
CREATE (loc1)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5)

// --- Repeat similar block for loc2 → loc10 with updated numbers ---
// loc2
CREATE (c2:Contact {use:"DIR"});
CREATE (t2:Telecom {phone:"555-00201", tty:"555-00202", fax:"555-00203", email:"RVLOC000CA002@example.com", website:"https://www.RVLOC000CA002.com"});
CREATE (c2)-[:TELECOM_IS]->(t2);
CREATE (p2:Person {firstName:"FirstName2", middleName:"M", lastName:"LastName2", title:"Director"});
CREATE (c2)-[:PERSON_IS]->(p2);
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2);
CREATE (osh2:Identifier:OSHPD_ID {value:"OSHPD_RVLOC000CA002"}); CREATE (loc2)-[:HAS_OSHPD_ID]->(osh2);
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCD_AC", value:100});
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"MCR_AC", value:150});
CREATE (loc2)-[:HAS_CAPACITY]->(:Capacity {type:"EXCH_AC", value:200});
CREATE (loc2)-[:HAS_QUALIFICATION]->(:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")});
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1);
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2);
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn3);
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn4);
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn5);

// --- Similarly, repeat loc3 → loc10 blocks with updated contact, OSHPD, and RoleLocation connections ---

