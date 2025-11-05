package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.OrganizationRepository;
import com.brihaspathee.sapphire.util.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Override
    public List<Organization> findAll() {
        log.info("Fetching all organizations inside the repository method");
        String cypher = cypherLoader.load("get_all_organizations.cypher");
        List<Organization> organizations = queryExecutor.executeReadQuery(cypher, Map.of(),
                record -> {
                        Node node = record.get("n").asNode();
                        log.info("Organization name: {}", node.get("name").asString());
                        log.info("Element id of the Org:{}", node.elementId());
                        return Organization.builder()
                                .elementId(node.elementId())
                                .name(node.get("name").asString())
                                .build();
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
                           Organization.OrganizationBuilder builder =
                                   Organization.builder()
                                        .elementId(orgNode.elementId())
                                        .name(orgNode.get("name").asString())
                                        .aliasName(orgNode.get("alias").asString());
                           List<Identifier> identifiers = new ArrayList<>();
                           List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
                           for (Map<String, Object> idMap : idList) {
                               String relType = (String) idMap.get("relType");
                               Node idNode = (Node) idMap.get("node");
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
                           builder.identifiers(identifiers);
                           return builder.build();
                        });
        return organizations;
    }

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
                RETURN DISTINCT org
                """.formatted(String.join(operator, whereClauses));
        log.info("Cypher query to match orgs by identifiers: {}", cypher);
        log.info("Parameters for the cypher query: {}", params);
        return null;
    }
}
