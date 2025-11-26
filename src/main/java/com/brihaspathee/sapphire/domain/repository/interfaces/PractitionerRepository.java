package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:10 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface PractitionerRepository {

    /**
     * Retrieves a list of practitioners based on the specified search criteria.
     *
     * @param practitionerSearchRequest the search request containing the criteria
     *                                   for filtering practitioners, such as identifiers or other attributes
     * @return a list of Practitioner objects that match the provided search criteria
     */
    List<Practitioner> findPractitioners(PractitionerSearchRequest practitionerSearchRequest);

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return the Practitioner object corresponding to the specified ID, or null if no practitioner is found
     */
    Practitioner findPractitionerById(String practitionerId);
}
