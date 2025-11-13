package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 13, November 2025
 * Time: 05:41
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to reference or track a specific entity
     * within its respective context or scope.
     */
    private String elementId;

    /**
     * Represents the name of the product.
     * This variable is used to store the name associated with a product
     * within the system.
     */
    private String name;

    /**
     * Represents the code associated with a product.
     * This variable is used to store a specific code that identifies
     * or categorizes the product within the system.
     */
    private String code;

    /**
     * Returns a string representation of the ProductDto object.
     * The string includes the values of the elementId, name, and code properties.
     *
     * @return a string representation of the ProductDto object.
     */
    @Override
    public String toString() {
        return "ProductDto{" +
                "elementId='" + elementId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
