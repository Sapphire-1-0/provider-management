package com.brihaspathee.sapphire.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 13, October 2025
 * Time: 05:59
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityIdentifier {

    @Id
    private String elementId;

    @Property("value")
    private String value;

    @Property
    private LocalDate startDate;

    @Property
    private LocalDate endDate;
}
