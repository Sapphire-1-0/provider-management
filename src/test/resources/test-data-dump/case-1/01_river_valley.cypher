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
CREATE (contact)-[:HAS_ORGANIZATION_CONTACT]->(org)
CREATE (addr:Address {streetAddress:"123 Main St", secondaryAddress:"Suite 100", city:"Los Angeles", state:"CA", zipCode:"90001", county:"Los Angeles", countyFIPS:"06037"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"555-123-4567", tty:"555-123-4568", fax:"555-123-4569", email:"billing@RiverValleyPartners.com", website:"https://www.RiverValleyPartners.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"John", middleName:"A", lastName:"Doe", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)

// --- Locations, RoleLocations, Accessibility, DIR Contact, OSHPD_ID, Capacities, Location Qualification ---
WITH org, role
UNWIND range(1,10) AS i
WITH org, role, i, "RVMP_LOC_" + toString(i) AS locId
CREATE (loc:Location {
  name: "River Valley Clinic " + i,
  streetAddress: (100+i) + " Main St",
  secondaryAddress: "Suite " + (100+i),
  city: CASE i WHEN 1 THEN "Los Angeles" WHEN 2 THEN "San Diego" WHEN 3 THEN "San Francisco" WHEN 4 THEN "Sacramento"
          WHEN 5 THEN "Fresno" WHEN 6 THEN "Oakland" WHEN 7 THEN "San Jose" WHEN 8 THEN "Long Beach"
          WHEN 9 THEN "Bakersfield" ELSE "Anaheim" END,
  state: "CA",
  zipCode: "9000" + i,
  county: "Los Angeles",
  countyFIPS: "06037"
})
WITH loc, role, i, locId

// --- RoleLocation ---
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
