package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.ThirdMenuDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ThirdMenu and its DTO ThirdMenuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThirdMenuMapper {

    @Mapping(source = "superMenu.id", target = "superMenuId")
    ThirdMenuDTO thirdMenuToThirdMenuDTO(ThirdMenu thirdMenu);

    List<ThirdMenuDTO> thirdMenusToThirdMenuDTOs(List<ThirdMenu> thirdMenus);

    @Mapping(source = "superMenuId", target = "superMenu")
    ThirdMenu thirdMenuDTOToThirdMenu(ThirdMenuDTO thirdMenuDTO);

    List<ThirdMenu> thirdMenuDTOsToThirdMenus(List<ThirdMenuDTO> thirdMenuDTOs);

    default SubMenu subMenuFromId(Long id) {
        if (id == null) {
            return null;
        }
        SubMenu subMenu = new SubMenu();
        subMenu.setId(id);
        return subMenu;
    }
}
