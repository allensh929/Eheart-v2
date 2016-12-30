package com.eheart.service.impl;

import com.eheart.service.ProductCategoryService;
import com.eheart.domain.ProductCategory;
import com.eheart.repository.ProductCategoryRepository;
import com.eheart.repository.search.ProductCategorySearchRepository;
import com.eheart.service.dto.ProductCategoryDTO;
import com.eheart.service.mapper.ProductCategoryMapper;
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
 * Service Implementation for managing ProductCategory.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private final Logger log = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
    
    @Inject
    private ProductCategoryRepository productCategoryRepository;

    @Inject
    private ProductCategoryMapper productCategoryMapper;

    @Inject
    private ProductCategorySearchRepository productCategorySearchRepository;

    /**
     * Save a productCategory.
     *
     * @param productCategoryDTO the entity to save
     * @return the persisted entity
     */
    public ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO) {
        log.debug("Request to save ProductCategory : {}", productCategoryDTO);
        ProductCategory productCategory = productCategoryMapper.productCategoryDTOToProductCategory(productCategoryDTO);
        productCategory = productCategoryRepository.save(productCategory);
        ProductCategoryDTO result = productCategoryMapper.productCategoryToProductCategoryDTO(productCategory);
        productCategorySearchRepository.save(productCategory);
        return result;
    }

    /**
     *  Get all the productCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProductCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCategories");
        Page<ProductCategory> result = productCategoryRepository.findAll(pageable);
        return result.map(productCategory -> productCategoryMapper.productCategoryToProductCategoryDTO(productCategory));
    }

    /**
     *  Get one productCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductCategoryDTO findOne(Long id) {
        log.debug("Request to get ProductCategory : {}", id);
        ProductCategory productCategory = productCategoryRepository.findOne(id);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.productCategoryToProductCategoryDTO(productCategory);
        return productCategoryDTO;
    }

    /**
     *  Delete the  productCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductCategory : {}", id);
        productCategoryRepository.delete(id);
        productCategorySearchRepository.delete(id);
    }

    /**
     * Search for the productCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductCategories for query {}", query);
        Page<ProductCategory> result = productCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(productCategory -> productCategoryMapper.productCategoryToProductCategoryDTO(productCategory));
    }
}
