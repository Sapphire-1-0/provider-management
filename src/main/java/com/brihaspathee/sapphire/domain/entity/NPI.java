package com.brihaspathee.sapphire.domain.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10/16/25
 * Time: 6:21 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node({"Identifier", "NPI"})
public class NPI extends EntityIdentifier{
}
