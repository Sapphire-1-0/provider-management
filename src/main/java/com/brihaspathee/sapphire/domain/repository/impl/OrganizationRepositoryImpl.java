package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.LocationRepository;
import com.brihaspathee.sapphire.domain.repository.interfaces.NetworkRepository;
import com.brihaspathee.sapphire.domain.repository.interfaces.OrganizationRepository;
import com.brihaspathee.sapphire.domain.repository.util.BuilderUtil;
import com.brihaspathee.sapphire.domain.repository.util.CypherQuery;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.utils.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 15:09
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    /**
     * Responsible for executing Neo4j database queries within the repository.
     * Utilized to perform both read and write operations by constructing
     * Cypher queries with specified parameters and mapping the results.
     * Ensures direct database interactions are abstracted and encapsulated.
     */
    private final Neo4jQueryExecutor queryExecutor;

    /**
     * Utility class used to manage and load Cypher query files.
     * It provides functionality to cache Cypher files for faster access
     * and dynamically load Cypher query content based on the file name.
     *
     * This instance of CypherLoader is utilized within the repository
     * to retrieve and map Cypher queries used for database operations,
     * ensuring efficient query management and execution.
     */
    private final CypherLoader cypherLoader;

    /**
     * Manages operations related to network retrieval and associations within the organization.
     * The {@code networkRepository} is an instance of the {@link NetworkRepository} interface
     * and provides access to methods that facilitate interactions with network-related data,
     * including searching for networks and retrieving networks associated with specific organizations
     * or locations. This component acts as a dependency in the data access layer.
     */
    private final NetworkRepository networkRepository;

    /**
     * This variable serves as a dependency for interacting with the `LocationRepository` interface.
     * The `LocationRepository` is responsible for accessing and managing location-related data
     * stored in the underlying database. It provides methods for retrieving organizations and their
     * associated locations based on various criteria, such as organization ID, network ID, or custom
     * search requests.
     *
     * The `locationRepository` is utilized within the `OrganizationRepositoryImpl` class to
     * implement functionality related to location data as part of the broader organization data
     * management operations.
     */
    private final LocationRepository locationRepository;

    /**
     * Retrieves a list of organizations by their unique code.
     * This method executes a Cypher query to search for organizations
     * in the database that match the specified code.
     *
     * @param code the unique code used to identify the organizations
     * @return a list of organizations that match the specified code
     */
    @Override
    public List<Organization> findByCode(String code) {
        String cypher = cypherLoader.load("get_org_by_code.cypher");
        Map<String, Object> params = new HashMap<>(Map.of("code", code));
        params.put("orgCode", code);
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, params,
                record -> {
                    Node node = record.get("o").asNode();
                    Organization org = BuilderUtil.buildOrganization(node);
                    return org;
                });
        return organizations;
    }

    /**
     * Retrieves a list of all organizations from the data repository.
     * This method utilizes a Cypher query to fetch organization details
     * and maps the results to a list of Organization objects.
     *
     * @return a list of all Organization objects available in the repository
     */
    @Override
    public List<Organization> findAll() {
        log.info("Fetching all organizations inside the repository method");
        String cypher = cypherLoader.load("get_all_organizations.cypher");
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, Map.of(),
                record -> {
                        Node node = record.get("n").asNode();
                        log.info("Organization name: {}", node.get("name").asString());
                        log.info("Element id of the Org:{}", node.elementId());
                        Organization org = BuilderUtil.buildOrganization(node);
                        return org;
                });
        return organizations;
    }

    public List<Organization> findAllWithIdentifiers() {
//        String cypher = cypherLoader.load("get_organizations_with_identifiers.cypher");
        List<Organization> organizations =
                queryExecutor.executeReadQuery(
                        cypherLoader.load("get_organizations_with_identifiers.cypher"),
                        Map.of(),
                        record -> {
                           Node orgNode = record.get("o").asNode();
                           Organization org =
                                   BuilderUtil.buildOrganization(orgNode);
                           List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
                           List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
                           org.setIdentifiers(identifiers);
                           return org;
                        });
        return organizations;
    }

    /**
     * Retrieves a list of organizations based on the given identifiers.
     * If the identifiers map is null or empty, returns all organizations.
     * The method builds a query to match organizations in the database
     * based on the provided identifiers and whether all identifiers must match.
     *
     * @param identifiers a map of identifier keys and their corresponding values
     *                    used to filter the organizations
     * @param matchAll    a boolean indicating whether all identifiers must match
     *                    (true for AND condition, false for OR condition)
     * @return a list of matching Organization objects
     */
    public List<Organization> findAllByIdentifier(Map<String, String> identifiers, boolean matchAll) {
        if (identifiers == null || identifiers.isEmpty()) {
            return findAll();
        }
        CypherQuery cypherQuery = BuilderUtil.buildCypher("ORG", identifiers, matchAll);
//        Map<String, Object> params = new HashMap<>();
//        List<String> whereClauses = new ArrayList<>();
//        int idx = 0;
//        for (var entry : identifiers.entrySet()) {
//            String relType = entry.getKey();
//            String paramName = "val" + idx;
//            String alias = "id" + idx;
//
//            params.put(paramName, entry.getValue());
//
//            whereClauses.add(String.format(
//                    "EXISTS { MATCH (org) -[:HAS_%s]->(%s:Identifier) WHERE %s.value = $%s }",
//                    relType, alias, alias, paramName));
//            idx++;
//        }
//
//        String operator = matchAll ? " AND " : " OR ";
//        String cypher = """
//                MATCH (org:Organization)
//                WHERE %s
//                OPTIONAL MATCH (org)-[r]->(id:Identifier)
//                RETURN DISTINCT org, collect(DISTINCT {relType: type(r), node: id}) AS identifiers
//                """.formatted(String.join(operator, whereClauses));
//        log.info("Cypher query to match orgs by identifiers: {}", cypher);
//        log.info("Parameters for the cypher query: {}", params);
        return mapResults(cypherQuery.getCypher(), cypherQuery.getParams());
    }

    public Organization findAllOrganizationNetworks(String elementId){
        return networkRepository.findNetworksByOrganization(elementId);
    }

    @Override
    public Organization findLocationsByOrgAndNet(String orgId, String netId) {
        return locationRepository.findLocationsByOrgAndNet(orgId, netId);
    }

    /**
     * Retrieves an organization along with all its associated locations based on the provided element ID.
     *
     * @param elementId the unique identifier of the organization element to find associated locations
     * @return an Organization object containing the organization details and its associated locations
     */
    @Override
    public Organization findAllOrganizationLocations(String elementId) {
        return locationRepository.findLocationsByOrganization(elementId);
    }

    /**
     * Retrieves the details of networks associated with a specific organization and location.
     *
     * @param orgId the unique identifier of the organization
     * @param locId the unique identifier of the location
     * @return an Organization object containing the organization details and associated networks for the specified location
     */
    @Override
    public Organization findNetworksByOrgAndLoc(String orgId, String locId) {

        return networkRepository.findNetworksByOrgAndLoc(orgId, locId);
    }

    /**
     * Creates a new organization using the details provided in the OrganizationDto object.
     *
     * @param organizationDto the data transfer object containing the details of the organization to be created
     * @return the newly created Organization object
     */
    @Override
    public Organization createOrganization(OrganizationDto organizationDto) {
        String cypher = cypherLoader.load("create_organization.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("name", organizationDto.getName());

        Map<String, String> tin = new HashMap<>();
        tin.put("type", "TIN");
        tin.put("value", "TestTIN");
        tin.put("legalName", "TestLegalName");

        Map<String, String> npi = new HashMap<>();
        tin.put("type", "NPI");
        tin.put("value", "TestNPI");

        Map<String, String> medicaidId = new HashMap<>();
        tin.put("type", "MEDICAID_ID");
        tin.put("value", "TestMedicaidId");
        tin.put("state", "FL");

        List<Map<String, String>> identifiers = Arrays.asList(tin, npi, medicaidId);
        params.put("identifiers", identifiers);

        queryExecutor.executeReadQuery(cypher, params,
                record -> {
                    Node node = record.get("o").asNode();
                    log.info("Organization name: {}", node.get("name").asString());
                    log.info("Element id of the Org:{}", node.elementId());
//                    Organization org = BuilderUtil.buildOrganization(node);
                    return node;
                });

        return null;
    }


    /**
     * Maps the results of a Neo4j Cypher query to a list of Organization objects.
     *
     * @param cypher the Cypher query string to be executed in the Neo4j database
     * @param params a map containing the parameters to be used in the Cypher query
     * @return a list of Organization objects created from the query results
     */
    private List<Organization> mapResults(String cypher, Map<String, Object> params) {
        return queryExecutor.executeReadQuery(cypher, params, record -> {
            Node node = record.get("org").asNode();
            Organization org = BuilderUtil.buildOrganization(node);
            List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
            List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
            org.setIdentifiers(identifiers);
            return org;
        });
    }




}
