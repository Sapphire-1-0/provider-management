package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, February 2026
 * Time: 05:23
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageDto {

    /**
     * Represents the unique identifier for an element.
     * This variable is used to store a string that uniquely identifies
     * a specific element within the context of the system.
     */
    private String elementId;

    /**
     * Represents a specific language value.
     * This variable is used to store the value corresponding to a language,
     * which may be a code, name, or any relevant identification associated
     * with a language in the system's context.
     */
    private String value;

    /**
     * Generates a string representation of the LanguageDto object.
     * The string includes the values of the elementId and value fields
     * in a formatted structure.
     *
     * @return a string describing the state of the LanguageDto object.
     */
    @Override
    public String toString() {
        return "LanguageDto{" +
                "elementId='" + elementId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
