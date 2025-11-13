package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, November 2025
 * Time: 06:54
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class BuilderUtil {

    /**
     * Constructs and returns an {@link Organization.OrganizationBuilder} instance populated with
     * data extracted from the provided Neo4j {@link Node}.
     *
     * @param orgNode the Neo4j {@link Node} containing organization data
     * @return an {@link Organization.OrganizationBuilder} initialized with the extracted data
     */
    public static Organization.OrganizationBuilder buildOrganization(Node orgNode) {
        if (orgNode == null) {
            return null;
        }
        return Organization.builder()
                .elementId(orgNode.elementId())
                .name(orgNode.get("name").asString())
                .aliasName(orgNode.get("alias").asString())
                .type(orgNode.get("type").asString())
                .atypical(orgNode.get("atypical").asBoolean())
                .capitated(orgNode.get("capitated").asBoolean())
                .pcpPractitionerRequired(orgNode.get("pcpPractitionerRequired").asBoolean());
    }

    /**
     * Builds and returns a {@link Network} object populated with data extracted from the provided Neo4j {@link Node}.
     * The method validates the input and constructs a Network instance using the builder pattern.
     * It also sets additional properties if the corresponding keys exist within the {@link Node}.
     *
     * @param networkNode the Neo4j {@link Node} containing the network data.
     *                    Must not be null for a valid {@link Network} instance to be created.
     * @return a {@link Network} object constructed from the given {@link Node} data, or null if the input {@link Node} is null.
     */
    public static Network buildNetwork(Node networkNode) {
        if (networkNode == null) {
            return null;
        }
        Network network = Network.builder()
                .elementId(networkNode.elementId())
                .code(networkNode.get("code").asString())
                .name(networkNode.get("name").asString())
                .build();
        log.debug("Network is HNET Network: {}", networkNode.get("isHNETNetwork"));
        log.debug("Network is Vendor Network: {}", networkNode.get("isVendorNetwork"));
        if (networkNode.containsKey("isHNETNetwork")) {
            network.setIsHNETNetwork(networkNode.get("isHNETNetwork").asBoolean());
        }
        if (networkNode.containsKey("isVendorNetwork")) {
            network.setIsVendorNetwork(networkNode.get("isVendorNetwork").asBoolean());
        }
        return network;
    }

    /**
     * Constructs and returns a {@link Product} instance populated with data extracted from the provided Neo4j {@link Node}.
     * This method uses the builder pattern to create a Product object and sets key properties based on the Node's content.
     *
     * @param productNode the Neo4j {@link Node} containing product data. If null, the method returns null.
     *                    It is expected that the Node provides values for keys such as "elementId" and "name".
     * @return a {@link Product} object constructed from the given {@link Node}'s data, or null if the input {@link Node} is null.
     */
    public static Product buildProduct(Node productNode){
        if (productNode == null) {
            return null;
        }
        Product product = Product.builder()
                .elementId(productNode.elementId())
                .name(productNode.get("name").asString())
                .build();
        return product;
    }

    /**
     * Constructs and returns a {@link Location} object populated with data extracted from the provided Neo4j {@link Node}.
     * This method builds a Location instance using the builder pattern and sets additional properties
     * if the corresponding keys exist within the {@link Node}.
     *
     * @param locationNode the Neo4j {@link Node} containing location data. If null, the method returns null.
     *                     The expected keys in the {@link Node} include "elementId", "name",
     *                     "streetAddress", "secondaryAddress", "city", "state", "zipCode", "county", and "countyFIPS".
     * @return a {@link Location} object constructed from the given {@link Node}'s data,
     *         or null if the input {@link Node} is null.
     */
    public static Location buildLocation(Node locationNode) {
        if (locationNode == null) {
            return null;
        }
        Location location = Location.builder()
                .elementId(locationNode.elementId())
                .name(locationNode.get("name").asString())
                .streetAddress(locationNode.get("streetAddress").asString())
                .build();
        if (locationNode.containsKey("secondaryAddress")) {
            location.setSecondaryAddress(locationNode.get("secondaryAddress").asString());
        }
        if (locationNode.containsKey("city")) {
            location.setCity(locationNode.get("city").asString());
        }
        if (locationNode.containsKey("state")) {
            location.setState(locationNode.get("state").asString());
        }
        if (locationNode.containsKey("zipCode")) {
            location.setZipCode(locationNode.get("zipCode").asString());
        }
        if (locationNode.containsKey("county")){
            location.setCounty(locationNode.get("county").asString());
        }
        if (locationNode.containsKey("countyFIPS")){
            location.setCountyFIPS(locationNode.get("countyFIPS").asString());
        }
        return location;
    }

    /**
     * Builds a list of identifiers based on the provided data.
     *
     * @param idList a list of maps, where each map represents an identifier's properties such as
     *               relationship type and associated node details
     * @return a list of {@link Identifier} objects constructed from the input data or null if the input list is null or empty
     */
    public static List<Identifier> buildIdentifiers(List<Map<String, Object>> idList) {
        if (idList == null || idList.isEmpty()) {
            return null;
        }
        log.debug("Building identifiers: {}", idList);
        List<Identifier> identifiers = new ArrayList<>();
        for (Map<String, Object> idMap : idList) {
            String relType = (String) idMap.get("relType");
            Node idNode = (Node) idMap.get("node");
            log.debug("relType: {}", relType);
            log.debug("idNode: {}", idNode);
            Identifier identifier = switch (relType) {
                case "HAS_NPI" -> new NPI();
                case "HAS_TIN" -> {
                    TIN tin = new TIN();
                    if (idNode.containsKey("legalName")){
                        tin.setLegalName(idNode.get("legalName").asString());
                    }
                    yield tin;
                }
                case "HAS_MEDICARE_ID" -> new MedicareID();
                case "HAS_MEDICAID_ID" -> {
                    MedicaidID medicaidId = new MedicaidID();
                    if (idNode.containsKey("state")){
                        medicaidId.setState(idNode.get("state").asString());
                    }
                    yield medicaidId;
                }
                default -> null;
            };
            if (identifier != null){
                identifier.setElementId(idNode.elementId());
                identifier.setValue(idNode.get("value").asString());
                if (idNode.containsKey("startDate")){
                    log.debug("Start date: {}", idNode.get("startDate"));
                    identifier.setStartDate(idNode.get("startDate").asLocalDate());
                }
                if (idNode.containsKey("endDate")){
                    identifier.setEndDate(idNode.get("endDate").asLocalDate());
                }
                identifiers.add(identifier);
            }
        }
        return identifiers;
    }

    /**
     * Constructs and returns a {@link HasPanel} object populated with data extracted from the provided {@link Relationship}.
     * The method maps various attributes from the Relationship object to corresponding fields in the HasPanel object.
     *
     * @param hasPanelRel the {@link Relationship} containing the data for constructing the {@link HasPanel} object.
     *                    This relationship may include attributes such as "status", "gender_limitation",
     *                    "age_limitation", "highest_age_years", "lowest_age_years", "highest_age_months",
     *                    and "lowest_age_months".
     * @return a {@link HasPanel} object created using the data extracted from the provided {@link Relationship}.
     */
    public static HasPanel buildHasPanelRel(Relationship hasPanelRel) {
        return HasPanel.builder()
                .status(hasPanelRel.get("status") !=null ? hasPanelRel.get("status").asString() : null)
                .genderLimitation(hasPanelRel.containsKey("gender_limitation")  ? hasPanelRel.get("gender_limitation").asString(): null)
                .ageLimitation(hasPanelRel.containsKey("age_limitation")  ? hasPanelRel.get("age_limitation").asString() : null)
                .highestAgeYears(hasPanelRel.containsKey("highest_age_years") ? hasPanelRel.get("highest_age_years").asInt() : null)
                .lowestAgeYears(hasPanelRel.containsKey("lowest_age_years")  ? hasPanelRel.get("lowest_age_years").asInt() : null)
                .highestAgeMonths(hasPanelRel.containsKey("highest_age_months")  ? hasPanelRel.get("highest_age_months").asInt() : null)
                .lowestAgeMonths(hasPanelRel.containsKey("lowest_age_months")  ? hasPanelRel.get("lowest_age_months").asInt() : null)
                .build();
    }

    public static List<RoleLocationServes> buildRoleLocationServesRels(Object rlsRels){
        if (rlsRels == null) {
            return null;
        }
        List<Relationship> servesRels = null;
        if (rlsRels instanceof List<?> list) {
            boolean allRelationships = list.stream().allMatch(Relationship.class::isInstance);
            if (allRelationships) {
                servesRels = list.stream()
                        .map(Relationship.class::cast)
                        .toList();
            }
        }
        List<RoleLocationServes> roleLocationServesList = null;
        if (servesRels!=null && !servesRels.isEmpty()){
            roleLocationServesList = new ArrayList<>();
            for (Relationship rel : servesRels) {
                log.debug("Rel: {}", rel);
                log.debug("Rel Type: {}", rel.type());
                log.debug("Rel Start Node: {}", rel.startNodeElementId());
                log.debug("Rel End Node: {}", rel.endNodeElementId());
                log.debug("Rel Properties: {}", rel.asMap());
                Map<String, Object> relProps = rel.asMap();
                RoleLocationServes roleLocationServes = RoleLocationServes.builder()
                        .startDate(relProps.get("startDate") !=null ? (LocalDate) relProps.get("startDate") : null)
                        .endDate(relProps.get("endDate") !=null ? (LocalDate) relProps.get("endDate") : null)
                        .termReason(relProps.get("termReason") !=null ? (String) relProps.get("termReason") : null)
                        .build();
                roleLocationServesList.add(roleLocationServes);
            }
        }
        return roleLocationServesList;
    }
}
