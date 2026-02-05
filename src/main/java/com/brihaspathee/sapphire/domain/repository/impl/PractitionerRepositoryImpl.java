package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.PractitionerRepository;
import com.brihaspathee.sapphire.domain.repository.util.*;
import com.brihaspathee.sapphire.model.*;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.RefDataValidationService;
import com.brihaspathee.sapphire.utils.CypherLoader;
import com.brihaspathee.sapphire.utils.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:12 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PractitionerRepositoryImpl implements PractitionerRepository {

    /**
     * Executes Cypher queries against a Neo4j database. This component is responsible
     * for managing read and write operations to the database by leveraging the Neo4j driver.
     * It provides methods for executing parameterized queries and mapping query results
     * to custom domain objects via a functional RecordMapper interface.
     *
     * Used within the repository to perform database operations required for
     * implementing repository methods.
     */
    private final Neo4jQueryExecutor queryExecutor;

    /**
     * A utility used to load Cypher query files from the classpath.
     * This component facilitates retrieving and caching the contents
     * of Cypher query files stored in the application's resources directory,
     * minimizing repeated file I/O operations. Leveraged for retrieving Cypher
     * queries used in database operations by the repository implementation.
     */
    private final CypherLoader cypherLoader;

    /**
     * An instance of {@code RefDataValidationService} used to validate reference data.
     * This service performs validation checks on provided reference data to ensure
     * adherence to specific rules or constraints. It helps in identifying errors,
     * warnings, and issues related to the validity of the data.
     */
    private final RefDataValidationService refDataValidationService;

    /**
     * Retrieves a Practitioner entity based on the provided practitioner code.
     *
     * @param code the unique code associated with the practitioner to be retrieved
     * @return the Practitioner object corresponding to the specified code,
     *         or null if no practitioner is found
     */
    @Override
    public Practitioner findPractitionerByCode(String code) {
        log.info("Retrieving practitioner with code: {}", code);
        log.info("Neo4j Database: {}", queryExecutor.getDatabase());
        String cypher = cypherLoader.load("get_prac_by_code.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("pracCode", code);
        log.info("Practitioner code: {}", code);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Node pracNode = record.get("prac").asNode();
            log.info("Practitioner Node: {}", pracNode);
            Practitioner practitioner = BuildPractitionerEntity.buildPractitioner(pracNode);
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return the Practitioner object corresponding to the specified ID, or null if no practitioner is found
     */
    @Override
    public Practitioner findPractitionerById(String practitionerId) {
        String cypher = cypherLoader.load("get_prac_by_id.cypher");
        log.info("Cypher: {}", cypher);
        Map<String, Object> params = new HashMap<>();
        params.put("pracId", practitionerId);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Value pracInfo = record.get("pracInfo");
            Practitioner practitioner = BuildPractitionerEntity.buildPractitioner(pracInfo);
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID and network ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @param netId          the unique identifier of the network associated with the practitioner
     * @return the Practitioner object corresponding to the specified practitioner ID and network ID,
     * or null if no matching practitioner is found
     */
    @Override
    public Practitioner findPracAndNetByElementId(String practitionerId, String netId) {
        String cypher = cypherLoader.load("get_prac_net_by_id.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("pracId", practitionerId);
        params.put("netId", netId);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Value pracInfo = record.get("pracInfo");
            Practitioner practitioner = BuildPractitionerEntity.buildPractitioner(pracInfo);
            Network network = BuildNetworkEntity.buildNetwork(record.get("netInfo"));
            practitioner.setNetworks(List.of(network));
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID and location ID.
     *
     * @param pracId the unique identifier of the practitioner
     * @param locId the unique identifier of the location associated with the practitioner
     * @return the Practitioner object matching the specified organization ID and location ID,
     * or null if no matching practitioner is found
     */
    @Override
    public Practitioner findPracAndLocByElementId(String pracId, String locId) {
        String cypher = cypherLoader.load("get_prac_loc_by_id.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("pracId", pracId);
        params.put("locId", locId);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Value pracInfo = record.get("pracInfo");
            Practitioner practitioner = BuildPractitionerEntity.buildPractitioner(pracInfo);
            Location location = BuildLocationEntity.buildLocation(record.get("locInfo"));
            practitioner.setLocations(List.of(location));
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID, network ID, and location ID.
     *
     * @param pracId the unique identifier of the practitioner
     * @param netId the unique identifier of the network associated with the practitioner
     * @param locId the unique identifier of the location associated with the practitioner
     * @return the Practitioner object matching the specified organization ID, network ID, and location ID,
     * or null if no matching practitioner is found
     */
    @Override
    public Practitioner findPracAndNetAndLocByElementId(String pracId, String netId, String locId) {
        // Retrieve practitioner, network, and location based on given IDs
        String cypher = cypherLoader.load("get_prac_net_loc_by_id.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("pracId", pracId);
        params.put("netId", netId);
        params.put("locId", locId);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Value pracInfo = record.get("pracInfo");
            Practitioner practitioner = BuildPractitionerEntity.buildPractitioner(pracInfo);
            Value netInfo = record.get("netInfo");
            Network network = BuildNetworkEntity.buildNetwork(netInfo);
            Value locInfo = record.get("locInfo");
            Location location = BuildLocationEntity.buildLocation(locInfo);
            network.setLocations(List.of(location));
            practitioner.setNetworks(List.of(network));
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }

    /**
     * Finds practitioners based on the criteria specified in the PractitionerSearchRequest.
     *
     * @param practitionerSearchRequest the search request containing criteria to filter practitioners
     * @return a list of practitioners matching the search criteria; an empty list if no practitioners are found
     */
    @Override
    public List<Practitioner> findPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        CypherQuery cypherQuery = BuildPractitionerEntity.buildPractitionerSearchCypher(practitionerSearchRequest, true);
        log.info("Practitioner search query: {}", cypherQuery);
        return mapResults(cypherQuery);
    }

    /**
     * Creates a new practitioner in the system using the provided data.
     *
     * @param practitionerDto the DTO containing practitioner details such as
     *                        element ID, code, name, birth date, gender,
     *                        identifiers, and qualifications
     */
    @Override
    public void createPractitioner(PractitionerDto practitionerDto) {
        String cypher = cypherLoader.load("create_practitioner.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("code", RandomStringUtil.generateRandomAlphaNumeric());
        params.put("firstName", practitionerDto.getFirstName());
        params.put("middleName", practitionerDto.getMiddleName());
        params.put("lastName", practitionerDto.getLastName());
        params.put("birthDate", practitionerDto.getBirthDate());
        params.put("gender", practitionerDto.getGender());
        Map<String, List<String>> referenceDataValidations = Map.of(
                "DD_IdentifierType", List.of("NPI", "MCID", "MDID"),
                "DD_OrganizationType", List.of("HOSP", "PPG", "O")
        );
        refDataValidationService.validateRefData(referenceDataValidations);
        List<IdentifierDto> identifiers = practitionerDto.getIdentifiers();
        Map<String, List<Map<String,Object>>> identifierMap = DataExtractor.getIdentifiers(identifiers);
        params.putAll(identifierMap);
//        if(identifiers != null && !identifiers.isEmpty()){
//            for (IdentifierDto identifier: identifiers){
//                switch(identifier.getType()){
//                    case "NPI":
//                        Map<String, Object> npiMap = new HashMap<>();
//                        npiMap.put("value", identifier.getValue());
//                        npiMap.put("startDate", identifier.getStartDate());
//                        npiMap.put("endDate",
//                                Optional.ofNullable(identifier.getEndDate())
//                                        .orElse(LocalDate.of(4000, 1, 1))
//                        );
//                        @SuppressWarnings("unchecked")
//                        List<Map<String, Object>> npiList =
//                                (List<Map<String, Object>>) params.computeIfAbsent(
//                                        "npiList", k -> new ArrayList<>()
//                                );
//
//                        npiList.add(npiMap);
////                        params.put("npiList", List.of(
////                                Map.of("value", "1234567890", "startDate", LocalDate.of(2020,1,1)),
////                                Map.of("value", "9876543210") // second NPI optional dates
////                        ));
//                        break;
//                    case "MEDICARE_ID":
//                        // Medicare identifiers
//                        Map<String, Object> medicareIdMap = new HashMap<>();
//                        medicareIdMap.put("value", identifier.getValue());
//                        @SuppressWarnings("unchecked")
//                        List<Map<String, Object>> medicareList =
//                                (List<Map<String, Object>>) params.computeIfAbsent(
//                                        "medicareList", k -> new ArrayList<>()
//                                );
//
//                        medicareList.add(medicareIdMap);
//                        break;
////                        params.put("medicareList", List.of(
////                                Map.of("value", "MCR12345", "startDate", LocalDate.of(2022,6,1))
////                        ));
//                    case "MEDICAID_ID":
//                        // Medicaid identifiers (state-specific)
//                        Map<String, Object> medicaidIdMap = new HashMap<>();
//                        medicaidIdMap.put("value", identifier.getValue());
//                        medicaidIdMap.put("state", identifier.getAdditionalProperties().get("state"));
//                        medicaidIdMap.put("startDate", identifier.getStartDate());
//                        medicaidIdMap.put("endDate",
//                                Optional.ofNullable(identifier.getEndDate())
//                                        .orElse(LocalDate.of(4000, 1, 1))
//                        );
//                        @SuppressWarnings("unchecked")
//                        List<Map<String, Object>> medicaidList =
//                                (List<Map<String, Object>>) params.computeIfAbsent(
//                                        "medicaidList", k -> new ArrayList<>()
//                                );
//
//                        medicaidList.add(medicaidIdMap);
//                        break;
////                        params.put("medicaidList", List.of(
////                                Map.of("value", "MD123", "state", "NY", "startDate", LocalDate.of(2021,1,1)),
////                                Map.of("value", "MD456", "state", "CA")
////                        ));
//
//                }
//            }
//
//        }
//        // NPI identifiers
//        params.put("npiList", List.of(
//                Map.of("value", "1234567890", "startDate", LocalDate.of(2020,1,1)),
//                Map.of("value", "9876543210") // second NPI optional dates
//        ));
//
//        // Medicare identifiers
//        params.put("medicareList", List.of(
//                Map.of("value", "MCR12345", "startDate", LocalDate.of(2022,6,1))
//        ));
//
//        // Medicaid identifiers (state-specific)
//        params.put("medicaidList", List.of(
//                Map.of("value", "MD123", "state", "NY", "startDate", LocalDate.of(2021,1,1)),
//                Map.of("value", "MD456", "state", "CA")
//        ));

        // Contracted Organizations
        List<Map<String, Object>> contractedOrgs = new ArrayList<>();
        for (OrganizationDto contractedOrg: practitionerDto.getContractedOrganizations()){
            Map<String, Object> orgMap = new HashMap<>();
            orgMap.put("elementId", contractedOrg.getElementId());
            if(contractedOrg.getContacts() != null && !contractedOrg.getContacts().isEmpty()){
                // Practitioner Organization Contacts
                List<Map<String, Object>> contacts = getContacts(contractedOrg.getContacts());
                orgMap.put("contacts", contacts);
            }
            // Check to see if there are specialties of the practitioner associated with the organization
            if (contractedOrg.getSpecialties() != null && !contractedOrg.getSpecialties().isEmpty()){
                List<Map<String, Object>> specialties = getSpecialties(contractedOrg.getSpecialties());
                orgMap.put("specialties", specialties);
            }
            // Networks
            if (contractedOrg.getNetworks() != null && !contractedOrg.getNetworks().isEmpty()){
                List<Map<String, Object>> networks = getNetworks(contractedOrg.getNetworks());
                orgMap.put("networks", networks);
            }
            contractedOrgs.add(orgMap);
        }
        params.put("organizationList", contractedOrgs);

        queryExecutor.executeWriteQuery(cypher, params);
    }

    /**
     * Maps the results of a Cypher query to a list of Practitioner objects.
     *
     * @param cypherQuery the CypherQuery object containing the query string and parameters
     * @return a list of Practitioner objects constructed from the query results
     */
    private List<Practitioner> mapResults(CypherQuery cypherQuery) {
        return queryExecutor.executeReadQuery(cypherQuery.getCypher(), cypherQuery.getParams(), record -> {
            Node node = record.get("prac").asNode();
            Practitioner prac = BuildPractitionerEntity.buildPractitioner(node);
            List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
            List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
            prac.setIdentifiers(identifiers);
            return prac;
        });
    }

    /**
     * Converts a list of ContactDto objects into a list of maps containing structured contact data.
     * The method processes each contact object to extract details about the usage (use),
     * address, telecommunications, and personal information and organizes them into
     * separate maps which are then added to the result list.
     *
     * @param contactList the list of ContactDto objects representing contacts. Each object
     *                    contains details about the contact's usage, address, telecommunications,
     *                    and personal information.
     * @return a list of maps where each map represents a contact with its structured details.
     *         The map includes keys such as "use", "address", "telecom", and "person",
     *         each containing data extracted from the provided ContactDto objects.
     */
    private List<Map<String, Object>> getContacts(List<ContactDto> contactList) {
        List<Map<String, Object>> contacts = new ArrayList<>();
        for (ContactDto contact : contactList) {
            Map<String, Object> contactMap = new HashMap<>();
            contactMap.put("use", contact.getUse());
            Map<String, Object> address = new HashMap<>();
            address.put("streetAddress", contact.getAddress().getStreetAddress());
            address.put("secondaryAddress", contact.getAddress().getSecondaryAddress());
            address.put("city", contact.getAddress().getCity());
            address.put("state", contact.getAddress().getState());
            address.put("zipCode", contact.getAddress().getZipCode());
            address.put("county", contact.getAddress().getCounty());
            address.put("countyFIPS", contact.getAddress().getCountyFIPS());
            contactMap.put("address", address);

            Map<String, Object> telecom = new HashMap<>();
            telecom.put("phone", contact.getTelecom().getPhone());
            telecom.put("tty", contact.getTelecom().getTty());
            telecom.put("afterHoursNumber", contact.getTelecom().getAfterHoursNumber());
            telecom.put("fax", contact.getTelecom().getFax());
            telecom.put("email", contact.getTelecom().getEmail());
            telecom.put("website", contact.getTelecom().getWebsite());
            contactMap.put("telecom", telecom);

            Map<String, Object> person = new HashMap<>();
            person.put("firstName", contact.getPerson().getFirstName());
            person.put("lastName", contact.getPerson().getLastName());
            person.put("middleName", contact.getPerson().getMiddleName());
            person.put("title", contact.getPerson().getTitle());
            contactMap.put("person", person);
            contacts.add(contactMap);
        }
        return contacts;
    }

    /**
     * Converts a list of NetworkDto objects into a structured representation as a list of maps.
     * Each map contains information about the network, its associated locations, and related data.
     *
     * @param networkList the list of NetworkDto objects to be converted. Each object contains data about
     *                    networks including their element ID, locations, and location networks.
     * @return a list of maps where each map represents a network and includes keys such as "elementId"
     *         and "locations". The "locations" key contains a list of maps, each representing a location
     *         with keys like "elementId" and "rls", where "rls" contains details about role location
     *         serves including start and end dates.
     */
    private List<Map<String, Object>> getNetworks(List<NetworkDto> networkList) {
        List<Map<String, Object>> networks = new ArrayList<>();
        for (NetworkDto network: networkList){
            Map<String, Object> netMap = new HashMap<>();
            netMap.put("elementId", network.getElementId());
            if (network.getLocations() != null && !network.getLocations().isEmpty()){
                List<Map<String, Object>> locations = getLocations(network.getLocations());
                netMap.put("locations", locations);
            }
            networks.add(netMap);
        }
        return networks;
    }

    /**
     * Converts a list of LocationDto objects into a list of maps containing structured location data.
     * The method processes each location object to extract details about its element ID, location network,
     * and spans, and organizes them into a structured format for further use.
     *
     * @param networkList the list of LocationDto objects to be converted.
     *                    Each object contains information about a location and its associated network.
     * @return a list of maps where each map represents a location, including keys such as "elementId" and "rls".
     *         The "rls" key contains a list of maps, each representing role location serves with details like
     *         "rlsStartDate" and "rlsEndDate".
     */
    private List<Map<String, Object>> getLocations(List<LocationDto> networkList) {
        List<Map<String, Object>> locations = new ArrayList<>();
        for (LocationDto location: networkList){
            Map<String, Object> locMap = new HashMap<>();
            locMap.put("elementId", location.getElementId());
            if (location.getLocationNetwork() != null){
                LocationNetworkDto locationNetworkDto = location.getLocationNetwork();
                if (locationNetworkDto.getSpans() != null && !locationNetworkDto.getSpans().isEmpty()){
                    List<Map<String, Object>> roleLocationServes = new ArrayList<>();
                    for (LocationNetworkSpanDto span: locationNetworkDto.getSpans()){
                        Map<String, Object> rlsMap = new HashMap<>();
                        rlsMap.put("rlsStartDate", span.getStartDate());
                        rlsMap.put("rlsEndDate", span.getEndDate());
                        roleLocationServes.add(rlsMap);
                    }
                    locMap.put("rls", roleLocationServes);
                }
            }
            locations.add(locMap);
        }
        return locations;
    }

    /**
     * Converts a list of SpecialtyDto objects into a structured representation as a list of maps.
     * Each map contains details about the specialty, taxonomy code, and whether it is the primary specialty.
     *
     * @param specialtyList the list of SpecialtyDto objects to be converted. Each object contains
     *                      information about a specialty, including its name, taxonomy code, and primary status.
     * @return a list of maps where each map represents a specialty. Keys in the map include:
     *         "specialty" (the name of the specialty), "taxonomy" (the taxonomy code),
     *         and "isPrimary" (a boolean indicating if it is the primary specialty).
     */
    private List<Map<String, Object>> getSpecialties(List<SpecialtyDto> specialtyList) {
        List<Map<String, Object>> specialties = new ArrayList<>();
        for (SpecialtyDto specialty: specialtyList){
            Map<String, Object> specialtyMap = new HashMap<>();
            specialtyMap.put("specialty", specialty.getSpecialty());
            specialtyMap.put("taxonomy", specialty.getTaxonomyCode());
            specialtyMap.put("isPrimary", specialty.getIsPrimary());
            specialties.add(specialtyMap);
        }
        return specialties;
    }

}
