// ===============================
// Create Practitioner
// ===============================

CREATE (p:Practitioner {
  code: $code,
  firstName: $firstName,
  lastName: $lastName
})
SET p += {
  middleName: $middleName,
  gender: $gender,
  birthDate: $birthDate
}
// ===============================
// Identifiers
// ===============================

// NPI
WITH p
UNWIND $npiList AS npiMap
MERGE (npi:NPI:Identifier {value: npiMap.value})
SET npi += {
  startDate: npiMap.startDate,
  endDate: npiMap.endDate
}
MERGE (p)-[:HAS_NPI]->(npi)

// Medicare
WITH p
UNWIND $medicareList as medicareMap
MERGE (medicareId: MedicareID: Identifier {value: medicareMap.value})
MERGE (p)-[:HAS_MEDICARE_ID]->(medicareId)

// MedicaidID
WITH p
UNWIND $medicaidList as medicaidMap
MERGE (medicaid: MedicaidID: Identifier {
    value: medicaidMap.value,
    state: medicaidMap.state
})
SET medicaid += {
  startDate: medicaidMap.startDate,
  endDate: medicaidMap.endDate
}
MERGE (p)-[:HAS_MEDICAID_ID]->(medicaid)

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
// Languages
// ===============================

with DISTINCT p
UNWIND coalesce($languages, []) AS languageMap
CREATE (lang:Language {
  value: languageMap.value
})
MERGE (p)-[:COMMUNICATES]->(lang)

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
// Hospital Privileges
// ===============================

with DISTINCT p
UNWIND coalesce($privileges, []) AS privilegeMap
MATCH (prvOrg:Organization)
WHERE elementId(prvOrg) = privilegeMap.organizationElementId

MERGE (p)-[:HAS_PRIVILEGE{
  type: privilegeMap.type
}]->(prvOrg)

// ===============================
// Organization + RoleInstance + Contacts
// ===============================

WITH DISTINCT p
UNWIND coalesce($organizationList, []) AS orgMap

// Match to an existing Organization
MATCH (org:Organization)
WHERE elementId(org) = orgMap.elementId

// RoleInstance (one per Practitioner-Organization pair)
CREATE (ri:RoleInstance)
MERGE (p)-[:HAS_ROLE]->(ri)
MERGE (ri)-[:CONTRACTED_BY]->(org)

// Specialties
with DISTINCT p, ri, orgMap
UNWIND coalesce(orgMap.specialties, []) AS specialtyMap
CREATE (s:Specialty {
  specialty: specialtyMap.specialty,
  taxonomy: specialtyMap.taxonomy
})
MERGE (ri)-[:SPECIALIZES]->(s)

// Conditionally create PRIMARY edge
FOREACH (_ IN CASE
  WHEN specialtyMap.isPrimary = true OR specialtyMap.isPrimary = 'Y'
  THEN [1]
  ELSE []
END |
  MERGE (ri)-[:PRIMARY_SPECIALTY_IS]->(s)
)

// Disorders
with DISTINCT p, ri, orgMap
UNWIND coalesce(orgMap.disorders, []) AS disorderMap
CREATE (disorder:Disorder {
  type: disorderMap.type
})
MERGE (ri)-[:TREATS_DISORDER]->(disorder)

// Healthcare Services
with DISTINCT p, ri, orgMap
UNWIND coalesce(orgMap.healthcareServices, []) AS healthcareServiceMap
MERGE (hs:HealthcareService {type: healthcareServiceMap.type})
SET hs += {
  type: healthcareServiceMap.type,
  state: healthcareServiceMap.state,
  servicePopulation: healthcareServiceMap.servicePopulation,
  startDate: healthcareServiceMap.startDate,
  endDate: healthcareServiceMap.endDate
}
MERGE (ri)-[:OFFERS_SERVICE]->(hs)


// Contacts
with DISTINCT p, ri, orgMap
UNWIND coalesce(orgMap.contacts, []) AS contactMap
CREATE (c:Contact {
  use: contactMap.use
})
MERGE (ri)-[:HAS_PRACTITIONER_CONTACT]->(c)
// Contact Address
WITH DISTINCT p, ri, orgMap, c, contactMap
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
WITH DISTINCT p, ri, orgMap, c, contactMap
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
WITH DISTINCT p, ri, orgMap, c, contactMap
FOREACH (_ IN CASE WHEN contactMap.person IS NOT NULL THEN [1] ELSE [] END |
  CREATE (per:Person {
    firstName: contactMap.person.firstName,
    lastName: contactMap.person.lastName,
    middleName: contactMap.person.middleName,
    title: contactMap.person.title})
  MERGE (c)-[:PERSON_IS]->(per)
)

// Networks
with DISTINCT p, ri, orgMap
UNWIND coalesce(orgMap.networks, []) AS networkMap
// Match to existing Network
MATCH (n:Network)
WHERE elementId(n) = networkMap.elementId
CREATE (rn:RoleNetwork)
MERGE (ri)-[:SERVES]->(rn)
MERGE (rn)-[:NETWORK_IS]->(n)

// Network Locations
with DISTINCT p, ri, orgMap, networkMap, rn, n
UNWIND coalesce(networkMap.locations, []) AS locationMap
// Match to existing Location
MATCH (loc:Location)
WHERE elementId(loc) = locationMap.elementId
CREATE (rl:RoleLocation)
MERGE (ri)-[:PERFORMED_AT]->(rl)
MERGE (rl)-[:LOCATION_IS]->(loc)

// Role Location Serves
with DISTINCT p, ri, orgMap, networkMap, rn, n, locationMap, rl
UNWIND coalesce(locationMap.rls, []) AS rlsMap
MERGE (rl)-[:ROLE_LOCATION_SERVES {
  startDate: rlsMap.rlsStartDate,
  endDate: rlsMap.rlsEndDate}]->(rn)
RETURN p
