package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, February 2026
 * Time: 05:25
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalPrivilegeDto {

    /**
     * Represents the organizational element identifier.
     * This variable is used to uniquely identify a specific organizational element
     * within the context where it is applied.
     */
    private String orgElementId;

    /**
     * Represents the type of hospital privileges associated with an individual or entity.
     * This variable is used to specify the classification or designation of privileges
     * granted by a hospital, such as admitting, attending, or consulting privileges.
     */
    private String hospitalPrivilegeType;

    /**
     * Generates a string representation of the HospitalPrivilegeDto object.
     * The string includes the values of the orgElementId and hospitalPrivilegeType
     * fields in a formatted structure.
     *
     * @return a string describing the state of the HospitalPrivilegeDto object.
     */
    @Override
    public String toString() {
        return "HospitalPrivilegeDto{" +
                "orgElementId='" + orgElementId + '\'' +
                ", hospitalPrivilegeType='" + hospitalPrivilegeType + '\'' +
                '}';
    }
}
