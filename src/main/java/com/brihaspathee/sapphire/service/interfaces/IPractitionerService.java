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
public interface IPractitionerService {

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

    /**
     * Retrieves the details of a practitioner based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner whose details are to be retrieved
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner
     */
    PractitionerDto getPractitionerById(String practitionerId);
}
