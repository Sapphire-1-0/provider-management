package com.brihaspathee.sapphire.model.web;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:40 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model.web
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerSearchRequest {

    /**
     * Represents the first name of the practitioner to be included in the search request.
     * This field is used as a search parameter to filter practitioners based on their first name.
     */
    private String firstName;

    /**
     * Represents the last name of the practitioner to be included in the search request.
     * The last name is used as a search parameter to filter the practitioners.
     */
    private String lastName;

    /**
     * Represents the current page number in a paginated search request.
     * This variable is used to determine which set of results should be fetched.
     * The default value is set to 0, indicating the first page of results.
     */
    private int pageNumber = 0;

    /**
     * Represents the number of results to be returned per page in a paginated search request.
     * This variable determines the maximum number of entries displayed on one page of the search results.
     * The default value is set to 10.
     */
    private int pageSize = 10;

    /**
     * Represents a list of identifiers associated with the practitioner being searched for.
     * Each identifier in the list provides additional details such as type and value.
     */
    private List<IdentifierInfo> identifiers;

    /**
     * Returns the string representation of the PractitionerSearchRequest object.
     * This includes the identifiers field.
     *
     * @return a string representation of the PractitionerSearchRequest object.
     */
    @Override
    public String toString() {
        return "OrganizationSearchRequest{" +
                "identifiers=" + identifiers +
                '}';
    }
}
