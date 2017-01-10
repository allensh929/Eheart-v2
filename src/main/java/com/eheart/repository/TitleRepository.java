package com.eheart.repository;

import com.eheart.domain.Title;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Title entity.
 */
@SuppressWarnings("unused")
public interface TitleRepository extends JpaRepository<Title,Long> {

}
