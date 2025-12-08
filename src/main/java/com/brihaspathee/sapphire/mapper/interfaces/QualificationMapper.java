package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Qualification;
import com.brihaspathee.sapphire.model.QualificationDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, December 2025
 * Time: 07:23
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface QualificationMapper {

    /**
     * Converts a Qualification entity into a QualificationDto object.
     *
     * @param qualification the Qualification entity to be converted
     * @return the corresponding QualificationDto object
     */
    QualificationDto toQualificationDto(Qualification qualification);

    /**
     * Converts a list of Qualification entities to a list of QualificationDto objects.
     *
     * @param qualifications the list of Qualification entities to be converted
     * @return a list of QualificationDto objects corresponding to the provided Qualification entities
     */
    List<QualificationDto> toQualificationDtoList(List<Qualification> qualifications);
}
