package com.brihaspathee.sapphire.domain.entity;

import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, November 2025
 * Time: 04:51
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationNetworkServiceInfo {

    /**
     * Indicates whether the associated entity or service acts as the Primary
     * Care Provider (PCP). A Primary Care Provider is typically the main point
     * of contact for patients seeking medical care and is responsible for
     * coordinating various aspects of the patient's healthcare. This property
     * helps identify such providers within the system for easier classification
     * and management.
     */
    private Boolean isPCP;

    /**
     * Indicates whether the service or entity is classified as a specialist.
     * This property helps in determining if the associated service provider
     * or entity offers specialized services, typically requiring advanced
     * expertise or training in a specific medical or professional field.
     * It is used to categorize and manage service offerings within the system.
     */
    private Boolean isSpecialist;

    /**
     * Indicates whether the associated service or entity is classified
     * under behavioral health. Behavioral health typically encompasses
     * services and support related to mental health, emotional well-being,
     * and substance abuse. This property helps in identifying and categorizing
     * services that pertain specifically to behavioral health within the system.
     */
    private Boolean isBehavioralHealth;

    /**
     * Represents the panel-related criteria and limitations of a specific entity.
     * The `HasPanel` variable encapsulates various attributes such as age range,
     * gender, and status that define panel constraints and eligibility.
     * It provides the necessary details to determine the applicability
     * of specific panel requirements or restrictions within a certain context.
     */
    private HasPanel hasPanel;

    /**
     * Represents a collection of roles and their corresponding details that
     * the location is expected to serve. Each item in the list specifies
     * the role, the period during which the role is active (start and end dates),
     * and the reason for termination if applicable.
     */
    private List<RoleLocationServes> roleLocationServes;
}
