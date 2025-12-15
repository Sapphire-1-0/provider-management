MATCH (net1:Network) WHERE net1.code = "2650"
MATCH (net2:Network) WHERE net2.code = "5296"
MATCH (net3:Network) WHERE net3.code = "8956"
MATCH (net4:Network) WHERE net4.code = "14347"
MATCH (net5:Network) WHERE net5.code = "14593"

MATCH(org1_loc1:Location) where org1_loc1.code = "RVLOC000CA001"
MATCH(org1_loc2:Location) where org1_loc2.code = "RVLOC000CA002"


MATCH (org1:Organization) WHERE org1.code = "RVMP1234567890"



CREATE (prac:Practitioner {
  code: "R5K8T2Q9L1W3X7Z",
  firstName: "Jonathan",
  middleName: "Paul",
  lastName: "Edwards",
  gender: "M",
  postNominalLetters: "MD",
  birthDate: date("1980-04-22")
})


CREATE (npi:Identifier:NPI {
  value: "1234567890",
  startDate: date("2005-01-01"),
  endDate: date("4000-01-01")
})
CREATE (prac)-[:HAS_NPI]->(npi)


CREATE (medicare:Identifier:MedicareID {
  value: "MCR9876543",
  startDate: date("2010-05-01"),
  endDate: date("4000-01-01")
})
CREATE (prac)-[:HAS_MEDICARE_ID]->(medicare)


CREATE (medicaid:Identifier:MedicaidID {
  value: "MCD1234567",
  state: "AL",
  startDate: date("2012-03-15"),
  endDate: date("4000-01-01")
})
CREATE (prac)-[:HAS_MEDICAID_ID]->(medicaid)


CREATE (license_1:Qualification:License {
  type: "LICE_A_MLC",
  status: "ACTIVE",
  state: "CA",
  value: "1234567890",
  startDate: date("2005-01-01"),
  endDate: date("4000-01-01")
})

CREATE (prac)-[:HAS_QUALIFICATION]->(license_1)

CREATE (certification_1:Qualification:Certification {
  type: "CERT_CCS",
  issuer: "California Children Services",
  startDate: date("2016-01-01"),
  endDate: date("4000-01-01")
})

CREATE (prac)-[:HAS_QUALIFICATION]->(certification_1)

CREATE (prac_contact_1:Contact {use:"BILLING"})
CREATE (prac_contact_telecom_1:Telecom {phone:"813-357-6342", tty:"813-357-6343", fax:"813-357-6344", email:"vbalaji215@example.com", website:"https://www.RVLOC000CA001.com"})
CREATE (prac_contact_1)-[:TELECOM_IS]->(prac_contact_telecom_1)
CREATE (prac_contact_person_1:Person {firstName:"Jamie", middleName:"M", lastName:"Smith"})
CREATE (prac_contact_1)-[:PERSON_IS]->(prac_contact_person_1)
CREATE (prac_contact_address_1:Address {streetAddress:"13377 Batten Lane", city:"Odessa", state:"FL", zipCode:"33556"})
CREATE (prac_contact_1)-[:ADDRESS_IS]->(prac_contact_address_1)

CREATE (prac_contact_2:Contact {use:"MAILING"})
CREATE (prac_contact_telecom_2:Telecom {phone:"813-357-6345", tty:"813-357-6346", fax:"813-357-6347", email:"vbalaji215@example.com", website:"https://www.RVLOC000CA001.com"})
CREATE (prac_contact_2)-[:TELECOM_IS]->(prac_contact_telecom_2)
CREATE (prac_contact_person_2:Person {firstName:"Jacky", middleName:"M", lastName:"Smith"})
CREATE (prac_contact_2)-[:PERSON_IS]->(prac_contact_person_2)
CREATE (prac_contact_address_2:Address {streetAddress:"P.O. Box 3423", city:"Odessa", state:"FL", zipCode:"33556"})
CREATE (prac_contact_2)-[:ADDRESS_IS]->(prac_contact_address_2)


CREATE (prac)-[:HAS_ROLE]->(ri1:RoleInstance)
CREATE (ri1)-[:CONTRACTED_BY]->(org1)
CREATE (spec_1:Specialty {
  specialty: "Dermatology",
  taxonomy: "207N00000X"
})
CREATE(ri1)-[:SPECIALIZES]->(spec_1)
CREATE(ri1)-[:HAS_PRACTITIONER_CONTACT]->(prac_contact_1)
CREATE(ri1)-[:HAS_PRACTITIONER_CONTACT]->(prac_contact_2)


CREATE (rn1: RoleNetwork)
CREATE (ri1)-[:SERVES]->(rn1)
CREATE (rn1)-[:NETWORK_IS]->(net1)

CREATE (rn2: RoleNetwork)
CREATE (ri1)-[:SERVES]->(rn2)
CREATE (rn2)-[:NETWORK_IS]->(net2)

CREATE (rl1: RoleLocation)
CREATE (ri1)-[:PERFORMED_AT]->(rl1)
CREATE (rl1)-[:LOCATION_IS]->(org1_loc1)
CREATE (c1:Contact {use:"DIR"})
CREATE (t1:Telecom {phone:"813-357-9150", tty:"813-357-9151", fax:"813-357-9152", email:"vbalaji215@example.com", website:"https://www.RVLOC000CA001.com"})
CREATE (c1)-[:TELECOM_IS]->(t1)
CREATE (p1:Person {firstName:"Balaji", middleName:"M", lastName:"Varadharajan", title:"Office Assistant"})
CREATE (c1)-[:PERSON_IS]->(p1)
CREATE (rl1)-[:HAS_LOCATION_CONTACT]->(c1)

CREATE (rl2: RoleLocation)
CREATE (ri1)-[:PERFORMED_AT]->(rl2)
CREATE (rl2)-[:LOCATION_IS]->(org1_loc2)
CREATE (c2:Contact {use:"DIR"})
CREATE (t2:Telecom {phone:"813-357-9153", tty:"813-357-9154", fax:"813-357-9155", email:"vbalaji216@example.com", website:"https://www.RVLOC000CA001.com"})
CREATE (c2)-[:TELECOM_IS]->(t2)
CREATE (p2:Person {firstName:"Pooja", middleName:"M", lastName:"Mohanakrishnan", title:"Office Assistant"})
CREATE (c2)-[:PERSON_IS]->(p2)
CREATE (rl2)-[:HAS_LOCATION_CONTACT]->(c2)

CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn1)
CREATE (rl1)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
CREATE (rl2)-[:ROLE_LOCATION_SERVES {startDate: date("2024-01-01"), endDate: date("4000-01-01")}]->(rn2)
