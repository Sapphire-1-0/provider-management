package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.model.LocationDto;
import com.brihaspathee.sapphire.model.web.LocationSearchRequest;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 16:32
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface ILocationService {

    /**
     * Retrieves the location details based on the specified location code.
     *
     * @param locationCode a {@code String} representing the unique code used to identify a location
     * @return an instance of {@code LocationDto} containing the details of the location corresponding
     *         to the provided location code
     */
    LocationDto getLocationByCode(String locationCode);

    /**
     * Retrieves a list of locations that match the specified search criteria.
     *
     * @param locationSearchRequest an instance of {@code LocationSearchRequest} containing
     *                              search parameters such as location name, address, city,
     *                              state, zip code, and county FIPS code
     * @return a list of {@code LocationDto} objects representing the locations that satisfy
     *         the search criteria
     */
    List<LocationDto> getLocations(LocationSearchRequest locationSearchRequest);
}
