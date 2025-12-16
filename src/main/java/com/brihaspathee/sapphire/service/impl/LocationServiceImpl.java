package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Location;
import com.brihaspathee.sapphire.domain.repository.interfaces.LocationRepository;
import com.brihaspathee.sapphire.model.LocationDto;
import com.brihaspathee.sapphire.model.web.LocationSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, November 2025
 * Time: 04:36
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {


    /**
     * Repository interface for accessing and performing operations
     * on location-related data in the data persistence layer.
     * This field is used by the service layer to interact with
     * location entities and manage search, retrieval, and persistence
     * of location data.
     */
    private final LocationRepository locationRepository;

    /**
     * Retrieves the location details based on the specified location code.
     *
     * @param locationCode a {@code String} representing the unique code used to identify a location
     * @return an instance of {@code LocationDto} containing the details of the location corresponding
     * to the provided location code
     */
    @Override
    public LocationDto getLocationByCode(String locationCode) {
        Location location = locationRepository.findLocationByCode(locationCode);
        return toLocationDto(location);
    }

    /**
     * Retrieves a list of locations that match the specified search criteria.
     *
     * @param locationSearchRequest an instance of {@code LocationSearchRequest} containing
     *                              search parameters such as location name, address, city,
     *                              state, zip code, and county FIPS code
     * @return a list of {@code LocationDto} objects representing the locations that satisfy
     * the search criteria
     */
    @Override
    public List<LocationDto> getLocations(LocationSearchRequest locationSearchRequest) {
        log.info("Fetching locations from ATON:{}", locationSearchRequest);
        List<Location> locations = locationRepository.findLocations(locationSearchRequest);
        return toLocationDtos(locations);
    }

    /**
     * Converts a list of {@code Location} objects into a list of {@code LocationDto} objects.
     * If the input list is null, this method returns null.
     *
     * @param locations the list of {@code Location} entities to be converted to DTOs
     * @return a list of {@code LocationDto} objects containing the mapped information from the input
     *         list of {@code Location} entities, or null if the input list is null
     */
    private List<LocationDto> toLocationDtos(List<Location> locations) {
        if (locations == null){
            return null;
        }
        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : locations) {
            LocationDto locationDto = toLocationDto(location);
            locationDtos.add(locationDto);
        }
        return locationDtos;
    }

    /**
     * Converts a {@code Location} entity to its corresponding {@code LocationDto}.
     *
     * @param location the {@code Location} entity to be converted to a DTO
     * @return a {@code LocationDto} containing the mapped information from the given {@code Location} entity
     */
    private LocationDto toLocationDto(Location location){
        LocationDto locationDto = LocationDto.builder()
                .elementId(location.getElementId())
                .name(location.getName())
                .streetAddress(location.getStreetAddress())
                .secondaryAddress(location.getSecondaryAddress())
                .city(location.getCity())
                .state(location.getState())
                .zipCode(location.getZipCode())
                .countyFIPS(location.getCountyFIPS())
                .build();
        return locationDto;
    }
}
