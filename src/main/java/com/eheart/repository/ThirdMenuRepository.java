package com.eheart.repository;

import com.eheart.domain.ThirdMenu;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThirdMenu entity.
 */
@SuppressWarnings("unused")
public interface ThirdMenuRepository extends JpaRepository<ThirdMenu,Long> {

}
