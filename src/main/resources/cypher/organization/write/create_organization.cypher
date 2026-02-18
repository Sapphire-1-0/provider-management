// ===============================
// Create Organization
// ===============================

CREATE (org:Organization {
  code: $code,
  name: $name,
  type: $type
})
SET org += {
  aliasName: $aliasName,
  description: $description
}
// ===============================
// Identifiers
// ===============================

// NPI
WITH org
UNWIND $npiList AS npiMap
MERGE (npi:NPI:Identifier {value: npiMap.value})
SET npi += {
  startDate: npiMap.startDate,
  endDate: npiMap.endDate
}
MERGE (org)-[:HAS_NPI]->(npi)

// Medicare
WITH org
UNWIND $medicareList as medicareMap
MERGE (medicareId: MedicareID: Identifier {value: medicareMap.value})
MERGE (org)-[:HAS_MEDICARE_ID]->(medicareId)

// MedicaidID
WITH org
UNWIND $medicaidList as medicaidMap
MERGE (medicaid: MedicaidID: Identifier {
    value: medicaidMap.value,
    state: medicaidMap.state
})
SET medicaid += {
  startDate: medicaidMap.startDate,
  endDate: medicaidMap.endDate
}
MERGE (org)-[:HAS_MEDICAID_ID]->(medicaid)

// ===============================
// Credentials
// ===============================

with DISTINCT p
UNWIND coalesce($credentials, []) AS credentialMap
CREATE (cred:Credential {
  type: credentialMap.type,
  geographyDescription: credentialMap.geographyDescription,
  FIPS: credentialMap.FIPS,
  committeeDate: credentialMap.committeeDate,
  endDate: credentialMap.endDate
})
MERGE (p)-[:HAS_CREDENTIALING]->(cred)

// ===============================
// Qualifications
// ===============================

with DISTINCT p
UNWIND coalesce($qualifications, []) AS qualificationMap
CREATE (q:Qualification {
  type: qualificationMap.type,
  issuer: qualificationMap.issuer,
  startDate: qualificationMap.startDate,
  endDate: qualificationMap.endDate,
  level: qualificationMap.level,
  value: qualificationMap.value
})
MERGE (p)-[:HAS_QUALIFICATION]->(q)

// ===============================
// Contacts
// ===============================
WITH DISTINCT org
UNWIND $contacts AS contactMap
CREATE (c:Contact {
  use: contactMap.use
})
MERGE (org)-[:HAS_ORGANIZATION_CONTACT]->(c)
// Contact Address
WITH DISTINCT org, c, contactMap
FOREACH (_ IN CASE WHEN contactMap.address IS NOT NULL THEN [1] ELSE [] END |
  CREATE (a:Address {
    streetAddress: contactMap.address.streetAddress,
    secondaryAddress: contactMap.address.secondaryAddress,
    city: contactMap.address.city,
    state: contactMap.address.state,
    zipCode: contactMap.address.zipCode,
    county: contactMap.address.county,
    countyFIPS: contactMap.address.countyFIPS})
  MERGE (c)-[:ADDRESS_IS]->(a)
)
// Contact Telecoms
WITH DISTINCT org, c, contactMap
FOREACH (_ IN CASE WHEN contactMap.telecom IS NOT NULL THEN [1] ELSE [] END |
  CREATE (t:Telecom {
    phone: contactMap.telecom.phone,
    tty: contactMap.telecom.tty,
    afterHoursNumber: contactMap.telecom.afterHoursNumber,
    email: contactMap.telecom.email,
    fax: contactMap.telecom.fax,
    website: contactMap.telecom.website
    })
  MERGE (c)-[:TELECOM_IS]->(t)
)
// Contact Person
WITH DISTINCT org, c, contactMap
FOREACH (_ IN CASE WHEN contactMap.person IS NOT NULL THEN [1] ELSE [] END |
  CREATE (per:Person {
    firstName: contactMap.person.firstName,
    lastName: contactMap.person.lastName,
    middleName: contactMap.person.middleName,
    title: contactMap.person.title})
  MERGE (c)-[:PERSON_IS]->(per)
)

// ===============================
// Create RoleInstance
// ===============================

WITH DISTINCT org
CREATE (ri:RoleInstance)
MERGE (org)-[:HAS_ROLE]->(ri)

// Networks
with DISTINCT org, ri
UNWIND coalesce($networks, []) AS networkMap
// Match to existing Network
MATCH (n:Network)
WHERE elementId(n) = networkMap.elementId
CREATE (rn:RoleNetwork)
MERGE (ri)-[:SERVES]->(rn)
MERGE (rn)-[:NETWORK_IS]->(n)

// Network Locations
with DISTINCT org, ri, networkMap, rn, n
UNWIND coalesce(networkMap.locations, []) AS locationMap
// Match to existing Location
MATCH (loc:Location)
WHERE elementId(loc) = locationMap.elementId
CREATE (rl:RoleLocation)
MERGE (ri)-[:PERFORMED_AT]->(rl)
MERGE (rl)-[:LOCATION_IS]->(loc)

// Specialties
with DISTINCT org, ri, networkMap, rn, n, locationMap, rl
UNWIND coalesce(locationMap.specialties, []) AS specialtyMap
CREATE (s:Specialty {
  specialty: specialtyMap.specialty,
  taxonomy: specialtyMap.taxonomy
})
MERGE (ri)-[:SPECIALIZES]->(s)
MERGE (s)-[:PRACTICED_AT]->(rl)

// Role Location Serves
with DISTINCT org, ri, networkMap, rn, n, locationMap, rl
UNWIND coalesce(locationMap.rls, []) AS rlsMap
MERGE (rl)-[:ROLE_LOCATION_SERVES {
  startDate: rlsMap.rlsStartDate,
  endDate: rlsMap.rlsEndDate}]->(rn)
RETURN org
