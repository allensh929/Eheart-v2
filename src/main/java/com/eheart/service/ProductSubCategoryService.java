package com.eheart.service;

import com.eheart.service.dto.ProductSubCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ProductSubCategory.
 */
public interface ProductSubCategoryService {

    /**
     * Save a productSubCategory.
     *
     * @param productSubCategoryDTO the entity to save
     * @return the persisted entity
     */
    ProductSubCategoryDTO save(ProductSubCategoryDTO productSubCategoryDTO);

    /**
     *  Get all the productSubCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductSubCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" productSubCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductSubCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" productSubCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productSubCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductSubCategoryDTO> search(String query, Pageable pageable);
}
