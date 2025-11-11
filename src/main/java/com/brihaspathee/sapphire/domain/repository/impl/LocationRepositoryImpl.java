package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.Location;
import com.brihaspathee.sapphire.domain.entity.LocationNetworkServiceInfo;
import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.LocationRepository;
import com.brihaspathee.sapphire.domain.repository.util.BuilderUtil;
import com.brihaspathee.sapphire.util.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, November 2025
 * Time: 07:09
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    /**
     * An instance of Neo4jQueryExecutor responsible for executing Cypher queries
     * against a Neo4j database. It provides methods for executing both read and write
     * operations on the database. This component serves as the primary interface
     * for interacting with the Neo4j database layer, enabling efficient query execution
     * through reusable and parameterized Cypher queries.
     */
    private final Neo4jQueryExecutor queryExecutor;

    /**
     * A utility object used for managing and loading Cypher query files.
     * It facilitates caching and efficient retrieval of Cypher queries from resources,
     * reducing redundant file reading operations. This object is primarily used
     * for fetching pre-configured Cypher queries needed for executing database operations.
     */
    private final CypherLoader cypherLoader;

    /**
     * Retrieves an organization and its associated locations based on the provided organization ID.
     *
     * This method executes a Cypher query to fetch the organization details and its
     * linked locations from the database using the given organization ID. The resulting
     * organization object includes information such as the organization's name, type,
     * and its locations if available.
     *
     * @param organizationId the unique identifier of the organization whose locations are to be retrieved
     * @return an {@link Organization} object containing the organization's details and associated locations,
     *         or {@code null} if no organization is found for the given ID
     */
    @Override
    public Organization findLocationsByOrganization(String organizationId) {
        log.info("Fetching locations for organization:");
        String cypher = cypherLoader.load("get_locs_by_org.cypher");
        log.info("Cypher query: {}", cypher);
        log.info("Parameter elementId: {}", organizationId);
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, Map.of("orgId", organizationId),
                LocationRepositoryImpl::getOrganizationWithLocations);
        return organizations.isEmpty() ? null : organizations.getFirst();
    }

    /**
     * Retrieves the organization and its associated locations based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @return an Organization object containing the organization details and its associated locations
     */
    @Override
    public Organization findLocationsByOrgAndNet(String orgId, String netId) {
        log.debug("Fetching locations for organization and network:");
        String cypher = cypherLoader.load("get_locs_by_net_org.cypher");
        log.debug("Cypher query: {}", cypher);
        log.debug("Org elementId: {}", orgId);
        log.debug("Net elementId: {}", netId);
        Map<String, Object> params = Map.of("orgId", orgId, "netId", netId);
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, params,
                LocationRepositoryImpl::getOrganizationWithLocAndNet);
        return organizations.isEmpty() ? null : organizations.getFirst();
    }

    /**
     * Constructs and retrieves an Organization object from the provided record.
     * The method extracts data from the record, processes it to build
     * the Organization, and its associated networks, identifiers,
     * and locations.
     *
     * @param record the Neo4j record containing the data to construct the Organization.
     *               The record includes nodes and properties representing the organization,
     *               its identifiers, associated networks, and locations.
     * @return an Organization object constructed using the data extracted from the record.
     */
    private static Organization getOrganizationWithLocAndNet(org.neo4j.driver.Record record) {
        Organization organization = getOrganization(record);
        Node networkNode = record.get("net").asNode();
        Network network = BuilderUtil.buildNetwork(networkNode);
        List<Location> locations = new ArrayList<>();
        List<Map<String, Object>> locationNetworkInfoList = record.get("locations").asList(Value::asMap);
        for (Map<String, Object> locNetInfo : locationNetworkInfoList) {
            log.debug("LocationNetworkInfo: {}", locNetInfo.get("location"));
            Node locationNode = (Node) (locNetInfo).get("location");
            Location location = BuilderUtil.buildLocation(locationNode);
            log.debug("Role Location Data: {}", locNetInfo.get("roleLocationData"));
            List<Map<String, Object>> rlDataList = (List<Map<String, Object>>) locNetInfo.get("roleLocationData");
            for (Map<String, Object> rlData : rlDataList) {
                LocationNetworkServiceInfo lnsi = LocationNetworkServiceInfo.builder().build();
                log.debug("rlData: {}", rlData);
                Relationship hasPanelRel = rlData.get("hasPanelRel") instanceof Relationship r ? r : null;
                log.debug("Has Panel Relationship Object: {}", hasPanelRel);
                Relationship isPCPRel = rlData.get("isPcpRel") instanceof Relationship r ? r : null;
                log.debug("Is PCP Relationship Object: {}", isPCPRel);
                Object obj = rlData.get("servesRels");
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
                location.setNetworkServiceInfo(lnsi);
                locations.add(location);
            }


        }
//        log.debug("Locations: {}", locations);
        network.setLocations(locations);
        organization.setNetworks(List.of(network));
        return organization;
    }

    /**
     * Constructs and retrieves an {@link Organization} object along with its associated locations
     * from the provided Neo4j record. This method processes the record to extract data from
     * nodes and their properties to build the organization and its locations.
     *
     * @param record the Neo4j record containing data about the organization and its associated locations.
     *               The record includes nodes and properties representing the organization details
     *               and its locations.
     * @return an {@link Organization} object containing the organization's details, including
     *         its identifiers and associated locations.
     */
    private static Organization getOrganizationWithLocations(org.neo4j.driver.Record record){
        Organization organization = getOrganization(record);
        List<Location> locations = new ArrayList<>();
        List<Value> locationList = record.get("locations").asList(v->v);
        for (Value location: locationList){
            Node locNode = location.asNode();
            Location loc = BuilderUtil.buildLocation(locNode);
            locations.add(loc);
        }
        organization.setLocations(locations);
        return organization;
    }

    /**
     * Constructs and retrieves an {@link Organization} object from the provided Neo4j {@link Record}.
     * This method processes the record to extract data related to the organization
     * and its associated identifiers. It builds and returns an organization object using the extracted data.
     *
     * @param record the Neo4j record containing data about the organization. The record includes
     *               nodes and properties representing the organization details and its identifiers.
     * @return an {@link Organization} object containing the organization's details, including its
     *         name, element ID, and associated identifiers.
     */
    private static Organization getOrganization(org.neo4j.driver.Record record){
        Node node = record.get("org").asNode();
        log.debug("Organization name: {}", node.get("name").asString());
        log.debug("Element id of the Org:{}", node.elementId());
        Organization.OrganizationBuilder orgBuilder = BuilderUtil.buildOrganization(node);
        List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
        return orgBuilder.identifiers(BuilderUtil.buildIdentifiers(idList)).build();

    }
}
