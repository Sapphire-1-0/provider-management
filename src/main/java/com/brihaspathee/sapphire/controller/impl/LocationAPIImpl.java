package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.LocationAPI;
import com.brihaspathee.sapphire.model.LocationDto;
import com.brihaspathee.sapphire.model.LocationList;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.web.LocationSearchRequest;
import com.brihaspathee.sapphire.service.impl.LocationService;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.brihaspathee.sapphire.utils.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 05:22
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationAPIImpl implements LocationAPI {

    /**
     * Reference to the {@link LocationService} that provides the core logic
     * for retrieving, processing, and managing location-related data within
     * the application. This service is utilized to implement functionality
     * defined in the {@link LocationAPIImpl}.
     */
    private final LocationService  locationService;

    /**
     * Retrieves location details based on the provided location code.
     *
     * @param locCode the unique code of the location to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse
     * that encapsulates a LocationDto representing the
     * details of the requested location
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<LocationDto>> getLocationByCode(String locCode) {
        LocationDto locationDto = locationService.getLocationByCode(locCode);
        SapphireAPIResponse<LocationDto> apiResponse =
                SapphireAPIResponse.<LocationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Location Retrieved successfully")
                        .response(locationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Location Retrieved successfully")
                        .developerMessage("Location Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Performs a search for networks based on the provided location search criteria.
     *
     * @param locationSearchRequest the request object containing location search criteria,
     *                              such as location name, address, city, state, and zip code.
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a
     * LocationList object, which represents the collection of locations
     * retrieved as part of the search operation.
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<LocationList>> getLocations(LocationSearchRequest locationSearchRequest) {
        log.info("Location API called with request:{}", locationSearchRequest);
        log.info("Is location search request empty? {}", ObjectUtils.isEmptyObject(locationSearchRequest));
        List<LocationDto> locationDtos = locationService.getLocations(locationSearchRequest);
        SapphireAPIResponse<LocationList> apiResponse =
                SapphireAPIResponse.<LocationList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Location Retrieved successfully")
                        .response(LocationList.builder()
                                .locations(locationDtos)
                                .build())
                        .timestamp(LocalDateTime.now())
                        .reason("Location Retrieved successfully")
                        .developerMessage("Location Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
