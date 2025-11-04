package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.util.List;

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
public class Organization {

    private String elementId;

    private String name;

    private String aliasName;

    private String type;

    private Boolean atypical;

    private Boolean capitated;

    private Boolean pcpPractitionerRequired;

    private List<Identifier> identifiers;

}
