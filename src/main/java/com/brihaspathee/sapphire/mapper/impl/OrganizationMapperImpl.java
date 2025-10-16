package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.mapper.interfaces.IOrganizationMapper;
import com.brihaspathee.sapphire.model.OrganizationDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 16, October 2025
 * Time: 05:14
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationMapperImpl implements IOrganizationMapper {

    /**
     * Converts an Organization entity to an OrganizationDto object.
     *
     * @param organization the Organization entity to be converted;
     *                     if null, the method will return null.
     * @return an OrganizationDto object built based on the data
     *         from the provided Organization entity, or null if
     *         the input organization is null.
     */
    @Override
    public OrganizationDto toOrganizationDto(Organization organization) {
        if(organization == null) {
            return null;
        }
        OrganizationDto organizationDto = OrganizationDto.builder()
                .name(organization.getName())
                .type(organization.getType())
                .aliasName(organization.getAliasName())
                .atypical(organization.getAtypical())
                .capitated(organization.getCapitated())
                .pcpPractitionerRequired(organization.getPcpPractitionerRequired())
                .build();
        return organizationDto;
    }

    /**
     * Converts an OrganizationDto object to an Organization entity.
     *
     * @param organizationDto the OrganizationDto object to be converted;
     *                        if null, the method will return null.
     * @return an Organization entity built based on the data from the provided OrganizationDto,
     *         or null if the input OrganizationDto is null.
     */
    @Override
    public Organization toOrganization(OrganizationDto organizationDto) {
        if(organizationDto == null) {
            return null;
        }
        Organization organization = Organization.builder()
                .name(organizationDto.getName())
                .type(organizationDto.getType())
                .aliasName(organizationDto.getAliasName())
                .atypical(organizationDto.getAtypical())
                .capitated(organizationDto.getCapitated())
                .pcpPractitionerRequired(organizationDto.getPcpPractitionerRequired())
                .build();
        return organization;
    }

    /**
     * Converts a list of Organization entities to a list of OrganizationDto objects.
     *
     * @param organizations the list of Organization entities to be converted
     * @return a list of OrganizationDto objects corresponding to the provided Organization entities,
     *         or null if the input list is null or empty
     */
    @Override
    public List<OrganizationDto> toOrganizationDtoList(List<Organization> organizations) {
        if(organizations != null && !organizations.isEmpty()) {
            return organizations.stream()
                    .map(this::toOrganizationDto)
                    .toList();
        }
        else{
            return null;
        }
    }

    /**
     * Converts a list of OrganizationDto objects to a list of Organization entities.
     *
     * @param organizationDtos the list of OrganizationDto objects to be converted
     * @return a list of Organization entities corresponding to the provided OrganizationDto objects,
     *         or null if the input list is null or empty
     */
    @Override
    public List<Organization> toOrganizationList(List<OrganizationDto> organizationDtos) {
        if(organizationDtos != null && !organizationDtos.isEmpty()) {
            return organizationDtos.stream()
                    .map(this::toOrganization)
                    .toList();
        }else{
            return null;
        }
    }
}
