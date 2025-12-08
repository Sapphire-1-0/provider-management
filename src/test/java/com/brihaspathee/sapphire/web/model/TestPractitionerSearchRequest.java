package com.brihaspathee.sapphire.web.model;

import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, December 2025
 * Time: 04:45
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPractitionerSearchRequest {

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
     * Represents a request data object used for searching practitioners.
     * It is an instance of the PractitionerSearchRequest class.
     * It contains details such as the practitioner's first name, last name,
     * page number, page size, and a list of identifier information used to
     * filter or locate specific practitioners during a search operation.
     * It facilitates the retrieval of practitioner details based on
     * the provided search criteria.
     */
    private PractitionerSearchRequest pracSearchRequest;

    /**
     * Represents a unique code used to identify a specific organization within the context
     * of a practitioner search request. This variable is utilized to reference or locate
     * the organization associated with the practitioner being searched.
     */
    private String orgCode;

    /**
     * Represents a unique code associated with a practitioner.
     * This field is used to identify or reference a specific practitioner
     * within the context of a search request or other practitioner-related operations.
     */
    private String pracCode;

    /**
     * Represents a unique code associated with a specific location within the context
     * of a practitioner search request. This field is used to identify or reference
     * the location being targeted in the search operation.
     */
    private String locCode;

    /**
     * Represents the code associated with a specific network in the context of a practitioner search request.
     * This variable is utilized to uniquely identify or reference a network, enabling network-specific
     * filtering or processing within test cases.
     */
    private String netCode;

    /**
     * Represents a list of practitioners associated with a test practitioner search request.
     * This variable is an instance of the PractitionerList class, which contains a collection
     * of PractitionerDto objects. Each PractitionerDto object holds the details of an individual
     * practitioner, including relevant attributes and identifiers.
     * <p>
     * The practitionerList variable is used to store the expected practitioner data
     * during testing, facilitating the comparison of actual and expected results
     * for validation purposes.
     */
    private PractitionerList practitionerList;

    /**
     * Generates a string representation of the TestPractitionerSearchRequest object.
     * The representation includes all attributes of the object such as test case details,
     * exception information, HTTP status code, search request details,
     * and practitioner list details.
     *
     * @return a string representation of the TestPractitionerSearchRequest object containing its field values.
     */
    @Override
    public String toString() {
        return "TestPractitionerSearchRequest{" +
                "testCaseId='" + testCaseId + '\'' +
                ", testCaseDescription='" + testCaseDescription + '\'' +
                ", exceptionExpected=" + exceptionExpected +
                ", exceptionCode='" + exceptionCode + '\'' +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", httpStatusCode='" + httpStatusCode + '\'' +
                ", pracSearchRequest=" + pracSearchRequest +
                ", orgCode='" + orgCode + '\'' +
                ", pracCode='" + pracCode + '\'' +
                ", locCode='" + locCode + '\'' +
                ", netCode='" + netCode + '\'' +
                ", practitionerList=" + practitionerList +
                '}';
    }
}
