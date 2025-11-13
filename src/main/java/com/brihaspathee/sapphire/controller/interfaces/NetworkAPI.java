package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.model.NetworkList;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12, November 2025
 * Time: 12:47
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/sapphire/network")
public interface NetworkAPI {

    /**
     * Retrieves a list of networks as part of a search operation.
     *
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a NetworkList,
     *         which represents the collection of networks retrieved by the search.
     */
    @GetMapping("/_search")
    ResponseEntity<SapphireAPIResponse<NetworkList>> getNetworks();
}
