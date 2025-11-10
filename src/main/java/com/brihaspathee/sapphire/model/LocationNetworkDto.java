package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, November 2025
 * Time: 17:05
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationNetworkDto {

    /**
     * Represents the panel details associated with the location network.
     * This variable is used to store information about age-related
     * criteria, gender-specific limitations, and other relevant attributes
     * encapsulated in the {@link PanelDto} class. The panel can provide a way
     * to define eligibility or classification for the associated network.
     */
    private PanelDto panel;

    /**
     * Indicates whether the network location is categorized as a Primary Care Provider (PCP).
     * This variable is used to specify if the network or provider associated with
     * the location is designated as a PCP within the system.
     */
    private Boolean isPCP;

    /**
     * Indicates whether the network location is categorized as a specialist.
     * This variable is used to specify if the network or provider associated
     * with the location is considered a specialist within the system.
     */
    private Boolean isSpecialist;

    /**
     * Indicates whether the location network is categorized as behavioral health.
     * This variable is used to specify if the network is related to behavioral health
     * services or providers within the system.
     */
    private Boolean isBehavioralHealth;

    /**
     * Represents a list of location network spans associated with a location.
     * This variable is used to store a collection of {@link LocationNetworkSpanDto} objects,
     * where each object provides details about the specific duration, termination reasons,
     * and other attributes of a network span for a location within the system.
     */
    private List<LocationNetworkSpanDto> spans;
}
