package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.time.LocalDate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, February 2026
 * Time: 13:01
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credentialing {

    /**
     * Unique identifier for an element within the system.
     * This property is used to uniquely identify an instance of the class
     * or a related entity in the context of the application, ensuring
     * traceability and consistency across data operations.
     */
    private String elementId;

    /**
     * Represents the type of credentialing associated with the entity.
     * This property is used to define or categorize the kind of credentialing
     * process or classification relevant to the context of the application.
     * It provides additional information or context about the nature of the
     * credentialing being applied or referenced.
     */
    private String credentialingType;

    /**
     * Represents the geography associated with credentialing.
     * This property provides information about the geographic region or location
     * relevant to the context of the credentialing process. It may be used to indicate
     * where a specific credentialing activity takes place or to categorize credentialing entities
     * based on geographic boundaries.
     */
    private String geography;

    /**
     * Represents the FIPS (Federal Information Processing Standards) code associated
     * with a specific geographic region. This property is used to uniquely identify
     * a location based on its FIPS code, which is a standardized set of numeric or
     * alphanumeric codes used to ensure uniform identification of geographic entities.
     */
    private String fips;

    /**
     * Represents the date when a committee action or decision has occurred or is scheduled
     * in the context of the credentialing process. This property indicates the specific
     * date on which the committee interacts with or evaluates the associated entity.
     */
    private LocalDate committeeDate;

    /**
     * Represents the end date associated with a specific credentialing process or entity.
     * This property is used to indicate the completion or termination date of a particular
     * activity, period, or validity related to the credentialing. The value stored in this
     * property should be in the format of a {@link LocalDate}.
     */
    private LocalDate endDate;
}
