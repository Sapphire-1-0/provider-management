package com.brihaspathee.sapphire.domain.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, August 2025
 * Time: 05:17
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Organization")
public class Organization {

    @Id
    private String elementId;

    @Property("name")
    private String name;

    @Property("aliasName")
    private String aliasName;

    @Property("type")
    private String type;

    @Property("atypical")
    private Boolean atypical;

    @Property("capitated")
    private Boolean capitated;

    @Property("pcpPractitionerRequired")
    private Boolean pcpPractitionerRequired;

//    @Relationship(type = "HAS_IDENTIFIER", direction = Relationship.Direction.OUTGOING)
//    private Set<Identifier> identifiers = new HashSet<>();
}
