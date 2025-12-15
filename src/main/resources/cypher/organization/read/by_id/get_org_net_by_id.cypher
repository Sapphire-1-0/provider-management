WITH $orgId AS organizationId,
     $netId AS networkId

MATCH (org:Organization)
  WHERE elementId(org) = organizationId

MATCH (net:Network)
  WHERE elementId(net) = networkId

MATCH (org)-[:HAS_ROLE]->(ri:RoleInstance)

// Network Details
CALL (net) {
  WITH net
  OPTIONAL MATCH (net)-[:PART_OF]->(prod:Product)
  OPTIONAL MATCH (net)-[:PART_OF]->(lob:LineOfBusiness)
  return {
         products: COLLECT(DISTINCT prod),
         lobs: COLLECT(DISTINCT lob)
        } as netDetails
}

// ---- Organization details
CALL (org) {
  WITH org
  WHERE org IS NOT NULL
  OPTIONAL CALL (org){
    OPTIONAL MATCH (org)-[:HAS_ORGANIZATION_CONTACT]->(contact:Contact)
    OPTIONAL MATCH (contact)-[:PERSON_IS]->(person:Person)
    OPTIONAL MATCH (contact)-[:ADDRESS_IS]->(address:Address)
    OPTIONAL MATCH (contact)-[:TELECOM_IS]->(telecom:Telecom)
    WITH contact, person, telecom, address
    RETURN COLLECT(
        DISTINCT {
          contact: contact,
          person: person,
          telecom: telecom,
          address: address
        }
      ) AS contacts
  }

  OPTIONAL CALL (org) {
    WITH org
    OPTIONAL MATCH (org)-[orgRel]->(id:Identifier)
    RETURN collect(DISTINCT { relType: type(orgRel  ), node: id }) AS identifiers
  }

  OPTIONAL CALL (org) {
    WITH org
    OPTIONAL MATCH (org)-[:HAS_QUALIFICATION]->(qual:Qualification)
    RETURN collect(DISTINCT qual) AS qualifications
  }
  RETURN {
    identifiers: identifiers,
    qualifications: qualifications,
    contacts: contacts
  } as orgDetails
}
RETURN {
          org: org,
          orgDetails: orgDetails
       } as orgInfo,
      {
          net: net,
          netDetails: netDetails
      } as netInfo
