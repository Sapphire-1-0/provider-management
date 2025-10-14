package com.brihaspathee.sapphire.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
public class OrganizationDto {

    private String elementId;
    /**
     * Represents the name of the organization.
     * This variable is used to store the name of an organization
     * associated with this DTO.
     */
    private String name;

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
    private String atypical;

    /**
     * Identifies whether the organization is operating on a capitated payment model.
     * It indicates if the organization receives a fixed amount per patient for services,
     * regardless of the actual amount or type of service provided.
     */
    private String capitated;

    /**
     * Indicates whether a Primary Care Provider (PCP) practitioner
     * is required for this organization. This variable represents an
     * attribute that determines the necessity of associating a PCP practitioner
     * with the organization in certain contexts.
     */
    private String pcpPractitionerRequired;

}
