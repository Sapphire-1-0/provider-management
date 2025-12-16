package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.entity.Qualification;
import com.brihaspathee.sapphire.model.web.IdentifierInfo;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, December 2025
 * Time: 05:44
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class BuildPractitionerEntity {

    /**
     * Builds a Practitioner object using the provided practitioner information.
     * This method extracts data from the given Value object, constructs the
     * Practitioner entity, and populates its identifiers and qualifications.
     *
     * @param pracInfo The Value object containing practitioner data, including
     *                 node information, identifiers, and qualifications.
     * @return A fully constructed Practitioner object with associated identifiers
     *         and qualifications.
     */
    public static Practitioner buildPractitioner(Value pracInfo){
        Node pracNode = pracInfo.get("prac").asNode();
        Practitioner practitioner = buildPractitioner(pracNode);
        Value pracDetails = pracInfo.get("pracDetails");
        List<Map<String, Object>> idList = pracDetails.get("identifiers").asList(Value::asMap);
        List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
        practitioner.setIdentifiers(identifiers);
        List<Node> qualNodes = pracDetails.get("qualifications").asList(Value::asNode);
        List<Qualification> qualifications = BuilderUtil.buildQualifications(qualNodes);
        practitioner.setQualifications(qualifications);
        return practitioner;
    }

    /**
     * Constructs a Practitioner object using the provided Node representation.
     * The method extracts various properties from the given Node and populates
     * a Practitioner object with corresponding values.
     *
     * @param practitionerNode the Node containing practitioner details, such as
     *                         element ID, name attributes, gender, birth date,
     *                         and alternative names. If the parameter is null,
     *                         the method will return null.
     * @return a Practitioner object populated with the data extracted from the
     *         provided Node, or null if the input Node is null.
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
     * Constructs a CypherQuery object based on the provided practitioner search request.
     * The method dynamically builds a Cypher query string and parameter map to search
     * for practitioners in the database. It supports filters such as names, identifiers,
     * and pagination, and allows combining conditions with logical operators based on the flag.
     *
     * @param searchRequest the PractitionerSearchRequest containing the search criteria,
     *                      including first name, last name, page number, page size, and identifiers.
     * @param matchAll      a boolean flag indicating whether all conditions should be matched (AND)
     *                      or at least one condition should be matched (OR).
     * @return a CypherQuery object containing the constructed Cypher query string and its
     *         associated parameters for searching practitioners in the database.
     */
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

    /**
     * Constructs a Cypher query string based on the provided parameters. The query
     * is dynamically created either with or without utilizing search terms against
     * a full-text index, depending on the value of the <code>useSearchTerms</code> parameter.
     * The generated query also includes a condition specified by the <code>identifierWhere</code> parameter.
     *
     * @param useSearchTerms a boolean indicating whether to execute a full-text search against
     *                       a Lucene index on practitioner names.
     *                       If true, the query will include a call to the Lucene index.
     *                       Otherwise, it will match all practitioners based on the provided condition.
     * @param identifierWhere a String representing additional filtering conditions to apply
     *                        within the query. It is expected to be a valid Cypher WHERE clause fragment.
     * @return a String representing the constructed Cypher query, which can be used to search
     *         and retrieve practitioners and associated identifiers from the database.
     */
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
}
