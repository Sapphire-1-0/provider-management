package com.brihaspathee.sapphire.mapper.interfaces;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.model.IdentifierDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, December 2025
 * Time: 12:58
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface IdentifierMapper {

    /**
     * Converts the given Identifier entity into an IdentifierDto object.
     *
     * @param identifier the Identifier entity to be converted
     * @return an IdentifierDto object corresponding to the provided Identifier entity
     */
    IdentifierDto toIdentifierDto(Identifier identifier);

    /**
     * Converts a list of {@link Identifier} objects into a list of {@link IdentifierDto} objects.
     *
     * @param identifiers the list of {@link Identifier} objects to be converted
     * @return a list of {@link IdentifierDto} objects corresponding to the provided {@link Identifier} objects
     */
    List<IdentifierDto> toIdentifierDtoList(List<Identifier> identifiers);
}
