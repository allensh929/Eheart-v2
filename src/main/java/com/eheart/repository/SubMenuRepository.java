package com.eheart.repository;

import com.eheart.domain.SubMenu;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubMenu entity.
 */
@SuppressWarnings("unused")
public interface SubMenuRepository extends JpaRepository<SubMenu,Long> {

}
