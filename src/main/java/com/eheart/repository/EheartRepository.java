package com.eheart.repository;

import com.eheart.domain.Eheart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Eheart entity.
 */
@SuppressWarnings("unused")
public interface EheartRepository extends JpaRepository<Eheart,Long> {

}
