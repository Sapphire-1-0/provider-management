package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.LineOfBusiness;
import com.brihaspathee.sapphire.model.LineOfBusinessDto;

import javax.sound.sampled.Line;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:03 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface LineOfBusinessMapper {

    /**
     * Converts the given LineOfBusiness entity into a LineOfBusinessDto object.
     *
     * @param lineOfBusiness the LineOfBusiness entity to be converted
     * @return a LineOfBusinessDto object corresponding to the provided LineOfBusiness entity
     */
    LineOfBusinessDto toLineOfBusinessDto(LineOfBusiness lineOfBusiness);

    /**
     * Converts a list of LineOfBusiness entities into a list of LineOfBusinessDto objects.
     *
     * @param lineOfBusinesses the list of LineOfBusiness entities to be converted
     * @return a list of LineOfBusinessDto objects corresponding to the provided LineOfBusiness entities
     */
    List<LineOfBusinessDto> toLineOfBusinessDtoList(List<LineOfBusiness> lineOfBusinesses);
}
