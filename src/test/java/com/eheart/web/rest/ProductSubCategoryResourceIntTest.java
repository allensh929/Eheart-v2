package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.ProductSubCategory;
import com.eheart.repository.ProductSubCategoryRepository;
import com.eheart.service.ProductSubCategoryService;
import com.eheart.repository.search.ProductSubCategorySearchRepository;
import com.eheart.service.dto.ProductSubCategoryDTO;
import com.eheart.service.mapper.ProductSubCategoryMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.eheart.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductSubCategoryResource REST controller.
 *
 * @see ProductSubCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class ProductSubCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_CATEGORY_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_CATEGORY_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Inject
    private ProductSubCategoryMapper productSubCategoryMapper;

    @Inject
    private ProductSubCategoryService productSubCategoryService;

    @Inject
    private ProductSubCategorySearchRepository productSubCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductSubCategoryMockMvc;

    private ProductSubCategory productSubCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductSubCategoryResource productSubCategoryResource = new ProductSubCategoryResource();
        ReflectionTestUtils.setField(productSubCategoryResource, "productSubCategoryService", productSubCategoryService);
        this.restProductSubCategoryMockMvc = MockMvcBuilders.standaloneSetup(productSubCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSubCategory createEntity(EntityManager em) {
        ProductSubCategory productSubCategory = new ProductSubCategory()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .img(DEFAULT_IMG)
                .subCategoryPlaceholder1(DEFAULT_SUB_CATEGORY_PLACEHOLDER_1)
                .subCategoryPlaceholder2(DEFAULT_SUB_CATEGORY_PLACEHOLDER_2)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return productSubCategory;
    }

    @Before
    public void initTest() {
        productSubCategorySearchRepository.deleteAll();
        productSubCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSubCategory() throws Exception {
        int databaseSizeBeforeCreate = productSubCategoryRepository.findAll().size();

        // Create the ProductSubCategory
        ProductSubCategoryDTO productSubCategoryDTO = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory);

        restProductSubCategoryMockMvc.perform(post("/api/product-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSubCategory in the database
        List<ProductSubCategory> productSubCategoryList = productSubCategoryRepository.findAll();
        assertThat(productSubCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSubCategory testProductSubCategory = productSubCategoryList.get(productSubCategoryList.size() - 1);
        assertThat(testProductSubCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSubCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductSubCategory.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testProductSubCategory.getSubCategoryPlaceholder1()).isEqualTo(DEFAULT_SUB_CATEGORY_PLACEHOLDER_1);
        assertThat(testProductSubCategory.getSubCategoryPlaceholder2()).isEqualTo(DEFAULT_SUB_CATEGORY_PLACEHOLDER_2);
        assertThat(testProductSubCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductSubCategory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductSubCategory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testProductSubCategory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the ProductSubCategory in ElasticSearch
        ProductSubCategory productSubCategoryEs = productSubCategorySearchRepository.findOne(testProductSubCategory.getId());
        assertThat(productSubCategoryEs).isEqualToComparingFieldByField(testProductSubCategory);
    }

    @Test
    @Transactional
    public void createProductSubCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSubCategoryRepository.findAll().size();

        // Create the ProductSubCategory with an existing ID
        ProductSubCategory existingProductSubCategory = new ProductSubCategory();
        existingProductSubCategory.setId(1L);
        ProductSubCategoryDTO existingProductSubCategoryDTO = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(existingProductSubCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSubCategoryMockMvc.perform(post("/api/product-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProductSubCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductSubCategory> productSubCategoryList = productSubCategoryRepository.findAll();
        assertThat(productSubCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSubCategoryRepository.findAll().size();
        // set the field null
        productSubCategory.setName(null);

        // Create the ProductSubCategory, which fails.
        ProductSubCategoryDTO productSubCategoryDTO = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory);

        restProductSubCategoryMockMvc.perform(post("/api/product-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSubCategory> productSubCategoryList = productSubCategoryRepository.findAll();
        assertThat(productSubCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSubCategories() throws Exception {
        // Initialize the database
        productSubCategoryRepository.saveAndFlush(productSubCategory);

        // Get all the productSubCategoryList
        restProductSubCategoryMockMvc.perform(get("/api/product-sub-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSubCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].subCategoryPlaceholder1").value(hasItem(DEFAULT_SUB_CATEGORY_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].subCategoryPlaceholder2").value(hasItem(DEFAULT_SUB_CATEGORY_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getProductSubCategory() throws Exception {
        // Initialize the database
        productSubCategoryRepository.saveAndFlush(productSubCategory);

        // Get the productSubCategory
        restProductSubCategoryMockMvc.perform(get("/api/product-sub-categories/{id}", productSubCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSubCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG.toString()))
            .andExpect(jsonPath("$.subCategoryPlaceholder1").value(DEFAULT_SUB_CATEGORY_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.subCategoryPlaceholder2").value(DEFAULT_SUB_CATEGORY_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductSubCategory() throws Exception {
        // Get the productSubCategory
        restProductSubCategoryMockMvc.perform(get("/api/product-sub-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSubCategory() throws Exception {
        // Initialize the database
        productSubCategoryRepository.saveAndFlush(productSubCategory);
        productSubCategorySearchRepository.save(productSubCategory);
        int databaseSizeBeforeUpdate = productSubCategoryRepository.findAll().size();

        // Update the productSubCategory
        ProductSubCategory updatedProductSubCategory = productSubCategoryRepository.findOne(productSubCategory.getId());
        updatedProductSubCategory
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .img(UPDATED_IMG)
                .subCategoryPlaceholder1(UPDATED_SUB_CATEGORY_PLACEHOLDER_1)
                .subCategoryPlaceholder2(UPDATED_SUB_CATEGORY_PLACEHOLDER_2)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ProductSubCategoryDTO productSubCategoryDTO = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(updatedProductSubCategory);

        restProductSubCategoryMockMvc.perform(put("/api/product-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSubCategory in the database
        List<ProductSubCategory> productSubCategoryList = productSubCategoryRepository.findAll();
        assertThat(productSubCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductSubCategory testProductSubCategory = productSubCategoryList.get(productSubCategoryList.size() - 1);
        assertThat(testProductSubCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSubCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductSubCategory.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testProductSubCategory.getSubCategoryPlaceholder1()).isEqualTo(UPDATED_SUB_CATEGORY_PLACEHOLDER_1);
        assertThat(testProductSubCategory.getSubCategoryPlaceholder2()).isEqualTo(UPDATED_SUB_CATEGORY_PLACEHOLDER_2);
        assertThat(testProductSubCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductSubCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductSubCategory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testProductSubCategory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the ProductSubCategory in ElasticSearch
        ProductSubCategory productSubCategoryEs = productSubCategorySearchRepository.findOne(testProductSubCategory.getId());
        assertThat(productSubCategoryEs).isEqualToComparingFieldByField(testProductSubCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = productSubCategoryRepository.findAll().size();

        // Create the ProductSubCategory
        ProductSubCategoryDTO productSubCategoryDTO = productSubCategoryMapper.productSubCategoryToProductSubCategoryDTO(productSubCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductSubCategoryMockMvc.perform(put("/api/product-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSubCategory in the database
        List<ProductSubCategory> productSubCategoryList = productSubCategoryRepository.findAll();
        assertThat(productSubCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductSubCategory() throws Exception {
        // Initialize the database
        productSubCategoryRepository.saveAndFlush(productSubCategory);
        productSubCategorySearchRepository.save(productSubCategory);
        int databaseSizeBeforeDelete = productSubCategoryRepository.findAll().size();

        // Get the productSubCategory
        restProductSubCategoryMockMvc.perform(delete("/api/product-sub-categories/{id}", productSubCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean productSubCategoryExistsInEs = productSubCategorySearchRepository.exists(productSubCategory.getId());
        assertThat(productSubCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductSubCategory> productSubCategoryList = productSubCategoryRepository.findAll();
        assertThat(productSubCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductSubCategory() throws Exception {
        // Initialize the database
        productSubCategoryRepository.saveAndFlush(productSubCategory);
        productSubCategorySearchRepository.save(productSubCategory);

        // Search the productSubCategory
        restProductSubCategoryMockMvc.perform(get("/api/_search/product-sub-categories?query=id:" + productSubCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSubCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].subCategoryPlaceholder1").value(hasItem(DEFAULT_SUB_CATEGORY_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].subCategoryPlaceholder2").value(hasItem(DEFAULT_SUB_CATEGORY_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
