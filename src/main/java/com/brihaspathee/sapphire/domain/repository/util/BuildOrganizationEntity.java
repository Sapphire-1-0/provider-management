package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 16, December 2025
 * Time: 04:40
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class BuildOrganizationEntity {

    /**
     * Builds and returns an instance of the Organization class based on the data
     * provided in the given Value object. The method leverages various builder
     * utilities to process and set identifiers and qualifications for the
     * Organization instance.
     *
     * @param orgInfo the Value object containing data required for building the
     *                Organization. The object is expected to have an "org" field
     *                for the core organization data and an "orgDetails" field for
     *                details like identifiers and qualifications.
     * @return an Organization object constructed from the data in the Value object.
     */
    public static Organization buildOrganization(Value orgInfo){
        Node orgNode = orgInfo.get("org").asNode();
        Organization organization = buildOrganization(orgNode);
        Value orgDetails = orgInfo.get("orgDetails");
        List<Map<String, Object>> idList = orgDetails.get("identifiers").asList(Value::asMap);
        List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
        organization.setIdentifiers(identifiers);
        List<Node> qualNodes = orgDetails.get("qualifications").asList(Value::asNode);
        List<Qualification> qualifications = BuilderUtil.buildQualifications(qualNodes);
        organization.setQualifications(qualifications);
        List<Value> networks = orgDetails.get("networks").asList(v->v);
        if(networks!=null && !networks.isEmpty()){
            List<Network> networkList = new ArrayList<>();
            for (Value network : networks) {
                Network net = BuildNetworkEntity.buildNetwork(network);
                List<Value> locations = network.get("locations").asList(v->v);
                if (locations != null && !locations.isEmpty()) {
                    List<Location> locList = new ArrayList<>();
                    for (Value location : locations) {
                        Location loc = BuildLocationEntity.buildLocation(location);
                        locList.add(loc);
                    }
                    net.setLocations(locList);
                }
                networkList.add(net);
            }
            organization.setNetworks(networkList);
        }
        return organization;
    }

    /**
     * Builds and returns an instance of the Organization class based on the data
     * provided in the given Node object. The method extracts various attributes
     * from the Node and uses the Organization builder to construct the object.
     *
     * @param orgNode the node containing data for building the Organization.
     *                It should include attributes such as elementId, name, aliasName,
     *                type, atypical, capitated, and pcpPractitionerRequired.
     *                If the provided node is null, the method returns null.
     * @return an Organization object built from the provided node's attributes,
     *         or null if the input node is null.
     */
    public static Organization buildOrganization(Node orgNode) {
        if (orgNode == null) {
            return null;
        }
        return Organization.builder()
                .elementId(orgNode.elementId())
                .name(orgNode.get("name").asString())
                .code(orgNode.get("code").asString())
                .aliasName(orgNode.get("aliasName").asString())
                .type(orgNode.get("type").asString())
                .atypical(orgNode.get("atypical").asBoolean())
                .capitated(orgNode.get("capitated").asBoolean())
                .pcpPractitionerRequired(orgNode.get("pcpPractitionerRequired").asBoolean())
                .build();
    }

    /**
     * Builds a Cypher query to fetch entities of the specified type based on given identifiers.
     * The method generates a query that matches the specified entity type and constructs
     * appropriate filtering conditions using the provided identifiers.
     *
     * @param entityType the type of the entity to be queried (e.g., "ORG" for Organization)
     * @param identifiers a map of identifier types and their corresponding values to use for filtering
     * @param matchAll if true, all identifier conditions must match (AND logic), otherwise any condition can match (OR logic)
     * @return a {@link CypherQuery} object containing the generated Cypher query and its parameters
     */
    public static CypherQuery buildCypher(String entityType, Map<String, String> identifiers, boolean matchAll){
        String entityVariable = "";
        String entityLabel = "";
        if (entityType.equals("ORG")){
            entityVariable = "org";
            entityLabel = "Organization";
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
                    "EXISTS { MATCH ("+entityVariable+") -[:HAS_%s]->(%s:Identifier) WHERE %s.value = $%s }",
                    relType, alias, alias, paramName));
            idx++;
        }

        String operator = matchAll ? " AND " : " OR ";
        String cypher = """
                MATCH ("""+entityVariable+
                """
                :"""+
                entityLabel+
                """
                )
                WHERE %s
                OPTIONAL MATCH (org)-[r]->(id:Identifier)
                RETURN DISTINCT org, collect(DISTINCT {relType: type(r), node: id}) AS identifiers
                """.formatted(String.join(operator, whereClauses));
        log.info("Cypher query to match orgs by identifiers: {}", cypher);
        return CypherQuery.builder()
                .cypher(cypher)
                .params(params)
                .build();
    }
}
