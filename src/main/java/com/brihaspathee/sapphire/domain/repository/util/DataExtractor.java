package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.model.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, February 2026
 * Time: 05:42
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
public class DataExtractor {

    public static Map<String, List<Map<String,Object>>> getIdentifiers(List<IdentifierDto> identifiers){
        Map<String, List<Map<String,Object>>> params = new HashMap<>();
        if(identifiers != null && !identifiers.isEmpty()){
            for (IdentifierDto identifier: identifiers){
                switch(identifier.getType()){
                    case "NPI":
                        Map<String, Object> npiMap = new HashMap<>();
                        npiMap.put("value", identifier.getValue());
                        npiMap.put("startDate", identifier.getStartDate());
                        npiMap.put("endDate",
                                Optional.ofNullable(identifier.getEndDate())
                                        .orElse(LocalDate.of(4000, 1, 1))
                        );
                        List<Map<String, Object>> npiList =
                                (List<Map<String, Object>>) params.computeIfAbsent(
                                        "npiList", k -> new ArrayList<>()
                                );

                        npiList.add(npiMap);
                        break;
                    case "MEDICARE_ID":
                        // Medicare identifiers
                        Map<String, Object> medicareIdMap = new HashMap<>();
                        medicareIdMap.put("value", identifier.getValue());
                        List<Map<String, Object>> medicareList =
                                (List<Map<String, Object>>) params.computeIfAbsent(
                                        "medicareList", k -> new ArrayList<>()
                                );

                        medicareList.add(medicareIdMap);
                        break;
                    case "MEDICAID_ID":
                        // Medicaid identifiers (state-specific)
                        Map<String, Object> medicaidIdMap = new HashMap<>();
                        medicaidIdMap.put("value", identifier.getValue());
                        medicaidIdMap.put("state", identifier.getAdditionalProperties().get("state"));
                        medicaidIdMap.put("startDate", identifier.getStartDate());
                        medicaidIdMap.put("endDate",
                                Optional.ofNullable(identifier.getEndDate())
                                        .orElse(LocalDate.of(4000, 1, 1))
                        );
                        List<Map<String, Object>> medicaidList =
                                (List<Map<String, Object>>) params.computeIfAbsent(
                                        "medicaidList", k -> new ArrayList<>()
                                );

                        medicaidList.add(medicaidIdMap);
                        break;

                }
            }

        }
        return params;
    }

    /**
     * Converts a list of ContactDto objects into a list of maps containing structured contact data.
     * The method processes each contact object to extract details about the usage (use),
     * address, telecommunications, and personal information and organizes them into
     * separate maps which are then added to the result list.
     *
     * @param contactList the list of ContactDto objects representing contacts. Each object
     *                    contains details about the contact's usage, address, telecommunications,
     *                    and personal information.
     * @return a list of maps where each map represents a contact with its structured details.
     *         The map includes keys such as "use", "address", "telecom", and "person",
     *         each containing data extracted from the provided ContactDto objects.
     */
    public static List<Map<String, Object>> getContacts(List<ContactDto> contactList) {
        List<Map<String, Object>> contacts = new ArrayList<>();
        for (ContactDto contact : contactList) {
            Map<String, Object> contactMap = new HashMap<>();
            contactMap.put("use", contact.getUse());
            Map<String, Object> address = new HashMap<>();
            address.put("streetAddress", contact.getAddress().getStreetAddress());
            address.put("secondaryAddress", contact.getAddress().getSecondaryAddress());
            address.put("city", contact.getAddress().getCity());
            address.put("state", contact.getAddress().getState());
            address.put("zipCode", contact.getAddress().getZipCode());
            address.put("county", contact.getAddress().getCounty());
            address.put("countyFIPS", contact.getAddress().getCountyFIPS());
            contactMap.put("address", address);

            Map<String, Object> telecom = new HashMap<>();
            telecom.put("phone", contact.getTelecom().getPhone());
            telecom.put("tty", contact.getTelecom().getTty());
            telecom.put("afterHoursNumber", contact.getTelecom().getAfterHoursNumber());
            telecom.put("fax", contact.getTelecom().getFax());
            telecom.put("email", contact.getTelecom().getEmail());
            telecom.put("website", contact.getTelecom().getWebsite());
            contactMap.put("telecom", telecom);

            Map<String, Object> person = new HashMap<>();
            person.put("firstName", contact.getPerson().getFirstName());
            person.put("lastName", contact.getPerson().getLastName());
            person.put("middleName", contact.getPerson().getMiddleName());
            person.put("title", contact.getPerson().getTitle());
            contactMap.put("person", person);
            contacts.add(contactMap);
        }
        return contacts;
    }

    /**
     * Converts a list of CredentialingDto objects into a structured representation as a list of maps.
     * Each map contains details about a credential, including specialty, taxonomy code, and primary status.
     *
     * @param credentialList the list of CredentialingDto objects to be converted. Each object contains
     *                    information about a credential, such as associated specialties and their details.
     * @return a list of maps where each map represents a credential, with keys such as:
     *         "specialty" (the name of the specialty), "taxonomy" (the taxonomy code),
     *         and "isPrimary" (a boolean indicating if it is the primary specialty).
     */
    public static List<Map<String, Object>> getCredentials(List<CredentialingDto> credentialList) {
        List<Map<String, Object>> credentials = new ArrayList<>();
        for (CredentialingDto credential: credentialList){
            Map<String, Object> credentialMap = new HashMap<>();
            credentialMap.put("type", credential.getCredentialingType());
            credentialMap.put("geographyDescription", credential.getGeography());
            credentialMap.put("FIPS", credential.getFips());
            credentialMap.put("committeeDate", credential.getCommitteeDate());
            credentialMap.put("endDate", credential.getEndDate());
            credentials.add(credentialMap);
        }
        return credentials;
    }

    /**
     * Processes the given list of qualifications and generates a list of maps containing
     * structured information about each qualification, including type, issuer, start date,
     * end date, level, and value.
     *
     * @param qualificationList a list of QualificationDto objects representing the qualifications to be processed
     * @return a list of maps where each map represents a qualification with related details
     */
    public static List<Map<String, Object>> getQualifications(List<QualificationDto> qualificationList) {
        List<Map<String, Object>> qualifications = new ArrayList<>();
        for (QualificationDto qualification: qualificationList){
            Map<String, Object> qualificationMap = new HashMap<>();
            qualificationMap.put("type", qualification.getType());
            qualificationMap.put("issuer", qualification.getIssuer());
            qualificationMap.put("startDate", qualification.getStartDate());
            qualificationMap.put("endDate", qualification.getEndDate());
            qualificationMap.put("level", qualification.getLevel());
            qualificationMap.put("value", qualification.getValue());
            qualifications.add(qualificationMap);
        }
        return qualifications;
    }

    /**
     * Retrieves a list of languages along with their associated details.
     *
     * @param languageList a list of LanguageDto objects representing the languages to process
     * @return a list of maps, where each map contains key-value pairs representing the details of a language
     */
    public static List<Map<String, Object>> getLanguages(List<LanguageDto> languageList) {
        List<Map<String, Object>> languages = new ArrayList<>();
        for (LanguageDto language: languageList){
            Map<String, Object> languageMap = new HashMap<>();
            languageMap.put("value", language.getValue());
            languages.add(languageMap);
        }
        return languages;
    }

    /**
     * Retrieves a list of hospital privileges based on the provided privilege data.
     * Each privilege is represented as a map containing details such as organization element ID and privilege type.
     *
     * @param privilegeList a list of HospitalPrivilegeDto objects containing data needed to determine hospital privileges
     * @return a list of maps, where each map represents a hospital privilege with specific details (e.g., organization ID, type)
     */
    public static List<Map<String, Object>> getHospitalPrivileges(List<HospitalPrivilegeDto> privilegeList) {
        List<Map<String, Object>> privileges = new ArrayList<>();
        for (HospitalPrivilegeDto privilege: privilegeList){
            Map<String, Object> privilegeMap = new HashMap<>();
            privilegeMap.put("organizationElementId", privilege.getOrgElementId());
            privilegeMap.put("type", privilege.getHospitalPrivilegeType());
            privileges.add(privilegeMap);
        }
        return privileges;
    }

    /**
     * Converts a list of DisorderDto objects into a structured representation as a list of maps.
     * Each map contains details about a disorder, including its type and related attributes.
     *
     * @param disorderList the list of DisorderDto objects to be converted. Each object represents
     *                     information about a specific disorder or condition.
     * @return a list of maps where each map represents a disorder. Each map contains key-value pairs
     *         representing disorder attributes, such as "type" or other specific details.
     */
    public static List<Map<String, Object>> getDisorders(List<DisorderDto> disorderList) {
        List<Map<String, Object>> disorders = new ArrayList<>();
        for (DisorderDto disorder: disorderList){
            Map<String, Object> disorderMap = new HashMap<>();
            disorderMap.put("type", disorder.getDisorderType());
            disorders.add(disorderMap);
        }
        return disorders;
    }

    /**
     * Retrieves a list of healthcare services as maps containing service details.
     *
     * @param healthcareServiceList a list of HealthcareServiceDto objects representing the input data
     *                              for processing the healthcare services.
     * @return a list of maps where each map represents a healthcare service with key-value pairs
     *         describing its details.
     */
    public static List<Map<String, Object>> getHealthcareServices(List<HealthcareServiceDto> healthcareServiceList) {
        List<Map<String, Object>> healthcareServices = new ArrayList<>();
        for (HealthcareServiceDto healthcareService: healthcareServiceList){
            Map<String, Object> healthcareServiceMap = new HashMap<>();
            healthcareServiceMap.put("type", healthcareService.getHealthcareServiceType());
            healthcareServiceMap.put("servicePopulation", healthcareService.getServicePopulation());
            healthcareServiceMap.put("state", healthcareService.getState());
            healthcareServiceMap.put("startDate", healthcareService.getStartDate());
            healthcareServiceMap.put("endDate", healthcareService.getEndDate());
            healthcareServices.add(healthcareServiceMap);
        }
        return healthcareServices;
    }

    /**
     * Converts a list of SpecialtyDto objects into a structured representation as a list of maps.
     * Each map contains details about the specialty, taxonomy code, and whether it is the primary specialty.
     *
     * @param specialtyList the list of SpecialtyDto objects to be converted. Each object contains
     *                      information about a specialty, including its name, taxonomy code, and primary status.
     * @return a list of maps where each map represents a specialty. Keys in the map include:
     *         "specialty" (the name of the specialty), "taxonomy" (the taxonomy code),
     *         and "isPrimary" (a boolean indicating if it is the primary specialty).
     */
    public static List<Map<String, Object>> getSpecialties(List<SpecialtyDto> specialtyList) {
        List<Map<String, Object>> specialties = new ArrayList<>();
        for (SpecialtyDto specialty: specialtyList){
            Map<String, Object> specialtyMap = new HashMap<>();
            specialtyMap.put("specialty", specialty.getSpecialty());
            specialtyMap.put("taxonomy", specialty.getTaxonomyCode());
            specialtyMap.put("isPrimary", specialty.getIsPrimary());
            specialties.add(specialtyMap);
        }
        return specialties;
    }

    /**
     * Converts a list of NetworkDto objects into a structured representation as a list of maps.
     * Each map contains information about the network, its associated locations, and related data.
     *
     * @param networkList the list of NetworkDto objects to be converted. Each object contains data about
     *                    networks including their element ID, locations, and location networks.
     * @return a list of maps where each map represents a network and includes keys such as "elementId"
     *         and "locations". The "locations" key contains a list of maps, each representing a location
     *         with keys like "elementId" and "rls", where "rls" contains details about role location
     *         serves including start and end dates.
     */
    public static List<Map<String, Object>> getNetworks(List<NetworkDto> networkList) {
        List<Map<String, Object>> networks = new ArrayList<>();
        for (NetworkDto network: networkList){
            Map<String, Object> netMap = new HashMap<>();
            netMap.put("elementId", network.getElementId());
            if (network.getLocations() != null && !network.getLocations().isEmpty()){
                List<Map<String, Object>> locations = getLocations(network.getLocations());
                netMap.put("locations", locations);
            }
            networks.add(netMap);
        }
        return networks;
    }

    /**
     * This method transforms a list of LocationDto objects into a list of maps
     * containing location and network span details. Each map represents a location
     * with its associated properties, such as elementId and role location serves
     * (if any).
     *
     * @param locationList a list of LocationDto objects representing the locations
     *                    to be transformed
     * @return a list of maps where each map contains the transformed details of
     *         a location and its associated network spans
     */
    private static List<Map<String, Object>> getLocations(List<LocationDto> locationList) {
        List<Map<String, Object>> locations = new ArrayList<>();
        for (LocationDto location: locationList){
            Map<String, Object> locMap = new HashMap<>();
            locMap.put("elementId", location.getElementId());
            if(location.getSpecialties() != null && !location.getSpecialties().isEmpty()){
                List<Map<String, Object>> specialties = DataExtractor.getSpecialties(location.getSpecialties());
                locMap.put("specialties", specialties);
            }
            if (location.getLocationNetwork() != null){
                LocationNetworkDto locationNetworkDto = location.getLocationNetwork();
                if (locationNetworkDto.getSpans() != null && !locationNetworkDto.getSpans().isEmpty()){
                    List<Map<String, Object>> roleLocationServes = new ArrayList<>();
                    for (LocationNetworkSpanDto span: locationNetworkDto.getSpans()){
                        Map<String, Object> rlsMap = new HashMap<>();
                        rlsMap.put("rlsStartDate", span.getStartDate());
                        rlsMap.put("rlsEndDate", span.getEndDate());
                        roleLocationServes.add(rlsMap);
                    }
                    locMap.put("rls", roleLocationServes);
                }
            }
            locations.add(locMap);
        }
        return locations;
    }
}
