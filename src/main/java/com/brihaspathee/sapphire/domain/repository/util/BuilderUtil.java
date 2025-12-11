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
     * Constructs and returns an {@link Organization} instance populated with
     * data extracted from the provided Neo4j {@link Node}.
     *
     * @param orgNode the Neo4j {@link Node} containing organization data
     * @return an {@link Organization} initialized with the extracted data
     */
    public static Organization buildOrganization(Node orgNode) {
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
                .pcpPractitionerRequired(orgNode.get("pcpPractitionerRequired").asBoolean())
                .build();
    }

    /**
     * Constructs and returns a {@link Practitioner} instance populated with data extracted
     * from a provided Neo4j {@link Node}.
     *
     * @param practitionerNode the Neo4j {@link Node} containing practitioner data. If null, the method returns null.
     *                         It is expected that the Node provides values for keys such as "elementId", "firstName",
     *                         "lastName", "middleName", and "gender".
     * @return a {@link Practitioner} object constructed from the given {@link Node} data,
     *         or null if the input {@link Node} is null.
     */
    public static Practitioner buildPractitioner(Node practitionerNode){
        if (practitionerNode == null) {
            return null;
        }
        return Practitioner.builder()
                .elementId(practitionerNode.elementId())
                .code(practitionerNode.get("code").asString())
                .firstName(practitionerNode.get("firstName").asString())
                .lastName(practitionerNode.get("lastName").asString())
                .middleName(practitionerNode.get("middleName").asString(null))
                .gender(practitionerNode.get("gender").asString(null))
                .birthDate(practitionerNode.get("birthDate").asLocalDate(null))
                .altFirstName(practitionerNode.get("altFirstName").asString(null))
                .altLastName(practitionerNode.get("altLastName").asString(null))
                .altMiddleName(practitionerNode.get("altMiddleName").asString(null))
                .build();
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
                .code(productNode.get("code").asString())
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

    /**
     * Builds a search string by concatenating non-blank terms from the provided list.
     * Each term is appended with a tilde (~) at the end, and terms are separated by spaces.
     * Trims the input terms before appending them, and skips null or blank terms.
     *
     * @param searchTerms the list of terms to be used for building the search string.
     *                    May contain null or blank values, which will be ignored.
     * @return a concatenated search string composed of valid terms from the list.
     *         Returns an empty string if the input list is null or contains no valid terms.
     */
    private static String buildSearchString(List<String> searchTerms){
        StringBuilder sb = new StringBuilder();
        if (searchTerms != null && !searchTerms.isEmpty()){
            for (String term : searchTerms) {
                if (term != null && !term.isBlank()){
                    if (!sb.isEmpty()){
                        sb.append(" ");
                    }
                    sb.append(term.trim()).append("~");
                }
            }
        }
        return sb.toString().trim();
    }

    public static CypherQuery buildPractitionerSearchCypher(PractitionerSearchRequest searchRequest,
                                                       boolean matchAll){
        Map<String, Object> params = new HashMap<>();
        List<String> whereClauses = new ArrayList<>();
        int skip = searchRequest.getPageNumber() * searchRequest.getPageSize();
        params.put("skip", skip);
        params.put("limit", searchRequest.getPageSize());
        List<String> names =
                Stream.of(searchRequest.getFirstName(), searchRequest.getLastName())
                        .filter(Objects::nonNull)
                        .toList();
        String searchString = buildSearchString(names);
        boolean useSearchTerms = !searchString.isBlank();
        if (useSearchTerms){
            params.put("searchString", searchString);
        }
        // ---- Build identifier EXISTS clauses ----
        int idx = 0;
        if (searchRequest.getIdentifiers() != null && !searchRequest.getIdentifiers().isEmpty()){
            for (IdentifierInfo identifierInfo : searchRequest.getIdentifiers()) {
                String relType = identifierInfo.getIdentifierType();
                String paramName = "val" + idx;
                String alias = "id" + idx;

                params.put(paramName, identifierInfo.getIdentifierValue());

                whereClauses.add(String.format(
                        """
                        EXISTS {
                            MATCH (prac)-[:HAS_%s]->(%s:Identifier)
                            WHERE %s.value = $%s
                        }
                        """,
                        relType, alias, alias, paramName
                ));
                idx++;
            }
        }

        String operator = matchAll ? " AND " : " OR ";
        String identifierWhere = whereClauses.isEmpty()
                ? "true"
                : String.join(operator, whereClauses);
        // ---- Build final Cypher ----
        String cypher = getCypherQuery(useSearchTerms, identifierWhere);
        return CypherQuery.builder()
                .cypher(cypher)
                .params(params)
                .build();
    }

    private static String getCypherQuery(boolean useSearchTerms, String identifierWhere) {
        String cypher;
        if (useSearchTerms) {
            cypher =
                    """
                    CALL db.index.fulltext.queryNodes('practitioner_name_lucene_index', $searchString)
                    YIELD node AS prac, score
                    WHERE score > 1.0
        
                    WITH prac
                    WHERE %s
        
                    OPTIONAL MATCH (prac)-[r]->(id:Identifier)
                    RETURN DISTINCT prac, collect(DISTINCT {relType: type(r), node: id}) AS identifiers
                    SKIP $skip LIMIT $limit
                    """.formatted(identifierWhere);
        }else{
            cypher =
                    """
                    MATCH (prac:Practitioner)
                    WHERE %s
                    OPTIONAL MATCH (prac)-[r]->(id:Identifier)
                    RETURN DISTINCT prac, collect(DISTINCT {relType: type(r), node: id}) AS identifiers
                    SKIP $skip LIMIT $limit
                    """.formatted(identifierWhere);
        }
        return cypher;
    }
}
