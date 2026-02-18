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
import lombok.Data;
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

        // Identifiers
        List<IdentifierDto> identifiers = practitionerDto.getIdentifiers();
        Map<String, List<Map<String,Object>>> identifierMap = DataExtractor.getIdentifiers(identifiers);
        params.putAll(identifierMap);

        // Credentials
        List<Map<String,Object>> creds = DataExtractor.getCredentials(practitionerDto.getCredentials());
        params.put("credentials", creds);

        // Languages
        List<Map<String,Object>> languages = DataExtractor.getLanguages(practitionerDto.getLanguages());
        params.put("languages", languages);

        // Hospital Privileges
        List<Map<String,Object>> privileges = DataExtractor.getHospitalPrivileges(practitionerDto.getHospitalPrivileges());
        params.put("privileges", privileges);

        // Qualifications
        List<Map<String,Object>> qualifications = DataExtractor.getQualifications(practitionerDto.getQualifications());
        params.put("qualifications", qualifications);

        // Contracted Organizations
        List<Map<String, Object>> contractedOrgs = new ArrayList<>();
        for (OrganizationDto contractedOrg: practitionerDto.getContractedOrganizations()){
            Map<String, Object> orgMap = new HashMap<>();
            orgMap.put("elementId", contractedOrg.getElementId());

            // Disorders
            List<Map<String, Object>> disorders = DataExtractor.getDisorders(contractedOrg.getDisorders());
            orgMap.put("disorders", disorders);

            // Healthcare Services
            List<Map<String, Object>> healthcareServices = DataExtractor.getHealthcareServices(contractedOrg.getHealthcareServices());
            orgMap.put("healthcareServices", healthcareServices);

            if(contractedOrg.getContacts() != null && !contractedOrg.getContacts().isEmpty()){
                // Practitioner Organization Contacts
                List<Map<String, Object>> contacts = DataExtractor.getContacts(contractedOrg.getContacts());
                orgMap.put("contacts", contacts);
            }
            // Check to see if there are specialties of the practitioner associated with the organization
            if (contractedOrg.getSpecialties() != null && !contractedOrg.getSpecialties().isEmpty()){
                List<Map<String, Object>> specialties = DataExtractor.getSpecialties(contractedOrg.getSpecialties());
                orgMap.put("specialties", specialties);
            }
            // Networks
            if (contractedOrg.getNetworks() != null && !contractedOrg.getNetworks().isEmpty()){
                List<Map<String, Object>> networks = DataExtractor.getNetworks(contractedOrg.getNetworks());
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
}
