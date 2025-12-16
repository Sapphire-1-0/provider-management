package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 5:21 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/sapphire/practitioner")
public interface PractitionerAPI {

    /**
     * Retrieves the details of a practitioner based on the provided practitioner code.
     *
     * @param practitionerCode the unique code associated with the practitioner to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a
     *         PractitionerDto, representing the details of the requested practitioner
     */
    @GetMapping("/code/{pracCode}")
    ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerByCode(@PathVariable(name = "pracCode") String practitionerCode);

    /**
     * Retrieves a practitioner based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     *         a PractitionerDto, which represents the details of the requested practitioner
     */
    @GetMapping("/{pracId}")
    ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerById(@PathVariable(name = "pracId") String practitionerId);

    /**
     * Retrieves a list of practitioners based on the search criteria provided in the request.
     *
     * @param practitionerSearchRequest the request object containing search criteria such as
     *                                  identifiers or other attributes related to practitioners.
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a
     *         PractitionerList, which represents the collection of practitioners
     *         retrieved as part of the search operation.
     */
    @PostMapping("/_search")
    ResponseEntity<SapphireAPIResponse<PractitionerList>> getPractitioners(
            @RequestBody PractitionerSearchRequest practitionerSearchRequest);

}
