package com.eheart.repository;

import com.eheart.domain.Clinic;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Clinic entity.
 */
@SuppressWarnings("unused")
public interface ClinicRepository extends JpaRepository<Clinic,Long> {

    @Query("select distinct clinic from Clinic clinic left join fetch clinic.hospitals")
    List<Clinic> findAllWithEagerRelationships();

    @Query("select clinic from Clinic clinic left join fetch clinic.hospitals where clinic.id =:id")
    Clinic findOneWithEagerRelationships(@Param("id") Long id);

}
