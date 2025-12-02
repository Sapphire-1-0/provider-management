package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.NetworkRepository;
import com.brihaspathee.sapphire.domain.repository.util.BuilderUtil;
import com.brihaspathee.sapphire.model.web.NetworkSearchRequest;
import com.brihaspathee.sapphire.utils.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
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
     * Retrieves a list of networks based on the specified search request.
     * This method executes a Cypher query to fetch all networks from the database
     * and returns a list of {@link Network} objects that match the criteria defined
     * in the {@link NetworkSearchRequest}.
     *
     * @param networkSearchRequest the search request containing criteria
     *                             such as product code, network code, and network name
     *                             for filtering the networks
     * @return a list of {@link Network} objects matching the search criteria
     */
    @Override
    public List<Network> findNetworks(NetworkSearchRequest networkSearchRequest) {
        log.info("Fetching all networks in ATON:");
        Map<String, Object> params = getCypherParams(networkSearchRequest);
        String cypher = cypherLoader.load("get_all_networks.cypher");
        log.info("Cypher query: {}", cypher);
        List<Network> networks = queryExecutor.executeReadQuery(cypher, params,
                NetworkRepositoryImpl::getNetworks);
        return networks;
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
    public Organization findNetworksByOrganization(String elementId) {
        log.info("Fetching networks for organization:");
        String cypher = cypherLoader.load("get_networks_by_org.cypher");
        log.info("Cypher query: {}", cypher);
        log.info("Parameter elementId: {}", elementId);
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, Map.of("orgId", elementId),
                NetworkRepositoryImpl::getOrganizationWithNetworks);
        return organizations.isEmpty() ? null : organizations.getFirst();
    }

    /**
     * Retrieves an {@link Organization} entity based on the provided organization ID and location ID.
     * This method fetches data related to an organization and its associated networks, filtered
     * by the specific organization and location identifiers.
     *
     * @param orgId the unique identifier of the organization to retrieve
     * @param locId the unique identifier of the location associated with the organization
     * @return an {@link Organization} object containing details of the organization and its associated
     *         networks based on the specified organization and location IDs
     */
    @Override
    public Organization findNetworksByOrgAndLoc(String orgId, String locId) {
        log.debug("Fetching locations for organization and network:");
        String cypher = cypherLoader.load("get_networks_by_loc_org.cypher");
        log.debug("Cypher query: {}", cypher);
        log.debug("Org elementId: {}", orgId);
        log.debug("Loc elementId: {}", locId);
        Map<String, Object> params = Map.of("orgId", orgId, "locId", locId);
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, params,
                NetworkRepositoryImpl::getOrganizationWithLocAndNet);
        return organizations.isEmpty() ? null : organizations.getFirst();
    }

    /**
     * Extracts organization details from a Neo4j database record and constructs an
     * {@link Organization} object using the provided data.
     *
     * @param record the Neo4j {@link org.neo4j.driver.Record} object containing the organization
     *               data, identifiers, and associated networks
     * @return an {@link Organization} object populated with the data from the specified record
     */
    private static Organization getOrganizationWithNetworks(org.neo4j.driver.Record record) {
        Organization organization = getOrganization(record);
        List<Node> networkList = record.get("networks").asList(Value::asNode);
        List<Network> networks = new ArrayList<>();
        for (Node networkNode: networkList){
            networks.add(BuilderUtil.buildNetwork(networkNode));
        }
        log.info("Networks: {}", networks);
        organization.setNetworks(networks);
        return organization;
    }

    private static Organization getOrganizationWithLocAndNet(org.neo4j.driver.Record record) {
        Organization organization = getOrganization(record);
        Node locationNode = record.get("loc").asNode();
        Location location = BuilderUtil.buildLocation(locationNode);
        List<Network> networks = new ArrayList<>();
        List<Map<String, Object>> locationNetworkInfoList = record.get("networks").asList(Value::asMap);
        for (Map<String, Object> locNetInfo : locationNetworkInfoList) {
            log.debug("LocationNetworkInfo: {}", locNetInfo.get("network"));
            Node networkNode = (Node) (locNetInfo).get("network");
            Network network = BuilderUtil.buildNetwork(networkNode);
            log.debug("Role Network Data: {}", locNetInfo.get("roleNetworkData"));
            List<Map<String, Object>> rnDataList = (List<Map<String, Object>>) locNetInfo.get("roleNetworkData");
            for (Map<String, Object> rnData : rnDataList) {
                LocationNetworkServiceInfo lnsi = LocationNetworkServiceInfo.builder().build();
                log.debug("rlData: {}", rnData);
                Relationship hasPanelRel = rnData.get("hasPanelRel") instanceof Relationship r ? r : null;
                log.debug("Has Panel Relationship Object: {}", hasPanelRel);
                Relationship isPCPRel = rnData.get("isPcpRel") instanceof Relationship r ? r : null;
                log.debug("Is PCP Relationship Object: {}", isPCPRel);
                Object obj = rnData.get("servesRels");
                List<RoleLocationServes> roleLocationServesList = BuilderUtil.buildRoleLocationServesRels(obj);
                if (roleLocationServesList != null) {
                    lnsi.setRoleLocationServes(roleLocationServesList);
                }
                if (hasPanelRel != null) {
                    HasPanel hasPanel = BuilderUtil.buildHasPanelRel(hasPanelRel);
                    lnsi.setHasPanel(hasPanel);
                }
                if (isPCPRel != null) {
                    log.debug("IsPCP Panel Rel: {}", isPCPRel);
                    log.debug("IsPCP Panel  Type: {}", isPCPRel.type());
                    log.debug("IsPCP Panel  Start Node: {}", isPCPRel.startNodeElementId());
                    log.debug("IsPCP Panel  End Node: {}", isPCPRel.endNodeElementId());
                    log.debug("IsPCP Panel  Properties: {}", isPCPRel.asMap());
                    lnsi.setIsPCP(true);
                }
                network.setNetworkServiceInfo(lnsi);
                networks.add(network);
            }


        }
//        log.debug("Locations: {}", locations);
        location.setNetworks(networks);
        organization.setLocations(List.of(location));
        return organization;
    }

    /**
     * Extracts organization details from a Neo4j database record and constructs an
     * {@link Organization} object using the provided data. The method retrieves
     * the organization node, processes its attributes, builds a list of identifiers,
     * and returns a complete {@link Organization} object.
     *
     * @param record the Neo4j {@link org.neo4j.driver.Record} containing the organization node,
     *               its attributes, and associated identifiers
     * @return an {@link Organization} object populated with the data from the specified record
     */
    private static Organization getOrganization(org.neo4j.driver.Record record){
        Node node = record.get("org").asNode();
        log.debug("Organization name: {}", node.get("name").asString());
        log.debug("Element id of the Org:{}", node.elementId());
        Organization org = BuilderUtil.buildOrganization(node);
        List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
        org.setIdentifiers(BuilderUtil.buildIdentifiers(idList));
        return org;

    }

    /**
     * Extracts network details from a Neo4j database record and constructs a {@link Network} object.
     * This method retrieves the network node and its associated product nodes, processes the data,
     * and returns a populated {@link Network} object with its relationships to products.
     *
     * @param record the Neo4j {@link org.neo4j.driver.Record} containing the network node and its associated product nodes
     * @return a {@link Network} object populated with the data from the given record, including associated products
     */
    private static Network getNetworks(org.neo4j.driver.Record record){
        Node networkNode = record.get("net").asNode();
        List<Node> productNodes = record.get("products").asList(Value::asNode);
        Network network = BuilderUtil.buildNetwork(networkNode);
        List<Product> products = new ArrayList<>();
        for (Node productNode : productNodes) {
            Product product = BuilderUtil.buildProduct(productNode);
            products.add(product);
        }
        network.setPartOfProducts(products);
        return network  ;
    }

    /**
     * Generates a Cypher query based on the given {@link NetworkSearchRequest}.
     * If the network search request is null, a query to retrieve all networks is returned.
     * If the product code within the network search request is not null, a query to retrieve
     * networks by product is returned.
     *
     * @param networkSearchRequest the search request containing criteria such as
     *                             product code for filtering the networks; can be null
     * @return the Cypher query as a {@link String} to retrieve networks based on the given criteria,
     *         or null if no criteria is provided
     */
    private Map<String,Object> getCypherParams(NetworkSearchRequest networkSearchRequest) {
        Map<String, Object> params = new HashMap<>();
        if (networkSearchRequest == null) {
            params.put("productCode", null);
            params.put("networkCode", null);
            params.put("networkName", null);
        }else{
            params.put("productCode", networkSearchRequest.getProductCode());
            params.put("networkCode", networkSearchRequest.getNetworkCode());
            params.put("networkName", networkSearchRequest.getNetworkName());
        }
        return params;
    }
}
