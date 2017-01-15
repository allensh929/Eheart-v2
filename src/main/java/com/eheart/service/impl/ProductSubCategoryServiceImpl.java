package com.eheart.service.impl;

import com.eheart.service.ProductSubCategoryService;
import com.eheart.domain.ProductSubCategory;
import com.eheart.repository.ProductSubCategoryRepository;
import com.eheart.repository.search.ProductSubCategorySearchRepository;
import com.eheart.service.dto.ProductSubCategoryDTO;
import com.eheart.service.mapper.ProductSubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductSubCategory.
 */
@Service
@Transactional
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService{

    private final Logger log = LoggerFactory.getLogger(ProductSubCategoryServiceImpl.class);
    
    @Inject
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Inject
    private ProductSubCategoryMapper productSubCategoryMapper;

    @Inject
    private ProductSubCategorySearchRepository productSubCategorySearchRepository;

    /**
     * Save a productSubCategory.
     *
     * @param productSubCategoryDTO the entity to save
     * @return the persisted entity
     */
    public ProductSubCategoryDTO save(ProductSubCategoryDTO productSubCategoryDTO) {
        log.debug("Request to save ProductSubCategory : {}", productSubCategoryDTO);
        ProductSubCategory productSubCategory = productSubCategoryMapper.productSubCategoryDTOToProductSubCategory(productSubCategoryDTO);
        productSubCategory = productSubCategoryRepository.save(productSubCategory);
        ProductSubCategoryDTO result = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory);
        productSubCategorySearchRepository.save(productSubCategory);
        return result;
    }

    /**
     *  Get all the productSubCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProductSubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSubCategories");
        Page<ProductSubCategory> result = productSubCategoryRepository.findAll(pageable);
        return result.map(productSubCategory -> productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory));
    }

    /**
     *  Get one productSubCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductSubCategoryDTO findOne(Long id) {
        log.debug("Request to get ProductSubCategory : {}", id);
        ProductSubCategory productSubCategory = productSubCategoryRepository.findOne(id);
        ProductSubCategoryDTO productSubCategoryDTO = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory);
        return productSubCategoryDTO;
    }

    /**
     *  Delete the  productSubCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductSubCategory : {}", id);
        productSubCategoryRepository.delete(id);
        productSubCategorySearchRepository.delete(id);
    }

    /**
     * Search for the productSubCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductSubCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductSubCategories for query {}", query);
        Page<ProductSubCategory> result = productSubCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(productSubCategory -> productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory));
    }
}
