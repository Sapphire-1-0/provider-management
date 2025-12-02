package com.brihaspathee.sapphire.web.model;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, November 2025
 * Time: 11:48
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestOrganizationSearchRequest {

    /**
     * Represents the unique identifier for a specific test case.
     * This identifier is used to distinguish and reference individual
     * test cases within the context of a test suite or testing process.
     */
    private String testCaseId;

    /**
     * Represents the description of a test case in a test organization search request.
     * This field provides additional details or context for the specific test case,
     * making it easier to understand its purpose or functionality during testing.
     */
    private String testCaseDescription;

    /**
     * Identifies if an exception is expected
     */
    private boolean exceptionExpected;

    /**
     * The exception code when an exception is expected
     */
    private String exceptionCode;

    /**
     * The exception message when an exception is expected
     */
    private String exceptionMessage;

    /**
     * The http status code expected
     */
    private String httpStatusCode;

    /**
     * Represents a request data object used for searching organizations.
     * This variable is an instance of the OrganizationSearchRequest class,
     * which contains details such as a list of identifier information used
     * to filter or locate specific organizations during a search operation.
     * It facilitates the retrieval of organization details based on
     * the provided search criteria.
     */
    private OrganizationSearchRequest orgSearchRequest;

    /**
     * Represents a unique code that identifies a specific organization.
     * This field is used to specify the organization being referenced
     * or searched for in a request context.
     */
    private String organizationCode;

    /**
     * Represents the code associated with a specific network.
     * This variable is used to uniquely identify or reference a network
     * within the context of an organization search request.
     */
    private String networkCode;

    /**
     * Represents the name of the location associated with a test case.
     * This variable is used to store the specific location name
     * that may be relevant to the test scenario or validation process.
     */
    private String locationName;

    /**
     * Represents the expected organization details in the form of an OrganizationDto object.
     * This variable is used to store the expected output for a test case,
     * where the organization details such as name, type, identifiers, networks, and locations
     * are validated against the actual values returned from a service or process.
     * It serves as a reference point to confirm the correctness and accuracy
     * of organization-related information during testing.
     */
    private OrganizationList expectedOrganizationList;

    /**
     * Generates a string representation of the TestOrganizationSearchRequest object.
     * This representation includes all key attributes associated with the object,
     * such as exception details, HTTP status code, search request details,
     * and expected organization information.
     *
     * @return a string representation of the object containing its field values.
     */
    @Override
    public String toString() {
        return "TestOrganizationSearchRequest{" +
                "exceptionExpected=" + exceptionExpected +
                ", exceptionCode='" + exceptionCode + '\'' +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", httpStatusCode='" + httpStatusCode + '\'' +
                ", orgSearchRequest=" + orgSearchRequest +
                ", expectedOrganizationList=" + expectedOrganizationList +
                '}';
    }
}
