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
     * Retrieves a Practitioner entity based on the provided code.
     *
     * @param code the unique code used to identify the practitioner
     * @return the Practitioner object corresponding to the specified code,
     *         or null if no practitioner is found
     */
    Practitioner findPractitionerByCode(String code);

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return the Practitioner object corresponding to the specified ID, or null if no practitioner is found
     */
    Practitioner findPractitionerById(String practitionerId);

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID and network ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @param netId the unique identifier of the network associated with the practitioner
     * @return the Practitioner object corresponding to the specified practitioner ID and network ID,
     *         or null if no matching practitioner is found
     */
    Practitioner findPracAndNetByElementId(String practitionerId, String netId);

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID and location ID.
     *
     * @param pracId the unique identifier of the practitioner
     * @param locId the unique identifier of the location associated with the practitioner
     * @return the Practitioner object matching the specified organization ID and location ID,
     *         or null if no matching practitioner is found
     */
    Practitioner findPracAndLocByElementId(String pracId, String locId);

    /**
     * Retrieves a Practitioner entity based on the provided practitioner ID, network ID, and location ID.
     *
     * @param pracId the unique identifier of the practitioner
     * @param netId the unique identifier of the network associated with the practitioner
     * @param locId the unique identifier of the location associated with the practitioner
     * @return the Practitioner object matching the specified organization ID, network ID, and location ID,
     *         or null if no matching practitioner is found
     */
    Practitioner findPracAndNetAndLocByElementId(String pracId, String netId, String locId);

    /**
     * Retrieves a list of practitioners based on the specified search criteria.
     *
     * @param practitionerSearchRequest the search request containing the criteria
     *                                   for filtering practitioners, such as identifiers or other attributes
     * @return a list of Practitioner objects that match the provided search criteria
     */
    List<Practitioner> findPractitioners(PractitionerSearchRequest practitionerSearchRequest);
}
