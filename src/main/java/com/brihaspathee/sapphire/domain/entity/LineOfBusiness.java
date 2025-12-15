package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, December 2025
 * Time: 05:26
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineOfBusiness {

    /**
     * Unique identifier for an element within the system.
     * This property is used to uniquely identify an instance of the class
     * or a related entity in the context of the application, ensuring
     * traceability and consistency across data operations.
     */
    private String elementId;

    /**
     * Represents a code that uniquely identifies an instance of the LineOfBusiness.
     * This property is used to classify or categorize lines of business within
     * the system based on predefined codes, supporting better organization
     * and identification.
     */
    private String code;

    /**
     * Represents the name of the line of business.
     * This property serves as a human-readable identifier or descriptor
     * that provides additional context and clarity about the specific
     * line of business within the system.
     */
    private String name;

    /**
     * Generates a string representation of the LineOfBusiness object, including the values
     * of its properties: elementId, code, and name.
     *
     * @return A string representation of the LineOfBusiness object, formatted to display
     *         the elementId, code, and name values enclosed within curly braces.
     */
    @Override
    public String toString() {
        return "LineOfBusiness{" +
                "elementId='" + elementId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
