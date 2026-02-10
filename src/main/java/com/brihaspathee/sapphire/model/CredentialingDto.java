package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, February 2026
 * Time: 13:06
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
public class CredentialingDto {

    /**
     * Represents the unique identifier for an element.
     * This variable is used to uniquely identify an element
     * within the context of the system.
     */
    private String elementId;

    /**
     * Represents the type of credentialing associated with the entity.
     * This variable is used to specify the category or classification
     * related to the credentialing process within the context of the system.
     */
    private String credentialingType;

    /**
     * Represents the geographic location or region associated with the entity.
     * This variable is used to specify the relevant geography context
     * within the system, such as a country, state, or region.
     */
    private String geography;

    /**
     * Represents the Federal Information Processing Standards (FIPS) code.
     * This variable is used to uniquely identify a geographic region or area
     * within the United States as per the standardized FIPS system.
     */
    private String fips;

    /**
     * Represents the date on which the committee meeting or decision occurred.
     * This variable is used to specify the scheduled or actual date
     * associated with a committee's activities or decisions within the system.
     */
    private LocalDate committeeDate;

    /**
     * Represents the end date associated with an event or process.
     * This variable is used to indicate the conclusion of a specific
     * time frame or duration within the context of the system.
     */
    private LocalDate endDate;
}
