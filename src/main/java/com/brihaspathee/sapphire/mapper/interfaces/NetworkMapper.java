package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.model.NetworkDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:00 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface NetworkMapper {

    /**
     * Converts the given Network entity into a NetworkDto object.
     *
     * @param network the Network entity to be converted
     * @return a NetworkDto object corresponding to the provided Network entity
     */
    NetworkDto toNetDto(Network network);

    /**
     * Converts a list of Network entities into a list of NetworkDto objects.
     *
     * @param networks the list of Network entities to be converted
     * @return a list of NetworkDto objects corresponding to the provided Network entities
     */
    List<NetworkDto> toNetDtoList(List<Network> networks);
}
