package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.Location;
import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 16, December 2025
 * Time: 04:58
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class BuildLocationEntity {

    /**
     * Constructs and returns a {@link Location} object populated with data extracted from the provided {@link Value}.
     * The method processes the given {@link Value}, retrieves nested data, and builds a {@link Location} object
     * by mapping its properties, including identifiers, which are constructed from a list of maps.
     *
     * @param locationInfo the {@link Value} containing the location data and its details. It is expected to include:
     *                     - "loc": a {@link Node} representing the location details.
     *                     - "locDetails": additional details such as "identifiers", which is a list of maps.
     * @return a {@link Location} object constructed using the provided data, including its identifiers.
     */
    public static Location buildLocation(Value locationInfo) {
        Node locNode = locationInfo.get("loc").asNode();
        Location loc = buildLocation(locNode);
        Value locDetails = locationInfo.get("locDetails");
        List<Map<String, Object>> idList = locDetails.get("identifiers").asList(Value::asMap);
        List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
        loc.setIdentifiers(identifiers);
        return loc;
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
                .genderLimitation(hasPanelRel.containsKey("genderLimitation")  ? hasPanelRel.get("genderLimitation").asString(): null)
                .ageLimitation(hasPanelRel.containsKey("ageLimitation")  ? hasPanelRel.get("ageLimitation").asString() : null)
                .highestAgeYears(hasPanelRel.containsKey("highestAgeYears") ? hasPanelRel.get("highestAgeYears").asInt() : null)
                .lowestAgeYears(hasPanelRel.containsKey("lowestAgeYears")  ? hasPanelRel.get("lowestAgeYears").asInt() : null)
                .highestAgeMonths(hasPanelRel.containsKey("highestAgeMonths")  ? hasPanelRel.get("highestAgeMonths").asInt() : null)
                .lowestAgeMonths(hasPanelRel.containsKey("lowestAgeMonths")  ? hasPanelRel.get("lowestAgeMonths").asInt() : null)
                .build();
    }

    /**
     * Constructs and returns a list of {@link RoleLocationServes} objects using data extracted from the provided input.
     * The method processes the input, which is expected to be a list of {@link Relationship} objects, and maps each
     * relationship's properties to create corresponding {@link RoleLocationServes} objects.
     *
     * @param rlsRels the input object, typically a list of {@link Relationship} instances, from which the
     *                {@link RoleLocationServes} objects are constructed. If the input is null or not a valid list of
     *                {@link Relationship} objects, the method returns null or an empty list.
     * @return a list of {@link RoleLocationServes} objects populated with data from the provided input, or null
     *         if the input is invalid.
     */
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
