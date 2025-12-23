package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.MedicaidID;
import com.brihaspathee.sapphire.domain.entity.TIN;
import com.brihaspathee.sapphire.mapper.interfaces.IdentifierMapper;
import com.brihaspathee.sapphire.model.IdentifierDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, December 2025
 * Time: 12:59
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class IdentifierMapperImpl implements IdentifierMapper {
    /**
     * Converts the given Identifier entity into an IdentifierDto object.
     *
     * @param identifier the Identifier entity to be converted
     * @return an IdentifierDto object corresponding to the provided Identifier entity
     */
    @Override
    public IdentifierDto toIdentifierDto(Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        IdentifierDto identifierDto = IdentifierDto.builder()
                .elementId(identifier.getElementId())
                .value(identifier.getValue())
                .type(identifier.getClass().getSimpleName())
                .startDate(identifier.getStartDate())
                .endDate(identifier.getEndDate())
                .additionalProperties(getAdditionalProperties(identifier))
                .build();
        return identifierDto;
    }

    /**
     * Converts a list of {@link Identifier} objects into a list of {@link IdentifierDto} objects.
     *
     * @param identifiers the list of {@link Identifier} objects to be converted
     * @return a list of {@link IdentifierDto} objects corresponding to the provided {@link Identifier} objects
     */
    @Override
    public List<IdentifierDto> toIdentifierDtoList(List<Identifier> identifiers) {
        return identifiers.stream().map(this::toIdentifierDto).toList();
    }

    /**
     * Constructs a map of additional properties based on the type of the provided identifier.
     * If the identifier is of type {@code MedicaidID}, its state is added to the map.
     * If the identifier is of type {@code TIN}, its legal name is added to the map.
     *
     * @param identifier the identifier whose additional properties need to be collected
     * @return a map containing additional properties specific to the identifier type
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
