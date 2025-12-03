package com.brihaspathee.sapphire.domain.repository.util;

import lombok.*;

import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:26 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CypherQuery {

    private String cypher;

    private Map<String, Object> params;

    @Override
    public String toString() {
        return "CypherQuery{" +
                "cypher='" + cypher + '\'' +
                ", params=" + params +
                '}';
    }
}
