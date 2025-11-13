package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.domain.entity.Product;
import com.brihaspathee.sapphire.domain.repository.interfaces.NetworkRepository;
import com.brihaspathee.sapphire.model.NetworkDto;
import com.brihaspathee.sapphire.model.NetworkList;
import com.brihaspathee.sapphire.model.ProductDto;
import com.brihaspathee.sapphire.service.interfaces.INetworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12, November 2025
 * Time: 12:52
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NetworkService implements INetworkService {

    /**
     * Repository interface for performing CRUD operations and queries on network-related data.
     * Used by the service layer to interact with the persistence layer for network entities.
     */
    private final NetworkRepository networkRepository;

    /**
     * Retrieves a list of all available networks.
     *
     * @return an instance of {@code NetworkList} containing a collection of networks,
     * where each network is represented as a {@code NetworkDto} object with
     * its associated attributes and details.
     */
    @Override
    public NetworkList getAllNetworks() {
        List<Network> networks = networkRepository.findAll();
        List<NetworkDto> networkDtos = networks.stream().map(NetworkService::toNetworkDto).toList();
        return NetworkList.builder()
                .networks(networkDtos)
                .build();
    }

    /**
     * Converts a {@link Network} entity into a {@link NetworkDto} object.
     * The method maps attributes of the {@link Network} object to those of
     * the {@link NetworkDto} and also includes mapping its associated products
     * into {@link ProductDto} objects.
     *
     * @param network the {@link Network} entity to be converted. If null, the method returns null.
     * @return a {@link NetworkDto} object containing the mapped information from the input {@link Network}.
     *         Returns null if the input {@link Network} is null.
     */
    private static NetworkDto toNetworkDto(Network network) {
        if (network == null) {
            return null;
        }
        NetworkDto networkDto = NetworkDto.builder()
                .elementId(network.getElementId())
                .name(network.getName())
                .code(network.getCode())
                .isHNETNetwork(network.getIsHNETNetwork())
                .isVendorNetwork(network.getIsVendorNetwork())
                .build();
        List<Product> products = network.getPartOfProducts();
        List<ProductDto> productDtos = products.stream().map(product ->
                ProductDto.builder()
                        .elementId(product.getElementId())
                        .name(product.getName())
                        .code(product.getCode())
                        .build()).toList();
        networkDto.setPartOfProducts(productDtos);
        return networkDto;
    }
}
