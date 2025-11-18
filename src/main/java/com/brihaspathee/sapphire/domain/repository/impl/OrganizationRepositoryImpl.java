package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.LocationRepository;
import com.brihaspathee.sapphire.domain.repository.interfaces.NetworkRepository;
import com.brihaspathee.sapphire.domain.repository.interfaces.OrganizationRepository;
import com.brihaspathee.sapphire.domain.repository.util.BuilderUtil;
import com.brihaspathee.sapphire.util.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Neo4jQueryExecutor queryExecutor;

    private final CypherLoader cypherLoader;

    private final NetworkRepository networkRepository;

    private final LocationRepository locationRepository;

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
        Map<String, Object> params = new HashMap<>();
        List<String> whereClauses = new ArrayList<>();
        int idx = 0;
        for (var entry : identifiers.entrySet()) {
            String relType = entry.getKey();
            String paramName = "val" + idx;
            String alias = "id" + idx;

            params.put(paramName, entry.getValue());

            whereClauses.add(String.format(
                    "EXISTS { MATCH (org) -[:HAS_%s]->(%s:Identifier) WHERE %s.value = $%s }",
                    relType, alias, alias, paramName));
            idx++;
        }

        String operator = matchAll ? " AND " : " OR ";
        String cypher = """
                MATCH (org:Organization)
                WHERE %s
                OPTIONAL MATCH (org)-[r]->(id:Identifier)
                RETURN DISTINCT org, collect(DISTINCT {relType: type(r), node: id}) AS identifiers
                """.formatted(String.join(operator, whereClauses));
        log.info("Cypher query to match orgs by identifiers: {}", cypher);
        log.info("Parameters for the cypher query: {}", params);
        return mapResults(cypher, params);
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
