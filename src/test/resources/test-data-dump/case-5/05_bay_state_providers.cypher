// --- Organization ---
CREATE (org:Organization {
  name: "Bay State Providers",
  aliasName: "Bay State",
  type: "PPG",
  atypical: false,
  capitated: false,
  pcpPractitionerRequired: false,
  code: "BSP1234567890"
})

// --- Identifiers ---
CREATE (npi:Identifier:NPI {value: "5234567890", startDate: date("2020-01-01"), endDate: date("4000-01-01")})
CREATE (medicare:Identifier:MedicareID {value: "100005"})
CREATE (tin:Identifier:TIN {value: "555555555", legalName: "Bay State Providers Legal Entity"})
CREATE (medicaid:Identifier:MedicaidID {value: "2000005", state: "MA", startDate: date("2019-01-01"), endDate: date("4000-01-01")})
CREATE (dea:Identifier:DEA_Number {value: "D5234567", startDate: date("2018-01-01"), endDate: date("4000-01-01")})
CREATE (ppgId:Identifier:PPG_ID {value: "PPG00005"})
CREATE (org)-[:HAS_NPI]->(npi)
CREATE (org)-[:HAS_MEDICARE_ID]->(medicare)
CREATE (org)-[:HAS_TIN]->(tin)
CREATE (org)-[:HAS_MEDICAID_ID]->(medicaid)
CREATE (org)-[:HAS_DEA_NUMBER]->(dea)
CREATE (org)-[:HAS_PPG_ID]->(ppgId)

// --- Qualifications ---
CREATE (q1:Qualification:License {type:"LICE_MLC", value:"LIC5234", state:"MA", startDate: date("2020-01-01"), endDate: date("4000-01-01"), status:"ACTIVE"})
CREATE (q2:Qualification:Accreditation {type:"ACCRED_NCQA", level:"A", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"NCQA"})
CREATE (q3:Qualification:Accreditation {type:"ACCRED_URAC", startDate: date("2020-01-01"), endDate: date("4000-01-01"), issuer:"URAC"})
CREATE (org)-[:HAS_QUALIFICATION]->(q1)
CREATE (org)-[:HAS_QUALIFICATION]->(q2)
CREATE (org)-[:HAS_QUALIFICATION]->(q3)

// --- Organization Contact ---
CREATE (contact:Contact {use:"BILLING"})
CREATE (contact)-[:HAS_ORGANIZATION_CONTACT]->(org)
CREATE (addr:Address {streetAddress:"654 Beacon St", secondaryAddress:"Suite 500", city:"Boston", state:"MA", zipCode:"02101", county:"Suffolk", countyFIPS:"25025"})
CREATE (contact)-[:ADDRESS_IS]->(addr)
CREATE (telecom:Telecom {phone:"555-523-4567", tty:"555-523-4568", fax:"555-523-4569", email:"billing@BayState.com", website:"https://www.BayState.com"})
CREATE (contact)-[:TELECOM_IS]->(telecom)
CREATE (person:Person {firstName:"Carol", middleName:"E", lastName:"Davis", title:"Billing Manager"})
CREATE (contact)-[:PERSON_IS]->(person)

// --- RoleInstance & Locations ---
CREATE (role:RoleInstance)
CREATE (org)-[:HAS_ROLE]->(role)
WITH org, role
UNWIND range(1,10) AS i
WITH org, role, i, "BSP_LOC_" + toString(i) AS locId
CREATE (loc:Location {
  name: "Bay State Clinic " + i,
  streetAddress: (500+i) + " Beacon St",
  secondaryAddress: "Suite " + (500+i),
  city: CASE i WHEN 1 THEN "Boston" WHEN 2 THEN "Cambridge" WHEN 3 THEN "Worcester" WHEN 4 THEN "Springfield"
          WHEN 5 THEN "Lowell" WHEN 6 THEN "Quincy" WHEN 7 THEN "Lynn" WHEN 8 THEN "Newton"
          WHEN 9 THEN "Somerville" ELSE "Framingham" END,
  state: "MA",
  zipCode: "02" + toString(100 + i),
  county: "Suffolk",
  countyFIPS: "25025"
})
WITH loc, role, i, locId

// RoleLocation
CREATE (roleLoc:RoleLocation)
CREATE (role)-[:PERFORMED_AT]->(roleLoc)
CREATE (roleLoc)-[:LOCATION_IS]->(loc)
WITH loc, roleLoc, i, locId

// Accessibility
CREATE (acc:Accessibility {adaBasicAccess:true, adaParking:true, adaRestRoom:true})
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

// Location Certification
CREATE (qual:Qualification:Certification {type:"CERT_DHSSE", startDate:date("2020-01-01"), endDate:date("4000-01-01")})
CREATE (loc)-[:HAS_QUALIFICATION]->(qual)
