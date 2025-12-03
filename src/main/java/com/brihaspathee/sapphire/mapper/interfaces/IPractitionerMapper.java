package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.model.PractitionerDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/2/25
 * Time: 6:25 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface IPractitionerMapper {

    /**
     * Converts the provided Practitioner entity into a PractitionerDto object.
     *
     * @param practitioner the Practitioner entity to be converted
     * @return a PractitionerDto object that corresponds to the provided Practitioner entity
     */
    PractitionerDto toPractitionerDto(Practitioner practitioner);
}
