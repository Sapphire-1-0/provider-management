package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.model.NetworkList;

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
     * Retrieves a list of all available networks.
     *
     * @return an instance of {@code NetworkList} containing a collection of networks,
     *         where each network is represented as a {@code NetworkDto} object with
     *         its associated attributes and details.
     */
    NetworkList getAllNetworks();
}
