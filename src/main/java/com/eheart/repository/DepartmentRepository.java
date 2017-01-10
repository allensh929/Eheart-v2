package com.eheart.repository;

import com.eheart.domain.Department;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Department entity.
 */
@SuppressWarnings("unused")
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("select distinct department from Department department left join fetch department.hospitals")
    List<Department> findAllWithEagerRelationships();

    @Query("select department from Department department left join fetch department.hospitals where department.id =:id")
    Department findOneWithEagerRelationships(@Param("id") Long id);

}
