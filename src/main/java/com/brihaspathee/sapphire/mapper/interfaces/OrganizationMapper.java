package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.OrganizationDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 28, August 2025
 * Time: 05:41
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface OrganizationMapper {

    /**
     * Converts the given Organization entity into an OrganizationDto object.
     *
     * @param organization the Organization entity to be converted
     * @return an OrganizationDto object corresponding to the provided Organization entity
     */
    OrganizationDto toOrganizationDto(Organization organization);

    /**
     * Converts the given OrganizationDto object to an Organization entity.
     *
     * @param organizationDto the OrganizationDto object to be converted
     * @return the Organization entity corresponding to the provided*/
    Organization toOrganization(OrganizationDto organizationDto);

    /**
     * Converts a list of Organization entities to a list of OrganizationDto objects.
     *
     * @param organizations the list of Organization entities to be converted
     * @return a list of OrganizationDto objects corresponding to the provided Organization entities
     */
    List<OrganizationDto> toOrganizationDtoList(List<Organization> organizations);

    /**
     * Converts a list of OrganizationDto objects to a list of Organization entities.
     *
     * @param organizationDtos the list of OrganizationDto objects to be converted
     * @return a list of Organization entities corresponding to the provided OrganizationDto objects
     */
    List<Organization> toOrganizationList(List<OrganizationDto> organizationDtos);
}
