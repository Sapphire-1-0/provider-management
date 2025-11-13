package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 13, November 2025
 * Time: 05:27
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Unique identifier for an element within the system.
     * This property is used to uniquely identify an instance of the class
     * or a related entity in the context of the application, ensuring
     * traceability and consistency across data operations.
     */
    private String elementId;

    /**
     * Represents a code that uniquely identifies a product.
     * This property may be used to classify or categorize products
     * within the system based on predefined codes, ensuring better
     * organization and identification.
     */
    private String code;

    /**
     * Represents the name of the product.
     * This property serves as a human-readable identifier or descriptor
     * that provides additional context and clarity about the specific product
     * within the system.
     */
    private String name;

    /**
     * Generates a string representation of the Product object, including the values
     * of its properties: elementId, code, and name.
     *
     * @return A string representation of the Product object, formatted to display
     *         the elementId, code, and name values enclosed within curly braces.
     */
    @Override
    public String toString() {
        return "Product{" +
                "elementId='" + elementId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
