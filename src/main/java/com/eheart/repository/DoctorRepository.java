package com.eheart.repository;

import com.eheart.domain.Doctor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Doctor entity.
 */
@SuppressWarnings("unused")
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("select distinct doctor from Doctor doctor left join fetch doctor.domains")
    List<Doctor> findAllWithEagerRelationships();

    @Query("select doctor from Doctor doctor left join fetch doctor.domains where doctor.id =:id")
    Doctor findOneWithEagerRelationships(@Param("id") Long id);

}
