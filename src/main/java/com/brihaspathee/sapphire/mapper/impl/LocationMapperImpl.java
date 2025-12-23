package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Location;
import com.brihaspathee.sapphire.domain.entity.LocationNetworkServiceInfo;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import com.brihaspathee.sapphire.mapper.interfaces.LocationMapper;
import com.brihaspathee.sapphire.model.LocationDto;
import com.brihaspathee.sapphire.model.LocationNetworkDto;
import com.brihaspathee.sapphire.model.LocationNetworkSpanDto;
import com.brihaspathee.sapphire.model.PanelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, December 2025
 * Time: 05:58
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class LocationMapperImpl implements LocationMapper {
    /**
     * Converts a {@link Location} object to a {@link LocationDto} object.
     *
     * @param location the {@link Location} object to be converted
     * @return a {@link LocationDto} object constructed from the provided {@link Location} object
     */
    @Override
    public LocationDto toLocationDto(Location location) {
        if (location == null) return null;
        LocationDto locationDto =  LocationDto.builder()
                .elementId(location.getElementId())
                .name(location.getName())
                .streetAddress(location.getStreetAddress())
                .secondaryAddress(location.getSecondaryAddress())
                .city(location.getCity())
                .state(location.getState())
                .zipCode(location.getZipCode())
                .build();
        if (location.getNetworkServiceInfo() != null){
            LocationNetworkDto locationNetworkDto = toLocationNetworkDto(location.getNetworkServiceInfo());
            locationDto.setLocationNetwork(locationNetworkDto);
        }
        return locationDto;
    }

    /**
     * Converts a list of {@link Location} objects into a list of {@link LocationDto} objects.
     *
     * @param locations the list of {@link Location} objects to be converted
     * @return a list of {@link LocationDto} objects constructed from the provided {@link Location} list
     */
    @Override
    public List<LocationDto> toLocationDtoList(List<Location> locations) {
        return locations.stream().map(this::toLocationDto).toList();
    }

    /**
     * Converts a {@code LocationNetworkServiceInfo} object to a {@code LocationNetworkDto}.
     * Extracts and maps relevant data from the input object, including panel details,
     * location network spans, and PCP status.
     *
     * @param locationNetworkServiceInfo an object containing information about the location network services
     *                                   to be converted into a {@code LocationNetworkDto}
     * @return a {@code LocationNetworkDto} object populated with data extracted from
     *         the given {@code LocationNetworkServiceInfo}
     */
    private static LocationNetworkDto toLocationNetworkDto(LocationNetworkServiceInfo locationNetworkServiceInfo) {
        LocationNetworkDto locationNetworkDto = LocationNetworkDto.builder().build();
//        LocationNetworkServiceInfo locationNetworkServiceInfo = network.getNetworkServiceInfo();
        HasPanel hasPanel = locationNetworkServiceInfo.getHasPanel();
        List<RoleLocationServes> roleLocationServesList = locationNetworkServiceInfo.getRoleLocationServes();
        if (hasPanel != null) {
            PanelDto panelDto = PanelDto.builder()
                    .genderLimitation(hasPanel.getGenderLimitation())
                    .ageLimitation(hasPanel.getAgeLimitation())
                    .highestAgeMonths(hasPanel.getHighestAgeMonths())
                    .lowestAgeMonths(hasPanel.getLowestAgeMonths())
                    .highestAgeYears(hasPanel.getHighestAgeYears())
                    .lowestAgeYears(hasPanel.getLowestAgeYears())
                    .status(hasPanel.getStatus())
                    .build();
            locationNetworkDto.setPanel(panelDto);
        }
        if (roleLocationServesList != null && !roleLocationServesList.isEmpty()) {
            List<LocationNetworkSpanDto> spanDtos = new ArrayList<>();
            for (RoleLocationServes roleLocationServes : roleLocationServesList) {
                LocationNetworkSpanDto spanDto = LocationNetworkSpanDto.builder()
                        .startDate(roleLocationServes.getStartDate())
                        .endDate(roleLocationServes.getEndDate())
                        .termReason(roleLocationServes.getTermReason())
                        .build();
                spanDtos.add(spanDto);
            }
            locationNetworkDto.setSpans(spanDtos);
        }
        locationNetworkDto.setIsPCP(locationNetworkServiceInfo.getIsPCP());
        return locationNetworkDto;
//        networkDto.setLocationNetwork(locationNetworkDto);
    }
}
