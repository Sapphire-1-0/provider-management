package com.brihaspathee.sapphire.domain.repository;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 14:16
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository
 * To change this template use File | Settings | File and Code Template
 */
@Repository
public class Neo4jQueryExecutor {

    /**
     * Represents the Neo4j driver used to establish a connection to the database,
     * execute Cypher queries, and manage transactions. It provides the interface
     * to interact with the Neo4j database in both read and write operations.
     * This driver is configured and utilized within the {@link Neo4jQueryExecutor} class.
     * <p>
     * It is instantiated externally and injected into this class to facilitate
     * database operations, ensuring thread-safety and proper resource management.
     */
    private final Driver driver;

    /**
     * Represents the name of the Neo4j database to which the driver connects.
     * This variable is used in the configuration of the session and ensures
     * that all queries executed through this instance are directed to the specified database.
     * It is set during the instantiation of the {@link Neo4jQueryExecutor} class and is immutable,
     * ensuring that the database context remains consistent throughout the lifetime of the object.
     */
    private final String database;

    /**
     * Constructs a new instance of the Neo4jQueryExecutor class.
     *
     * @param driver   the Neo4j driver used to establish a connection to the database,
     *                 execute Cypher queries, and manage transactions
     * @param database the name of the Neo4j database to which the driver connects,
     *                 ensuring queries are directed to the specified database
     */
    public Neo4jQueryExecutor(Driver driver, String database) {
        this.driver = driver;
        this.database = database;
    }

    /**
     * Executes a read-only Cypher query on a Neo4j database and maps the resulting records to a list of objects
     * of type T using the provided RecordMapper. This method establishes a read transaction and processes the
     * results returned by the query.
     *
     * @param <T>        the type of objects to be created from the query results
     * @param cypher     the Cypher query to execute
     * @param parameters the parameters to be passed to the Cypher query
     * @param mapper     the {@link RecordMapper} implementation used to map each record in the query result
     *                   into an object of type T
     * @return a list of objects of type T that were mapped from the query result
     */
    public <T> List<T> executeReadQuery(String cypher,
                                        Map<String, Object> parameters,
                                        RecordMapper<T> mapper){
        try (Session session = driver.session(SessionConfig.forDatabase(database))) {
            return session.executeRead(
                    tx -> {
                        var result = tx.run(cypher, parameters);
                        List<T> list = new ArrayList<>();
                        while (result.hasNext()) {
                            list.add(mapper.map(result.next()));
                        }
                        return list;
                    });
        }

    }

    /**
     * Executes a write Cypher query on a Neo4j database. This method establishes a write transaction
     * and passes the provided query and parameters to be executed within the transaction.
     *
     * @param cypher     the Cypher query to execute
     * @param parameters the parameters to be passed to the Cypher query
     */
    public void executeWriteQuery(String cypher, Map<String, Object> parameters){
        try (Session session = driver.session(SessionConfig.forDatabase(database))) {
            session.executeWrite(tx -> tx.run(cypher, parameters));
        }
    }

    /**
     * Functional interface for mapping a Neo4j {@code Record} into an object of type {@code T}.
     * This interface serves as a bridge to translate raw records retrieved from a Neo4j database
     * into domain-specific objects, making it easier to work with query results in an application.
     *
     * @param <T> the type of objects to which the {@code Record} should be mapped
     */
    @FunctionalInterface
    public interface RecordMapper<T> {
        T map(org.neo4j.driver.Record record);
    }
}
