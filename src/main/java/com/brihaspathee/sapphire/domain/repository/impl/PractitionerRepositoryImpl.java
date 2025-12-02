package com.brihaspathee.sapphire.domain.repository.impl;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.repository.Neo4jQueryExecutor;
import com.brihaspathee.sapphire.domain.repository.interfaces.PractitionerRepository;
import com.brihaspathee.sapphire.domain.repository.util.BuilderUtil;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.utils.CypherLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:12 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PractitionerRepositoryImpl implements PractitionerRepository {

    /**
     * Executes Cypher queries against a Neo4j database. This component is responsible
     * for managing read and write operations to the database by leveraging the Neo4j driver.
     * It provides methods for executing parameterized queries and mapping query results
     * to custom domain objects via a functional RecordMapper interface.
     *
     * Used within the repository to perform database operations required for
     * implementing repository methods.
     */
    private final Neo4jQueryExecutor queryExecutor;

    /**
     * A utility used to load Cypher query files from the classpath.
     * This component facilitates retrieving and caching the contents
     * of Cypher query files stored in the application's resources directory,
     * minimizing repeated file I/O operations. Leveraged for retrieving Cypher
     * queries used in database operations by the repository implementation.
     */
    private final CypherLoader cypherLoader;

    /**
     * Retrieves a Practitioner entity based on the provided practitioner code.
     *
     * @param code the unique code associated with the practitioner to be retrieved
     * @return the Practitioner object corresponding to the specified code,
     *         or null if no practitioner is found
     */
    @Override
    public Practitioner findPractitionerByCode(String code) {
        String cypher = cypherLoader.load("get_prac_by_code.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("pracCode", code);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Node pracNode = record.get("prac").asNode();
            Practitioner practitioner = BuilderUtil.buildPractitioner(pracNode);
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }

    /**
     * Finds practitioners based on the criteria specified in the PractitionerSearchRequest.
     *
     * @param practitionerSearchRequest the search request containing criteria to filter practitioners
     * @return a list of practitioners matching the search criteria; an empty list if no practitioners are found
     */
    @Override
    public List<Practitioner> findPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        return List.of();
    }

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return the Practitioner object corresponding to the specified ID, or null if no practitioner is found
     */
    @Override
    public Practitioner findPractitionerById(String practitionerId) {
        String cypher = cypherLoader.load("get_prac_by_id.cypher");
        Map<String, Object> params = new HashMap<>();
        params.put("pracId", practitionerId);
        params.put("orgId", null);
        List<Practitioner> practitioners = queryExecutor.executeReadQuery(cypher, params, record -> {
            Node pracNode = record.get("prac").asNode();
            Practitioner practitioner = BuilderUtil.buildPractitioner(pracNode);
            List<Map<String, Object>> idList = record.get("identifiers").asList(Value::asMap);
            List<Identifier> identifiers = BuilderUtil.buildIdentifiers(idList);
            practitioner.setIdentifiers(identifiers);
            return practitioner;
        });
        return practitioners.isEmpty() ? null : practitioners.getFirst();
    }
}
