package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Location;
import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.web.LocationSearchRequest;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, November 2025
 * Time: 07:08
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface LocationRepository {

    /**
     * Retrieves a location based on the provided location code.
     *
     * @param locationCode the unique code representing the location to be retrieved
     * @return the Location object associated with the given location code, or null if no location is found
     */
    Location findLocationByCode(String locationCode);

    /**
     * Retrieves the organization and all its associated locations based on the provided organization ID.
     *
     * @param organizationId the unique identifier of the organization
     * @return an Organization object containing the organization details and its associated locations
     */
    Organization findLocationsByOrganization(String organizationId);

    /**
     * Retrieves the organization and its associated locations based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @return an Organization object containing the organization details and its associated locations
     */
    Organization findLocationsByOrgAndNet(String orgId, String netId);

    /**
     * Retrieves a list of locations based on the specified search criteria.
     *
     * @param locationSearchRequest the search request containing the criteria
     *                              for filtering locations, such as name, address, city,
     *                              state, zip code, or county FIPS code
     * @return a list of Location objects that match the provided search criteria
     */
    List<Location> findLocations(LocationSearchRequest locationSearchRequest);
}
