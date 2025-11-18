package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.NetworkAPI;
import com.brihaspathee.sapphire.model.NetworkList;
import com.brihaspathee.sapphire.model.web.NetworkSearchRequest;
import com.brihaspathee.sapphire.service.impl.NetworkService;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12, November 2025
 * Time: 12:49
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class NetworkAPIImpl implements NetworkAPI {

    /**
     * Reference to the {@link NetworkService} that provides the core logic for retrieving,
     * processing, and managing network-related data within the application.
     * This service is utilized to implement the functionality defined in the {@link NetworkAPIImpl}.
     */
    private final NetworkService networkService;

    /**
     * Retrieves a list of networks as part of a search operation.
     *
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a NetworkList,
     * which represents the collection of networks retrieved by the search.
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<NetworkList>> getNetworks(NetworkSearchRequest networkSearchRequest) {
        NetworkList networkList = networkService.getNetworks(networkSearchRequest);

        SapphireAPIResponse<NetworkList> apiResponse =
                SapphireAPIResponse.<NetworkList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Networks Retrieved successfully")
                        .response(networkList)
                        .timestamp(LocalDateTime.now())
                        .reason("Networks Retrieved successfully")
                        .developerMessage("Networks Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
