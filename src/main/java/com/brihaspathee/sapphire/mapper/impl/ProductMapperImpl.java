package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Product;
import com.brihaspathee.sapphire.mapper.interfaces.ProductMapper;
import com.brihaspathee.sapphire.model.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:07 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class ProductMapperImpl implements ProductMapper {

    /**
     * Converts a {@link Product} entity to a {@link ProductDto}.
     *
     * @param product the {@link Product} entity to be converted
     * @return the corresponding {@link ProductDto} representation of the provided {@link Product} entity,
     *         or null if the input is null
     */
    @Override
    public ProductDto toProductDto(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDto.builder()
                .elementId(product.getElementId())
                .name(product.getName())
                .code(product.getCode())
                .build();
    }

    /**
     * Converts a list of Product entities into a list of ProductDto objects.
     *
     * @param products the list of Product entities to be converted
     * @return a list of ProductDto objects corresponding to the provided Product entities
     */
    @Override
    public List<ProductDto> toProductDtoList(List<Product> products) {
        return products.stream().map(this::toProductDto).toList();
    }
}
