package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Location;
import com.brihaspathee.sapphire.model.LocationDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, December 2025
 * Time: 05:57
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface LocationMapper {

    /**
     * Converts a {@link Location} object to a {@link LocationDto} object.
     *
     * @param location the {@link Location} object to be converted
     * @return a {@link LocationDto} object constructed from the provided {@link Location} object
     */
    LocationDto toLocationDto(Location location);

    /**
     * Converts a list of {@link Location} objects into a list of {@link LocationDto} objects.
     *
     * @param locations the list of {@link Location} objects to be converted
     * @return a list of {@link LocationDto} objects constructed from the provided {@link Location} list
     */
    List<LocationDto> toLocationDtoList(List<Location> locations);
}
