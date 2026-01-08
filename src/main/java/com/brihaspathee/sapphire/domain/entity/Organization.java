package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, August 2025
 * Time: 05:17
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    /**
     * Unique identifier for the element in the context of the organization.
     * This property can be used to distinctly identify an instance of
     * the class or a related entity within the system, ensuring traceability
     * and linkage across different records or components.
     */
    private String elementId;

    /**
     * Represents a code that is associated with the organization.
     *
     * This code serves as a unique identifier or categorization mechanism for the
     * organization and may be used in various system processes where an
     * organization-specific code is required.
     */
    private String code;

    /**
     * Represents the official name of the organization.
     * This property serves as the primary identifier for the organization in terms of its name.
     */
    private String name;

    /**
     * Represents an alternate name or nickname for the organization.
     * This property can be used for referring to the organization by a name
     * other than its official name.
     */
    private String aliasName;

    /**
     * Represents the type of the organization.
     * This property can be used to categorize the organization
     * based on its function, role, or other distinguishing characteristics.
     */
    private String type;

    /**
     * Indicates whether the organization is classified as atypical.
     * An atypical organization does not provide direct healthcare
     * services and typically includes businesses like billing
     * agencies, transportation services, and other non-healthcare
     * providers that participate in the healthcare ecosystem.
     */
    private Boolean atypical;

    /**
     * Indicates whether the organization is capitated. A capitated organization
     * receives a set amount of money per patient per period of time to provide
     * healthcare services, irrespective of the actual services rendered.
     */
    private Boolean capitated;

    /**
     * Indicates whether the organization requires a Primary Care Physician (PCP)
     * to be assigned or involved for associated practitioners or services.
     */
    private Boolean pcpPractitionerRequired;

    /**
     * Represents a list of identifiers associated with the organization.
     * These identifiers provide specific details such as value, start
     * date, end date, and additional subclass-specific properties.
     */
    private List<Identifier> identifiers;

    /**
     * Represents a collection of qualifications associated with the organization.
     * Each qualification provides detailed information such as type, value, issuer,
     * validity period, associated taxonomy, levels, and geographic scope. This property
     * is used to manage and track the qualifications relevant to the organization within the system.
     */
    private List<Qualification> qualifications;

    /**
     * Represents the list of networks associated with the organization.
     * Each network provides specific details such as its code, name, and
     * whether it is an HNET network or a vendor network.
     */
    private List<Network> networks;

    /**
     * Represents the list of locations associated with the organization.
     * Each location captures details such as name, address, city, state, ZIP code,
     * county, and FIPS code, providing comprehensive geographic information
     * related to the organization.
     */
    private List<Location> locations;

    /**
     * Represents the list of practitioners associated with an organization.
     * Each practitioner in the list provides detailed information such
     * as personal information (e.g., first name, last name, gender),
     * identifiers (e.g., NPI, TIN), qualifications, networks, and locations.
     * This property allows the organization to manage and track practitioners
     * that are affiliated or associated with it.
     */
    private List<Practitioner> practitioners;

    /**
     * Provides a string representation of the Organization object, including key attributes
     * such as elementId, name, aliasName, type, atypical, capitated, and pcpPractitionerRequired.
     *
     * @return a string representation of the Organization object, describing its attributes and their values.
     */
    @Override
    public String toString() {
        return "Organization{" +
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
