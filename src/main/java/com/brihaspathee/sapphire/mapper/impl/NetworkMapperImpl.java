package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.mapper.interfaces.LineOfBusinessMapper;
import com.brihaspathee.sapphire.mapper.interfaces.NetworkMapper;
import com.brihaspathee.sapphire.mapper.interfaces.ProductMapper;
import com.brihaspathee.sapphire.model.NetworkDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:01 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NetworkMapperImpl implements NetworkMapper {

    /**
     * A mapper component used for converting LineOfBusiness entities to their corresponding
     * DTO representations and vice versa. This field holds a reference to the {@link LineOfBusinessMapper}
     * implementation and is utilized to facilitate mapping operations involving Line of Business objects.
     */
    private final LineOfBusinessMapper lineOfBusinessMapper;

    /**
     * A reference to the {@link ProductMapper} implementation
     * used for converting Product entities to ProductDto objects and vice versa.
     */
    private final ProductMapper productMapper;

    /**
     * Converts a {@link Network} entity to a {@link NetworkDto}.
     *
     * @param network the {@link Network} entity to be converted
     * @return the corresponding {@link NetworkDto} representation of the provided {@link Network} entity
     */
    @Override
    public NetworkDto toNetDto(Network network) {
        if (network == null){
            return null;
        }
        NetworkDto networkDto = NetworkDto.builder()
                .elementId(network.getElementId())
                .code(network.getCode())
                .name(network.getName())
                .build();
        if (network.getPartOfProducts() != null && !network.getPartOfProducts().isEmpty()) {
            networkDto.setPartOfProducts(productMapper.toProductDtoList(network.getPartOfProducts()));
        }
        if (network.getPartOfLinesOfBusiness() != null && !network.getPartOfLinesOfBusiness().isEmpty()) {
            networkDto.setPartOfLineOfBusinesses(lineOfBusinessMapper.toLineOfBusinessDtoList(network.getPartOfLinesOfBusiness()));
        }
        return networkDto;
    }

    /**
     * Converts a list of Network entities into a list of NetworkDto objects.
     *
     * @param networks the list of Network entities to be converted
     * @return a list of NetworkDto objects corresponding to the provided Network entities
     */
    @Override
    public List<NetworkDto> toNetDtoList(List<Network> networks) {
        return networks.stream().map(this::toNetDto).toList();
    }
}
