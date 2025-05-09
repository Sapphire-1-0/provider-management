package com.brihaspathee.sapphire.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 5/9/25
 * Time: 1:43 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
public class OrganizationList {

    /**
     * Represents a list of OrganizationDto objects.
     * This variable is used to store multiple organizations,
     * where each organization is represented by an instance of OrganizationDto.
     */
    private List<OrganizationDto> organizationList;
}
