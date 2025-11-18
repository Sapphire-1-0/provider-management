package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.web.NetworkSearchRequest;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, November 2025
 * Time: 04:47
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface NetworkRepository {

    /**
     * Retrieves a list of networks based on the specified search criteria.
     *
     * @param networkSearchRequest the search request containing the criteria
     *                             for filtering networks, such as product code,
     *                             network code, or network name
     * @return a list of Network objects that match the provided search criteria
     */
    List<Network> findNetworks(NetworkSearchRequest networkSearchRequest);


    /**
     * Retrieves all the networks associated with the organization
     *
     * @param elementId the unique identifier of the network used to find the associated organizations
     * @return the Organization object and its associated network
     */
    Organization findNetworksByOrganization(String elementId);

    Organization findNetworksByOrgAndLoc(String orgId, String locId);
}
