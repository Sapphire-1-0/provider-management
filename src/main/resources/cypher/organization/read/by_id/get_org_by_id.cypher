WITH $orgId AS organizationId

MATCH (org:Organization)
  WHERE elementId(org) = organizationId


MATCH (org)-[:HAS_ROLE]->(ri:RoleInstance)

// ---- Organization details
CALL (org, ri) {
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

  OPTIONAL CALL (org, ri) {
  WITH org, ri
  WHERE ri IS NOT NULL

  OPTIONAL MATCH (ri)-[:SERVES]->(rn:RoleNetwork)-[:NETWORK_IS]->(net:Network)
  WITH org, ri, rn, net
  ORDER BY net.code ASC
  LIMIT 10
  OPTIONAL MATCH (rn)<-[rls:ROLE_LOCATION_SERVES]-(rl:RoleLocation)-[:LOCATION_IS]->(loc:Location)

  // ---- Location details
  OPTIONAL CALL (loc) {
    WITH loc
    OPTIONAL MATCH (loc)-[locRel]->(id:Identifier)
    OPTIONAL MATCH (loc)-[:ACCESSIBLE]->(acc:Accessibility)
    RETURN
      collect(DISTINCT { relType: type(locRel), node: id }) AS locIdentifiers,
      collect(DISTINCT acc) AS accessibilities
  }

  OPTIONAL CALL (rl) {
    WITH rl
    WHERE rl IS NOT NULL
    OPTIONAL MATCH (rl)-[:HAS_LOCATION_CONTACT]->(contact:Contact)
    OPTIONAL MATCH (contact)-[:PERSON_IS]->(person:Person)
    OPTIONAL MATCH (contact)-[:ADDRESS_IS]->(address:Address)
    OPTIONAL MATCH (contact)-[:TELECOM_IS]->(telecom:Telecom)
    WITH contact, person, telecom, address
    RETURN collect(
      DISTINCT CASE
        WHEN contact IS NOT NULL OR
             person IS NOT NULL OR
             telecom IS NOT NULL OR
             address IS NOT NULL
        THEN {
          contact: contact,
          person: person,
          telecom: telecom,
          address: address
        }
        ELSE null
      END
    ) AS locContacts
  }

  WITH rn, net, rl, loc, rls,
       {
         identifiers: locIdentifiers,
         contacts: locContacts,
         accessibilities: accessibilities
       } AS locDetails

  // ---- Group locations under each network

  // group locations under each (roleNetwork + network)
    WITH rn, net,
        collect(DISTINCT {
          loc: loc,
          roleLocationServes: rls,
          locDetails: locDetails
        }) AS locations
    // 🔹 Network-level details
    CALL (net) {
      WITH net
      OPTIONAL MATCH (net)-[:PART_OF]->(prod:Product)
      OPTIONAL MATCH (net)-[:PART_OF]->(lob:LineOfBusiness)
      RETURN {
        products: collect(DISTINCT prod),
        lobs: collect(DISTINCT lob)
      } AS netDetails
    }
    RETURN collect( DISTINCT {
          net: net,
          netDetails: netDetails,
          locations: locations
      }) AS networks
  }

  RETURN COLLECT(DISTINCT{
    identifiers: identifiers,
    qualifications: qualifications,
    contacts: contacts,
    networks: networks
  }) as orgDetails
}
RETURN {
          org: org,
          orgDetails: orgDetails
       } as orgInfo
