package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.ProductSubCategoryService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.ProductSubCategoryDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductSubCategory.
 */
@RestController
@RequestMapping("/api")
public class ProductSubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductSubCategoryResource.class);
        
    @Inject
    private ProductSubCategoryService productSubCategoryService;

    /**
     * POST  /product-sub-categories : Create a new productSubCategory.
     *
     * @param productSubCategoryDTO the productSubCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productSubCategoryDTO, or with status 400 (Bad Request) if the productSubCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-sub-categories")
    @Timed
    public ResponseEntity<ProductSubCategoryDTO> createProductSubCategory(@Valid @RequestBody ProductSubCategoryDTO productSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSubCategory : {}", productSubCategoryDTO);
        if (productSubCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productSubCategory", "idexists", "A new productSubCategory cannot already have an ID")).body(null);
        }
        ProductSubCategoryDTO result = productSubCategoryService.save(productSubCategoryDTO);
        return ResponseEntity.created(new URI("/api/product-sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productSubCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-sub-categories : Updates an existing productSubCategory.
     *
     * @param productSubCategoryDTO the productSubCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productSubCategoryDTO,
     * or with status 400 (Bad Request) if the productSubCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the productSubCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-sub-categories")
    @Timed
    public ResponseEntity<ProductSubCategoryDTO> updateProductSubCategory(@Valid @RequestBody ProductSubCategoryDTO productSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSubCategory : {}", productSubCategoryDTO);
        if (productSubCategoryDTO.getId() == null) {
            return createProductSubCategory(productSubCategoryDTO);
        }
        ProductSubCategoryDTO result = productSubCategoryService.save(productSubCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productSubCategory", productSubCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-sub-categories : get all the productSubCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productSubCategories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-sub-categories")
    @Timed
    public ResponseEntity<List<ProductSubCategoryDTO>> getAllProductSubCategories(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductSubCategories");
        Page<ProductSubCategoryDTO> page = productSubCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-sub-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-sub-categories/:id : get the "id" productSubCategory.
     *
     * @param id the id of the productSubCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productSubCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-sub-categories/{id}")
    @Timed
    public ResponseEntity<ProductSubCategoryDTO> getProductSubCategory(@PathVariable Long id) {
        log.debug("REST request to get ProductSubCategory : {}", id);
        ProductSubCategoryDTO productSubCategoryDTO = productSubCategoryService.findOne(id);
        return Optional.ofNullable(productSubCategoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-sub-categories/:id : delete the "id" productSubCategory.
     *
     * @param id the id of the productSubCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-sub-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProductSubCategory : {}", id);
        productSubCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productSubCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-sub-categories?query=:query : search for the productSubCategory corresponding
     * to the query.
     *
     * @param query the query of the productSubCategory search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/product-sub-categories")
    @Timed
    public ResponseEntity<List<ProductSubCategoryDTO>> searchProductSubCategories(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ProductSubCategories for query {}", query);
        Page<ProductSubCategoryDTO> page = productSubCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-sub-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
