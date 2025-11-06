package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.NetworkRepository;
import com.brihaspathee.sapphire.domain.repository.util.BuilderUtil;
import com.brihaspathee.sapphire.util.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, November 2025
 * Time: 06:43
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NetworkRepositoryImpl implements NetworkRepository {

    /**
     * An instance of Neo4jQueryExecutor responsible for executing Cypher queries
     * against a Neo4j database. It provides methods to perform both read and write
     * operations using parameterized Cypher queries, ensuring efficient interaction
     * with the database.
     */
    private final Neo4jQueryExecutor queryExecutor;

    /**
     * A utility object used for loading and caching Cypher query files.
     * It helps in managing and retrieving the contents of Cypher files efficiently
     * and is utilized within the repository implementation for executing Cypher-based operations.
     * This object provides the necessary abstraction to interact with Cypher file resources.
     */
    private final CypherLoader cypherLoader;

    /**
     * Retrieves a list of all network entities.
     *
     * @return a list of Network objects representing all networks in the repository
     */
    @Override
    public List<Network> findAll() {
        return List.of();
    }


    /**
     * Retrieves a list of organizations associated with the specified element ID.
     * This method executes a Cypher query to fetch organizations from the database,
     * processes the results, and returns a list of populated {@link Organization} objects.
     *
     * @param elementId the unique identifier of the element whose associated organizations are to be fetched
     * @return a list of {@link Organization} objects representing the organizations associated with the given element ID
     */
    @Override
    public List<Organization> findNetworksByOrganizations(String elementId) {
        log.info("Fetching networks for organization:");
        String cypher = cypherLoader.load("get_all_organizations.cypher");
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, Map.of("orgId", elementId),
                NetworkRepositoryImpl::getOrganization);
        return organizations;
    }

    /**
     * Extracts organization details from a Neo4j database record and constructs an
     * {@link Organization} object using the provided data.
     *
     * @param record the Neo4j {@link org.neo4j.driver.Record} object containing the organization
     *               data, identifiers, and associated networks
     * @return an {@link Organization} object populated with the data from the specified record
     */
    private static Organization getOrganization(org.neo4j.driver.Record record) {
        Node node = record.get("org").asNode();
        log.info("Organization name: {}", node.get("name").asString());
        log.info("Element id of the Org:{}", node.elementId());
        Organization.OrganizationBuilder builder = BuilderUtil.buildOrganization(node);
        List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
        List<Value> networkList = record.get("networks").asList(v -> v);
        return builder
                .identifiers(BuilderUtil.buildIdentifiers(idList))
                .networks(null)
                .build();
    }
}
