WITH $pracId AS practitionerId

MATCH (prac:Practitioner)
WHERE elementId(prac) = practitionerId

CALL (prac) {
  WITH prac
  OPTIONAL MATCH (prac)-[rel]->(id:Identifier)
  WITH prac,
       collect(DISTINCT { relType: type(rel), node: id }) AS identifiers

  OPTIONAL MATCH (prac)-[:HAS_QUALIFICATION]->(qual:Qualification)
  WITH identifiers,
       collect(DISTINCT qual) AS qualifications

  RETURN {
    identifiers: identifiers,
    qualifications: qualifications
  } AS pracDetails
}
MATCH (prac)-[:HAS_ROLE]->(ri:RoleInstance)-[:CONTRACTED_BY]->(org:Organization)

CALL (ri, org) {
  /* -------------------------
     RoleInstance contacts
     ------------------------- */
  OPTIONAL MATCH (ri)-[:HAS_PRACTITIONER_CONTACT]->(c:Contact)
  OPTIONAL MATCH (c)-[:PERSON_IS]->(p:Person)
  OPTIONAL MATCH (c)-[:ADDRESS_IS]->(a:Address)
  OPTIONAL MATCH (c)-[:TELECOM_IS]->(t:Telecom)
  WITH ri, org,
       collect(DISTINCT CASE
         WHEN c IS NOT NULL OR p IS NOT NULL OR a IS NOT NULL OR t IS NOT NULL
         THEN { contact: c, person: p, address: a, telecom: t }
       END) AS contacts

  /* -------------------------
     Specialties
     ------------------------- */
  OPTIONAL MATCH (ri)-[:SPECIALIZES]->(s:Specialty)
  WITH ri, org, contacts,
       collect(DISTINCT s) AS specialties
  /* -------------------------
     Networks (LIMIT per RI)
     ------------------------- */
  CALL (ri){
    WITH ri
    OPTIONAL MATCH (ri)-[:SERVES]->(rn:RoleNetwork)-[:NETWORK_IS]->(net:Network)
    ORDER BY net.code ASC
    LIMIT 10
    RETURN rn, net
  }
  /* -------------------------
     Locations per network
     ------------------------- */
  OPTIONAL MATCH (rn)<-[rls:ROLE_LOCATION_SERVES]-(rl:RoleLocation)
                 -[:LOCATION_IS]->(loc:Location)

  CALL (loc) {
    WITH loc
    OPTIONAL MATCH (loc)-[idRel]->(id:Identifier)
    OPTIONAL MATCH (loc)-[:ACCESSIBLE]->(acc:Accessibility)
    RETURN {
      identifiers: collect(DISTINCT { relType: type(idRel), node: id }),
      accessibilities: collect(DISTINCT acc)
    } AS locDetails
  }

  CALL (rl) {
    WITH rl
    OPTIONAL MATCH (rl)-[:HAS_LOCATION_CONTACT]->(c:Contact)
    OPTIONAL MATCH (c)-[:PERSON_IS]->(p:Person)
    OPTIONAL MATCH (c)-[:ADDRESS_IS]->(a:Address)
    OPTIONAL MATCH (c)-[:TELECOM_IS]->(t:Telecom)
    RETURN collect(DISTINCT CASE
      WHEN c IS NOT NULL OR p IS NOT NULL OR a IS NOT NULL OR t IS NOT NULL
      THEN { contact: c, person: p, address: a, telecom: t }
    END) AS locContacts
  }
  WITH ri, org, contacts, specialties, net,
       collect(DISTINCT {
         loc: loc,
         locDetails: locDetails,
         roleLocationServes: rls,
         locContacts: locContacts
       }) AS locations

  CALL (net) {
    WITH net
    OPTIONAL MATCH (net)-[:PART_OF]->(prod:Product)
    OPTIONAL MATCH (net)-[:PART_OF]->(lob:LineOfBusiness)
    RETURN {
      products: collect(DISTINCT prod),
      lobs: collect(DISTINCT lob)
    } AS netDetails
  }

  WITH ri, org, contacts, specialties,
       collect(DISTINCT {
         net: net,
         netDetails: netDetails,
         locations: locations
       }) AS networks
  RETURN {
    ri: ri,
    org: org,
    contacts: contacts,
    specialties: specialties,
    networks: networks
  } AS roleInstance
}
WITH prac, pracDetails, roleInstance
WITH prac, pracDetails, collect(roleInstance) AS roleInstanceDetails
RETURN {
  prac: prac,
  pracDetails: pracDetails,
  roleInstanceDetails: roleInstanceDetails
} AS pracInfo