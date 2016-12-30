package com.eheart.repository;

import com.eheart.domain.ProductCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductCategory entity.
 */
@SuppressWarnings("unused")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

}
