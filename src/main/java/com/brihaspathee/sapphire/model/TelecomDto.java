package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, January 2026
 * Time: 10:33
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelecomDto {

    /**
     * Represents the primary phone number associated with the TelecomDto entity.
     * This variable stores a contact number intended for general communication
     * purposes within the context of this entity.
     */
    private String phone;

    /**
     * Represents the TTY (Text Telephone or Teletypewriter) contact information.
     * This variable is used to store the communication number for individuals
     * or devices that require TTY-enabled communication due to hearing or
     * speech impairments.
     */
    private String tty;

    /**
     * Represents the fax number associated with the TelecomDto entity.
     * This variable is used to store a contact number designated for sending
     * or receiving facsimile transmissions in contexts requiring this mode
     * of communication.
     */
    private String fax;

    /**
     * Represents the phone number available for after-hours communication.
     * This variable is used to store a contact number that can be reached outside
     * regular working hours for support, inquiries, or other communication needs.
     */
    private String afterHoursNumber;

    /**
     * Represents the email address associated with the TelecomDto entity.
     * This variable stores the primary email address used for communication
     * or correspondence purposes.
     */
    private String email;

    /**
     * Represents a secure email address.
     * This variable is used to store an email address intended to be used
     * in secure or confidential communication contexts.
     */
    private String secureEmail;

    /**
     * Represents the website address associated with the TelecomDto entity.
     * This variable is used to store the URL or web address relevant to the entity
     * or associated system context for communication or informational purposes.
     */
    private String website;

    /**
     * Generates a string representation of the TelecomDto object.
     * The string includes the values of the phone, tty, fax, afterHoursNumber, email,
     * secureEmail, and website fields in a formatted structure.
     *
     * @return a string describing the state of the TelecomDto object.
     */
    @Override
    public String toString() {
        return "TelecomDto{" +
                "phone='" + phone + '\'' +
                ", tty='" + tty + '\'' +
                ", fax='" + fax + '\'' +
                ", afterHoursNumber='" + afterHoursNumber + '\'' +
                ", email='" + email + '\'' +
                ", secureEmail='" + secureEmail + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
