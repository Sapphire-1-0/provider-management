package com.brihaspathee.sapphire.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 5/9/25
 * Time: 1:40 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
public class OrganizationDto {

    /**
     * Represents the name of the organization.
     * This variable is used to store the name of an organization
     * associated with this DTO.
     */
    private String organizationName;
}
