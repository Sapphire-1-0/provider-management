package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.mapper.interfaces.PractitionerMapper;
import com.brihaspathee.sapphire.mapper.interfaces.QualificationMapper;
import com.brihaspathee.sapphire.model.IdentifierDto;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.QualificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/2/25
 * Time: 6:25 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PractitionerMapperImpl implements PractitionerMapper {

    /**
     * A mapper used for converting Qualification entities to their respective
     * Data Transfer Objects (DTOs) and vice versa. This component aids in
     * transforming qualifications data between different layers of the application.
     */
    private final QualificationMapper qualificationMapper;

    /**
     * Converts a Practitioner entity to a PractitionerDto object.
     *
     * @param practitioner the Practitioner entity to be converted
     * @return the populated PractitionerDto object
     */
    @Override
    public PractitionerDto toPractitionerDto(Practitioner practitioner) {
        if (practitioner == null) {
            return null;
        }
        List<IdentifierDto> identifierDtos = null;
        if (practitioner.getIdentifiers() != null && !practitioner.getIdentifiers().isEmpty()){
            identifierDtos = practitioner.getIdentifiers().stream().map(identifier -> {
                return IdentifierDto.builder()
                        .elementId(identifier.getElementId())
                        .value(identifier.getValue())
                        .type(identifier.getClass().getSimpleName())
                        .startDate(identifier.getStartDate())
                        .endDate(identifier.getEndDate())
                        .additionalProperties(getAdditionalProperties(identifier))
                        .build();
            }).toList();
        }
        List<QualificationDto> qualificationDtos;
        if(practitioner.getQualifications() != null && !practitioner.getQualifications().isEmpty()){
            qualificationDtos = new ArrayList<>();
            practitioner.getQualifications().forEach(qualification -> {
                qualificationDtos.add(qualificationMapper.toQualificationDto(qualification));
            });
        } else {
            qualificationDtos = null;
        }
        PractitionerDto practitionerDto = PractitionerDto.builder().build();
        practitionerDto.setElementId(practitioner.getElementId());
        practitionerDto.setCode(practitioner.getCode());
        practitionerDto.setFirstName(practitioner.getFirstName());
        practitionerDto.setLastName(practitioner.getLastName());
        practitionerDto.setMiddleName(practitioner.getMiddleName());
        practitionerDto.setBirthDate(practitioner.getBirthDate());
        practitionerDto.setGender(practitioner.getGender());
        practitionerDto.setAltFirstName(practitioner.getAltFirstName());
        practitionerDto.setAltMiddleName(practitioner.getAltMiddleName());
        practitionerDto.setAltLastName(practitioner.getAltLastName());
        practitionerDto.setIdentifiers(identifierDtos);
        practitionerDto.setQualifications(qualificationDtos);
        return practitionerDto;
    }

    /**
     * Converts a list of Practitioner entities to a list of PractitionerDto objects.
     *
     * @param practitioners the list of Practitioner entities to be converted
     * @return a list of PractitionerDto objects if the input list is non-null and non-empty,
     *         otherwise null
     */
    @Override
    public List<PractitionerDto> toPractitionerDtoList(List<Practitioner> practitioners) {
        if(practitioners != null && !practitioners.isEmpty()) {
            return practitioners.stream()
                    .map(this::toPractitionerDto)
                    .toList();
        }
        else{
            return null;
        }
    }

    /**
     * Retrieves additional properties associated with the given identifier.
     * The method determines the type of the identifier and based on its specific
     * subtype (e.g., MedicaidID, TIN), extracts and adds relevant properties
     * to a map of additional properties.
     *
     * @param identifier the identifier object from which additional properties
     *                   are to be extracted. This can be an instance of a subclass
     *                   of Identifier such as MedicaidID or TIN.
     * @return a map containing additional properties relevant to the given identifier.
     *         The map includes properties like 'state' for MedicaidID or 'legalName' for TIN.
     */
    private Map<String, Object> getAdditionalProperties(Identifier identifier) {
        Map<String, Object> additionalProperties = new HashMap<>();
        if (identifier instanceof MedicaidID medicaidID) {
            additionalProperties.put("state",medicaidID.getState());
        }
        if (identifier instanceof TIN tin) {
            additionalProperties.put("legalName", tin.getLegalName());
        }
        return additionalProperties;
    }
}
