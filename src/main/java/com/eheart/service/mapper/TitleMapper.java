package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.TitleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Title and its DTO TitleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TitleMapper {

    TitleDTO titleToTitleDTO(Title title);

    List<TitleDTO> titlesToTitleDTOs(List<Title> titles);

    Title titleDTOToTitle(TitleDTO titleDTO);

    List<Title> titleDTOsToTitles(List<TitleDTO> titleDTOs);
}
