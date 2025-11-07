package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.types.Node;

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


    public static Network buildNetwork(Node networkNode) {
        if (networkNode == null) {
            return null;
        }
        return Network.builder()
                .code(networkNode.get("code").asString())
                .name(networkNode.get("name").asString())
//                .isHNETNetwork(networkNode.get("isHNETNetwork").asBoolean())
//                .isVendorNetwork(networkNode.get("isVendorNetwork").asBoolean())
                .build();
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
        log.info("Building identifiers: {}", idList);
        List<Identifier> identifiers = new ArrayList<>();
        for (Map<String, Object> idMap : idList) {
            String relType = (String) idMap.get("relType");
            Node idNode = (Node) idMap.get("node");
            log.info("relType: {}", relType);
            log.info("idNode: {}", idNode);
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
                    log.info("Start date: {}", idNode.get("startDate"));
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
}
