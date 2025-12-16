package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.LineOfBusiness;
import com.brihaspathee.sapphire.mapper.interfaces.LineOfBusinessMapper;
import com.brihaspathee.sapphire.model.LineOfBusinessDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12/16/25
 * Time: 6:08 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class LineOfBusinessMapperImpl implements LineOfBusinessMapper {
    /**
     * Converts the given LineOfBusiness entity into a LineOfBusinessDto object.
     *
     * @param lineOfBusiness the LineOfBusiness entity to be converted
     * @return a LineOfBusinessDto object corresponding to the provided LineOfBusiness entity
     */
    @Override
    public LineOfBusinessDto toLineOfBusinessDto(LineOfBusiness lineOfBusiness) {
        if (lineOfBusiness == null) {
            return null;
        }
        return LineOfBusinessDto.builder()
                .elementId(lineOfBusiness.getElementId())
                .code(lineOfBusiness.getCode())
                .name(lineOfBusiness.getName())
                .build();
    }

    /**
     * Converts a list of LineOfBusiness entities into a list of LineOfBusinessDto objects.
     *
     * @param lineOfBusinesses the list of LineOfBusiness entities to be converted
     * @return a list of LineOfBusinessDto objects corresponding to the provided LineOfBusiness entities
     */
    @Override
    public List<LineOfBusinessDto> toLineOfBusinessDtoList(List<LineOfBusiness> lineOfBusinesses) {
        return lineOfBusinesses.stream().map(this::toLineOfBusinessDto).toList();
    }
}
