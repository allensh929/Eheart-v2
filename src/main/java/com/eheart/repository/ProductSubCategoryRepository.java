package com.eheart.repository;

import com.eheart.domain.ProductSubCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductSubCategory entity.
 */
@SuppressWarnings("unused")
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory,Long> {

}
