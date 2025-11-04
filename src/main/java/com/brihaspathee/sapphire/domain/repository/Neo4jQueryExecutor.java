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

    private final Driver driver;

    private final String database;

    public Neo4jQueryExecutor(Driver driver, String database) {
        this.driver = driver;
        this.database = database;
    }

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

    public void executeWriteQuery(String cypher, Map<String, Object> parameters){
        try (Session session = driver.session(SessionConfig.forDatabase(database))) {
            session.executeWrite(tx -> tx.run(cypher, parameters));
        }
    }

    @FunctionalInterface
    public interface RecordMapper<T> {
        T map(org.neo4j.driver.Record record);
    }
}
