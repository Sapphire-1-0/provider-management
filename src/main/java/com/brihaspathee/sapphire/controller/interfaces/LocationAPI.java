package com.brihaspathee.sapphire.controller.interfaces;


import com.brihaspathee.sapphire.model.LocationList;
import com.brihaspathee.sapphire.model.web.LocationSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 05:13
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/sapphire/location")
public interface LocationAPI {

    /**
     * Performs a search for networks based on the provided location search criteria.
     *
     * @param locationSearchRequest the request object containing location search criteria,
     *                              such as location name, address, city, state, and zip code.
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a
     *         LocationList object, which represents the collection of locations
     *         retrieved as part of the search operation.
     */
    @PostMapping("/_search")
    ResponseEntity<SapphireAPIResponse<LocationList>> getLocations(@RequestBody LocationSearchRequest locationSearchRequest);
}
