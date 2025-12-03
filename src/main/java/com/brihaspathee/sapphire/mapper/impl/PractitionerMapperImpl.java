package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.mapper.interfaces.IPractitionerMapper;
import com.brihaspathee.sapphire.model.PractitionerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
public class PractitionerMapperImpl implements IPractitionerMapper {

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
        PractitionerDto practitionerDto = PractitionerDto.builder().build();
        practitionerDto.setElementId(practitioner.getElementId());
        practitionerDto.setFirstName(practitioner.getFirstName());
        practitionerDto.setLastName(practitioner.getLastName());
        practitionerDto.setMiddleName(practitioner.getMiddleName());
        practitionerDto.setBirthDate(practitioner.getBirthDate());
        practitionerDto.setGender(practitioner.getGender());
        practitionerDto.setAltFirstName(practitioner.getAltFirstName());
        practitionerDto.setAltMiddleName(practitioner.getAltMiddleName());
        practitionerDto.setAltLastName(practitioner.getAltLastName());
        return practitionerDto;
    }
}
