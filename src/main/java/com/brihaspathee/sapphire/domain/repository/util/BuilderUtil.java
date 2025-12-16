package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import com.brihaspathee.sapphire.model.web.IdentifierInfo;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

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
     * Builds and returns a list of {@link Qualification} objects from the provided list of Neo4j {@link Node} instances.
     * Each {@link Node} in the input list is expected to contain data required to construct a {@link Qualification}.
     *
     * @param qualNodes a list of Neo4j {@link Node} instances, where each {@link Node} represents qualification data.
     *                  If null or empty, an empty list is returned.
     * @return a list of {@link Qualification} objects constructed from the provided {@link Node} instances.
     *         Returns an empty list if the input {@link Node} list is null or empty.
     */
    public static List<Qualification> buildQualifications(List<Node> qualNodes){
        if (qualNodes == null || qualNodes.isEmpty()) {
            return null;
        }
        log.debug("Building Qualifications: {}", qualNodes);
        List<Qualification> qualifications = new ArrayList<>();
        for (Node qualNode : qualNodes) {
            Qualification qual = Qualification.builder()
                    .elementId(qualNode.elementId())
                    .type(qualNode.get("type").asString())
                    .issuer(qualNode.get("issuer").asString(null))
                    .state(qualNode.get("state").asString(null))
                    .value(qualNode.get("value").asString(null))
                    .specialtyTaxonomy(qualNode.get("specialtyTaxonomy").asString(null))
                    .startDate(qualNode.get("startDate").asLocalDate(null))
                    .endDate(qualNode.get("endDate").asLocalDate(null))
                    .build();
            qualifications.add(qual);
        }
        return qualifications;
    }

}
