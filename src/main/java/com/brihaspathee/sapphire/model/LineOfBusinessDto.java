package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:04 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineOfBusinessDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to reference or track a specific entity
     * within its respective context or scope.
     */
    private String elementId;

    /**
     * Represents the code associated with the line of business.
     * This variable is used to store a specific code that uniquely identifies
     * or categorizes a line of business within the system.
     */
    private String code;

    /**
     * Represents the name of the line of business.
     * This variable is used to store descriptive information
     * related to a specific business line within the system.
     */
    private String name;

    /**
     * Returns a string representation of the LineOfBusinessDto object.
     * The string includes the values of the elementId, code, and name properties.
     *
     * @return a string representation of the LineOfBusinessDto object.
     */
    @Override
    public String toString() {
        return "LineOfBusinessDto{" +
                "elementId='" + elementId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
