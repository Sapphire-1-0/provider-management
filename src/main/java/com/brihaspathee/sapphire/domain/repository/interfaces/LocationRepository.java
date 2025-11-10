package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;

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
     * Retrieves the organization and its associated locations based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @return an Organization object containing the organization details and its associated locations
     */
    Organization findLocationsByOrgAndNet(String orgId, String netId);
}
