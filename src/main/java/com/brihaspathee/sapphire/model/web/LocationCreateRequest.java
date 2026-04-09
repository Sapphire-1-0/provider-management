package com.brihaspathee.sapphire.model.web;

import com.brihaspathee.sapphire.model.ContactDto;
import com.brihaspathee.sapphire.model.LocationDto;
import com.brihaspathee.sapphire.model.OrganizationDto;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 24, February 2026
 * Time: 05:33
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model.web
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationCreateRequest {

    /**
     * Represents the location details used for creating a new location.
     * This variable holds an instance of {@link LocationDto}, which contains
     * comprehensive information about the location, such as its name,
     * address, city, state, ZIP code, and additional details like specialties
     * and network associations. It is a core component of the location creation request.
     */
    private LocationDto location;

    /**
     * Represents the identifier of the organizational element associated with the location creation request.
     * This identifier is typically used to uniquely identify and link the organization to the location.
     */
    private String orgElementId;

    /**
     * Represents the contact details associated with the request.
     * This variable holds an instance of {@link ContactDto}, which contains
     * information such as the address, telecommunications details, usage category,
     * and person-related data. It is used to encapsulate and manage the contact
     * information relevant to the location creation process.
     */
    private ContactDto contact;
}
