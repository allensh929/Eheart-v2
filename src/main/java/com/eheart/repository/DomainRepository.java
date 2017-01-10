package com.eheart.repository;

import com.eheart.domain.Domain;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Domain entity.
 */
@SuppressWarnings("unused")
public interface DomainRepository extends JpaRepository<Domain,Long> {

}
