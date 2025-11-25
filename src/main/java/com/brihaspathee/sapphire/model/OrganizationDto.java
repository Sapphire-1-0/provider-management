package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 5/9/25
 * Time: 1:40 PM
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
@Schema(description = "Represents an organization in the system")
public class OrganizationDto {

    @Schema(description = "Unique identifier for an element within the system",
            example = "123456789")
    private String elementId;
    /**
     * Represents the name of the organization.
     * This variable is used to store the name of an organization
     * associated with this DTO.
     */
    @Schema(description = "Name of the organization",
            example = "St. Joseph Hospital")
    private String name;

    /**
     * Represents a description of the organization.
     * This variable is used to provide additional details or context
     * about the organization within the system.
     */
    private String description;

    /**
     * Represents an alternate or additional name for the organization.
     * This variable is used to store a secondary or informal name
     * that the organization may be known by or associated with.
     */
    private String aliasName;

    /**
     * Specifies the type of the organization.
     * This variable represents a classification or category
     * that defines the specific role, function, or nature of the organization.
     */
    private String type;

    /**
     * Indicates whether the organization is classified as atypical.
     * It may be used to flag organizations that do not meet standard classifications
     * or operate outside of traditional structures. This variable provides a way to distinguish
     * such organizations in specific contexts.
     */
    private Boolean atypical;

    /**
     * Identifies whether the organization is operating on a capitated payment model.
     * It indicates if the organization receives a fixed amount per patient for services,
     * regardless of the actual amount or type of service provided.
     */
    private Boolean capitated;

    /**
     * Indicates whether a Primary Care Provider (PCP) practitioner
     * is required for this organization. This variable represents an
     * attribute that determines the necessity of associating a PCP practitioner
     * with the organization in certain contexts.
     */
    private Boolean pcpPractitionerRequired;

    /**
     * Represents a list of identifiers associated with the organization.
     * Each identifier is represented by an instance of IdentifierDto,
     * which contains attributes such as value, type, and date range.
     * These identifiers are used to uniquely recognize or describe
     * the organization in different contexts or systems.
     */
    private List<IdentifierDto> identifiers;

    /**
     * Represents a list of network information associated with an organization.
     * Each network is represented by an instance of NetworkDto, which contains attributes
     * such as the network's name, code, and specific classifications or properties.
     * This variable is used to store and manage a collection of networks related
     * to the organization in various contexts.
     */
    private List<NetworkDto> networks;

    /**
     * Represents a list of locations associated with an organization.
     * Each location is represented by an instance of LocationDto, which contains attributes
     * such as name, address details, and geographic identifiers.
     * This variable is used to store and manage a collection of locations
     * that belong to or are related to the organization in various contexts.
     */
    private List<LocationDto> locations;

    @Override
    public String toString() {
        return "OrganizationDto{" +
                "elementId='" + elementId + '\'' +
                ", name='" + name + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", type='" + type + '\'' +
                ", atypical=" + atypical +
                ", capitated=" + capitated +
                ", pcpPractitionerRequired=" + pcpPractitionerRequired +
                '}';
    }
}
