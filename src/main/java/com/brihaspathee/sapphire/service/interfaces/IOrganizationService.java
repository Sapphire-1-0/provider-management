package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.OrganizationDto;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:20
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface IOrganizationService {

    /**
     * Retrieves a list of all organizations.
     *
     * @return a list of OrganizationDto instances, where each instance represents
     *         the details of an individual organization
     */
    List<OrganizationDto> getAllOrganizations();

    /**
     * Retrieves a list of organizations that match the given identifiers.
     * The identifiers are provided as key-value pairs in a map, where the key
     * represents the type of identifier and the value represents the corresponding
     * identifier value.
     *
     * @param identifiers a map of identifiers where the key is the type of identifier
     *                    and the value is the identifier value
     * @return a list of OrganizationDto instances that match the given identifiers
     */
    List<OrganizationDto> getOrganizationsByIdentifiers(Map<String, String> identifiers);


    OrganizationDto getOrganizationAndNetworks(String elementId);
}
