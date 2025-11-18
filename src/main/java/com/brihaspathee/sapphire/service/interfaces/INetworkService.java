package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.model.NetworkList;
import com.brihaspathee.sapphire.model.web.NetworkSearchRequest;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12, November 2025
 * Time: 12:51
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface INetworkService {

    /**
     * Retrieves a list of networks based on the specified search criteria.
     *
     * @param networkSearchRequest an instance of {@code NetworkSearchRequest} that contains
     *                             the search criteria for retrieving networks, including
     *                             parameters such as product code, network code, and network name
     * @return an instance of {@code NetworkList} containing the collection of networks
     *         that match the provided search criteria
     */
    NetworkList getNetworks(NetworkSearchRequest networkSearchRequest);
}
