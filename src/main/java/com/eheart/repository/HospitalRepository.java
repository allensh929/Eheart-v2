package com.eheart.repository;

import com.eheart.domain.Hospital;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hospital entity.
 */
@SuppressWarnings("unused")
public interface HospitalRepository extends JpaRepository<Hospital,Long> {

}
