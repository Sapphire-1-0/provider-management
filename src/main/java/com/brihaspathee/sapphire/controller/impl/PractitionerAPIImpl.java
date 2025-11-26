package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.PractitionerAPI;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.impl.PractitionerService;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:06 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PractitionerAPIImpl implements PractitionerAPI {

    /**
     * Reference to the {@link PractitionerService} that provides the core logic for retrieving,
     * processing, and managing practitioner-related data within the application.
     * This service is utilized to implement the functionality defined in the {@link PractitionerAPIImpl}.
     */
    private final PractitionerService practitionerService;

    /**
     * Retrieves a list of practitioners based on the provided search criteria.
     *
     * @param practitionerSearchRequest the search request containing criteria such as identifiers
     *                                   to filter and retrieve practitioners
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     *         a PractitionerList, which includes the list of practitioners matching
     *         the search criteria
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerList>> getPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        PractitionerList practitionerList = practitionerService.getPractitioners(practitionerSearchRequest);
        SapphireAPIResponse<PractitionerList> apiResponse =
                SapphireAPIResponse.<PractitionerList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(practitionerList)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a practitioner based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     * a PractitionerDto, which represents the details of the requested practitioner
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerById(String practitionerId) {
        PractitionerDto practitionerDto = practitionerService.getPractitionerById(practitionerId);
        return null;
    }
}
