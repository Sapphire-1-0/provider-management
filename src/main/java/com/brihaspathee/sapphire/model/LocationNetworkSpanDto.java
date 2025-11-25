package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, November 2025
 * Time: 17:07
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
public class LocationNetworkSpanDto {

    /**
     * Represents the start date of a location network span.
     * This variable is used to specify the beginning date
     * for the duration of a network span within the system.
     */
    private LocalDate startDate;

    /**
     * Represents the end date of a location network span.
     * This variable is used to specify the concluding date
     * for the duration of a network span within the system.
     */
    private LocalDate endDate;

    /**
     * Represents the reason for the termination of a location network span.
     * This variable is used to specify the justification or cause for
     * the termination of a network span within the system. It provides
     * clarity and additional context for the termination event.
     */
    private String termReason;
}
