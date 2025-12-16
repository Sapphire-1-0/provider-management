package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Product;
import com.brihaspathee.sapphire.model.ProductDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:06 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface ProductMapper {

    /**
     * Converts the given Product entity into a ProductDto object.
     *
     * @param product the Product entity to be converted
     * @return a ProductDto object corresponding to the provided Product entity
     */
    ProductDto toProductDto(Product product);

    /**
     * Converts a list of Product entities into a list of ProductDto objects.
     *
     * @param products the list of Product entities to be converted
     * @return a list of ProductDto objects corresponding to the provided Product entities
     */
    List<ProductDto> toProductDtoList(List<Product> products);
}
