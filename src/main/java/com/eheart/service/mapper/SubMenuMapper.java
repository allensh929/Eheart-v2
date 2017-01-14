package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.SubMenuDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SubMenu and its DTO SubMenuDTO.
 */
@Mapper(componentModel = "spring", uses = {MenuMapper.class, ThirdMenuMapper.class})
public interface SubMenuMapper {

    @Mapping(source = "superMenu.id", target = "superMenuId")
    SubMenuDTO subMenuToSubMenuDTO(SubMenu subMenu);

    List<SubMenuDTO> subMenusToSubMenuDTOs(List<SubMenu> subMenus);

    @Mapping(target = "hasSubMenus")
    @Mapping(source = "superMenuId", target = "superMenu")
    SubMenu subMenuDTOToSubMenu(SubMenuDTO subMenuDTO);

    List<SubMenu> subMenuDTOsToSubMenus(List<SubMenuDTO> subMenuDTOs);

    default Menu menuFromId(Long id) {
        if (id == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setId(id);
        return menu;
    }
}
