package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:07 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface PractitionerService {

    /**
     * Retrieves the details of a practitioner based on the provided practitioner code.
     *
     * @param practitionerCode the unique code of the practitioner whose details are to be retrieved
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner
     *         that corresponds to the specified code, or {@code null} if no such practitioner exists
     */
    PractitionerDto getPractitionerByCode(String practitionerCode);

    /**
     * Retrieves the details of a practitioner based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner whose details are to be retrieved
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner
     */
    PractitionerDto getPractitionerById(String practitionerId);

    /**
     * Retrieves information about a practitioner and network based on their respective element IDs.
     *
     * @param pracId the unique identifier for the practitioner
     * @param netId the unique identifier for the network
     * @return an instance of {@code PractitionerDto} containing the details of the associated
     *         practitioner and network, or {@code null} if not found
     */
    PractitionerDto getPracAndNetByElementId(String pracId, String netId);

    /**
     * Retrieves a practitioner's details and location information based on the provided practitioner ID and location ID.
     *
     * @param pracId the unique identifier for the practitioner
     * @param locId the unique identifier for the location
     * @return an instance of {@code PractitionerDto} containing the practitioner's details and associated location information,
     *         or {@code null} if no matching record is found
     */
    PractitionerDto getPracAndLocByElementId(String pracId, String locId);

    /**
     * Retrieves information about a practitioner, network, and location based on their respective element IDs.
     *
     * @param pracId the unique identifier for the practitioner
     * @param netId the unique identifier for the network
     * @param locId the unique identifier for the location
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner,
     *         network, and location, or {@code null} if no matching record is found
     */
    PractitionerDto getPracAndNetAndLocByElementId(String pracId, String netId, String locId);

    /**
     * Retrieves a list of practitioners based on the specified search criteria.
     *
     * @param practitionerSearchRequest an instance of {@code PractitionerSearchRequest} containing
     *                                  the search criteria for retrieving practitioners. The criteria
     *                                  may include identifiers or other relevant search parameters.
     * @return an instance of {@code PractitionerList} containing the collection of practitioners
     *         that match the provided search criteria.
     */
    PractitionerList getPractitioners(PractitionerSearchRequest practitionerSearchRequest);

}
