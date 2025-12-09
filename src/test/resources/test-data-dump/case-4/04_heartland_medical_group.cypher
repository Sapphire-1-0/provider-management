// --- Organization ---
CREATE (org:Organization {
  name: "Heartland Medical Group",
  aliasName: "Heartland MG",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "HMG1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "4234567890", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "100004"})
CREATE (tin:Identifier:TIN {value: "444444444", legalName: "Heartland Medical Group Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "2000004", state: "AL", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D4234567", startDate: date("2018-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00004"})
CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC4234", state:"AL", startDate: date("2020-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})
CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Org Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)-[:HAS_ORGANIZATION_CONTACT]->(org)
CREATE (addr:Address {streetAddress:"321 River St", secondaryAddress:"Suite 400", city:"Birmingham", state:"AL", zipCode:"35201", county:"Jefferson", countyFIPS:"01073"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"555-423-4567", tty:"555-423-4568", fax:"555-423-4569", email:"billing@HeartlandMG.com", website:"https://www.HeartlandMG.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Bob", middleName:"D", lastName:"Williams", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance & Locations ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)
WITH org, role
UNWIND range(1,10) AS i
WITH org, role, i, "HMG_LOC_" + toString(i) AS locId
CREATE (loc:Location {
  name: "Heartland Clinic " + i,
  streetAddress: (400+i) + " River St",
  secondaryAddress: "Suite " + (400+i),
  city: CASE i WHEN 1 THEN "Birmingham" WHEN 2 THEN "Montgomery" WHEN 3 THEN "Mobile" WHEN 4 THEN "Huntsville"
          WHEN 5 THEN "Tuscaloosa" WHEN 6 THEN "Hoover" WHEN 7 THEN "Dothan" WHEN 8 THEN "Decatur"
          WHEN 9 THEN "Madison" ELSE "Auburn" END,
  state: "AL",
  zipCode: "35" + toString(100 + i),
  county: "Jefferson",
  countyFIPS: "01073"
})
WITH loc, role, i, locId

// RoleLocation
CREATE (roleLoc:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(roleLoc)
CREATE (roleLoc)-[:LOCATION_IS]->(loc)
WITH loc, roleLoc, i, locId

// Accessibility
CREATE (acc:Accessibility {adaBasicAccess:true, adaParking:true, adaExamRoom:true})
CREATE (loc)-[:ACCESSIBLE]->(acc)
WITH loc, roleLoc, i, locId

// DIR Contact
CREATE (locContact:Contact {use:"DIR"})
CREATE (roleLoc)-[:HAS_LOCATION_CONTACT]->(locContact)
CREATE (tele:Telecom {phone:"555-" + locId + "01", tty:"555-" + locId + "02", fax:"555-" + locId + "03", email:locId + "@example.com", website:"https://www." + locId + ".com"})
CREATE (locContact)-[:TELECOM_IS]->(tele)
CREATE (per:Person {firstName:"FirstName" + locId, middleName:"M", lastName:"LastName" + locId, title:"Director"})
CREATE (locContact)-[:PERSON_IS]->(per)
WITH loc, i, locId

// OSHPD_ID
CREATE (oshpd:Identifier:OSHPD_ID {value:"OSHPD_" + locId})
CREATE (loc)-[:HAS_OSHPD_ID]->(oshpd)
WITH loc, i, locId

// Capacities
UNWIND ["MCD_AC","MCR_AC","EXCH_AC"] AS capType
CREATE (cap:Capacity {type:capType, value:50 + (i * CASE capType WHEN "MCD_AC" THEN 7 WHEN "MCR_AC" THEN 11 ELSE 13 END) % 450})
CREATE (loc)-[:HAS_CAPACITY]->(cap)
WITH loc

// Location Qualification
CREATE (qual:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
CREATE (loc)-[:HAS_QUALIFICATION]->(qual)
