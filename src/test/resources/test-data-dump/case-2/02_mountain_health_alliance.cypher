// --- Organization ---
CREATE (org:Organization {
  name: "Mountain Health Alliance",
  aliasName: "Mtn Health Alliance",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "MHA1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "2234567890", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "100002"})
CREATE (tin:Identifier:TIN {value: "222222222", legalName: "Mountain Health Alliance Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "2000002", state: "GA", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D2234567", startDate: date("2018-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00002"})
CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC2234", state:"GA", startDate: date("2020-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})
CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)-[:HAS_ORGANIZATION_CONTACT]->(org)
CREATE (addr:Address {streetAddress:"456 Peachtree St", secondaryAddress:"Suite 200", city:"Atlanta", state:"GA", zipCode:"30303", county:"Fulton", countyFIPS:"13121"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"555-223-4567", tty:"555-223-4568", fax:"555-223-4569", email:"billing@MountainHealth.com", website:"https://www.MountainHealth.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Jane", middleName:"B", lastName:"Smith", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)

// --- Locations ---
WITH org, role
UNWIND range(1,10) AS i
WITH org, role, i, "MHA_LOC_" + toString(i) AS locId
CREATE (loc:Location {
  name: "Mountain Clinic " + i,
  streetAddress: (200+i) + " Peachtree St",
  secondaryAddress: "Suite " + (200+i),
  city: CASE i WHEN 1 THEN "Atlanta" WHEN 2 THEN "Savannah" WHEN 3 THEN "Augusta" WHEN 4 THEN "Columbus"
          WHEN 5 THEN "Macon" WHEN 6 THEN "Athens" WHEN 7 THEN "Albany" WHEN 8 THEN "Roswell"
          WHEN 9 THEN "Marietta" ELSE "Decatur" END,
  state: "GA",
  zipCode: "3030" + i,
  county: "Fulton",
  countyFIPS: "13121"
})
WITH loc, role, i, locId
CREATE (roleLoc:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(roleLoc)
CREATE (roleLoc)-[:LOCATION_IS]->(loc)
WITH loc, roleLoc, i, locId

// --- Accessibility ---
CREATE (acc:Accessibility {
  adaBasicAccess: true,
  adaParking: true,
  adaExamRoom: true,
  adaRestRoom: true,
  adaLimitedAccess: true,
  adaExteriorBldg: true,
  adaInteriorBldg: true,
  adaPatientArea: true,
  adaPatientDiagnostic: true,
  adaNumberOfSpaces: true
})
CREATE (loc)-[:ACCESSIBLE]->(acc)
WITH loc, roleLoc, i, locId

// --- Location Contact (DIR) ---
CREATE (locContact:Contact {use:"DIR"})
CREATE (roleLoc)-[:HAS_LOCATION_CONTACT]->(locContact)
CREATE (tele:Telecom {
  phone: "555-" + locId + "01",
  tty: "555-" + locId + "02",
  fax: "555-" + locId + "03",
  email: locId + "@example.com",
  website: "https://www." + locId + ".com"
})
CREATE (locContact)-[:TELECOM_IS]->(tele)
CREATE (per:Person {
  firstName: "FirstName" + locId,
  middleName: "M",
  lastName: "LastName" + locId,
  title: "Director"
})
CREATE (locContact)-[:PERSON_IS]->(per)
WITH loc, i, locId

// --- OSHPD_ID ---
CREATE (oshpd:Identifier:OSHPD_ID {value:"OSHPD_" + locId})
CREATE (loc)-[:HAS_OSHPD_ID]->(oshpd)
WITH loc, i, locId

// --- Capacity Nodes ---
UNWIND ["MCD_AC", "MCR_AC", "EXCH_AC"] AS capType
CREATE (cap:Capacity {type: capType, value: 50 + (i * CASE capType
  WHEN "MCD_AC" THEN 7
  WHEN "MCR_AC" THEN 11
  ELSE 13 END) % 450 })
CREATE (loc)-[:HAS_CAPACITY]->(cap)
WITH loc

// --- Qualification:Certification ---
CREATE (qual:Qualification:Certification {
  type:"CERT_DHSSE",
  startDate: date("2020-01-01"),
  endDate: date("4000-01-01")
})
CREATE (loc)-[:HAS_QUALIFICATION]->(qual)
