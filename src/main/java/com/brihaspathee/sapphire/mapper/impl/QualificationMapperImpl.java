package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Qualification;
import com.brihaspathee.sapphire.mapper.interfaces.QualificationMapper;
import com.brihaspathee.sapphire.model.QualificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, December 2025
 * Time: 07:25
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class QualificationMapperImpl implements QualificationMapper {
    /**
     * Converts a Qualification entity into a QualificationDto object.
     *
     * @param qualification the Qualification entity to be converted
     * @return the corresponding QualificationDto object
     */
    @Override
    public QualificationDto toQualificationDto(Qualification qualification) {
        if (qualification == null) {
            return null;
        }
        QualificationDto qualificationDto = QualificationDto.builder()
                .elementId(qualification.getElementId())
                .type(qualification.getType())
                .value(qualification.getValue())
                .issuer(qualification.getIssuer())
                .state(qualification.getState())
                .level(qualification.getLevel())
                .startDate(qualification.getStartDate())
                .endDate(qualification.getEndDate())
                .build();
        return qualificationDto;
    }

    /**
     * Converts a list of Qualification entities to a list of QualificationDto objects.
     *
     * @param qualifications the list of Qualification entities to be converted
     * @return a list of QualificationDto objects corresponding to the provided Qualification entities
     */
    @Override
    public List<QualificationDto> toQualificationDtoList(List<Qualification> qualifications) {
        return qualifications.stream().map(this::toQualificationDto).toList();
    }
}
