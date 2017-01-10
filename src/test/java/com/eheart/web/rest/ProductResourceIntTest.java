package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Product;
import com.eheart.repository.ProductRepository;
import com.eheart.service.ProductService;
import com.eheart.repository.search.ProductSearchRepository;
import com.eheart.service.dto.ProductDTO;
import com.eheart.service.mapper.ProductMapper;

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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_SHELF_LIFE = "AAAAAAAAAA";
    private static final String UPDATED_SHELF_LIFE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PRODUCE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRODUCE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PRODUCER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCER = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCE_LOACTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCE_LOACTION = "BBBBBBBBBB";

    private static final String DEFAULT_GUIGE = "AAAAAAAAAA";
    private static final String UPDATED_GUIGE = "BBBBBBBBBB";

    private static final String DEFAULT_CHENGFEN = "AAAAAAAAAA";
    private static final String UPDATED_CHENGFEN = "BBBBBBBBBB";

    private static final String DEFAULT_YONGFA = "AAAAAAAAAA";
    private static final String UPDATED_YONGFA = "BBBBBBBBBB";

    private static final String DEFAULT_XINGZHUANG = "AAAAAAAAAA";
    private static final String UPDATED_XINGZHUANG = "BBBBBBBBBB";

    private static final String DEFAULT_GONGNENG = "AAAAAAAAAA";
    private static final String UPDATED_GONGNENG = "BBBBBBBBBB";

    private static final String DEFAULT_PIHAO = "AAAAAAAAAA";
    private static final String UPDATED_PIHAO = "BBBBBBBBBB";

    private static final String DEFAULT_BULIANGFANYING = "AAAAAAAAAA";
    private static final String UPDATED_BULIANGFANYING = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Integer DEFAULT_INVENTORY = 1;
    private static final Integer UPDATED_INVENTORY = 2;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Boolean DEFAULT_IS_NEW = false;
    private static final Boolean UPDATED_IS_NEW = true;

    private static final Boolean DEFAULT_FAVORITE = false;
    private static final Boolean UPDATED_FAVORITE = true;

    private static final String DEFAULT_PRODUCT_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_PLACEHOLDER_3 = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_PLACEHOLDER_3 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductService productService;

    @Inject
    private ProductSearchRepository productSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productService", productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .img(DEFAULT_IMG)
                .link(DEFAULT_LINK)
                .price(DEFAULT_PRICE)
                .shelfLife(DEFAULT_SHELF_LIFE)
                .produceDate(DEFAULT_PRODUCE_DATE)
                .producer(DEFAULT_PRODUCER)
                .produceLoaction(DEFAULT_PRODUCE_LOACTION)
                .guige(DEFAULT_GUIGE)
                .chengfen(DEFAULT_CHENGFEN)
                .yongfa(DEFAULT_YONGFA)
                .xingzhuang(DEFAULT_XINGZHUANG)
                .gongneng(DEFAULT_GONGNENG)
                .pihao(DEFAULT_PIHAO)
                .buliangfanying(DEFAULT_BULIANGFANYING)
                .notes(DEFAULT_NOTES)
                .inventory(DEFAULT_INVENTORY)
                .total(DEFAULT_TOTAL)
                .isNew(DEFAULT_IS_NEW)
                .favorite(DEFAULT_FAVORITE)
                .productPlaceholder1(DEFAULT_PRODUCT_PLACEHOLDER_1)
                .productPlaceholder2(DEFAULT_PRODUCT_PLACEHOLDER_2)
                .productPlaceholder3(DEFAULT_PRODUCT_PLACEHOLDER_3)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return product;
    }

    @Before
    public void initTest() {
        productSearchRepository.deleteAll();
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testProduct.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getShelfLife()).isEqualTo(DEFAULT_SHELF_LIFE);
        assertThat(testProduct.getProduceDate()).isEqualTo(DEFAULT_PRODUCE_DATE);
        assertThat(testProduct.getProducer()).isEqualTo(DEFAULT_PRODUCER);
        assertThat(testProduct.getProduceLoaction()).isEqualTo(DEFAULT_PRODUCE_LOACTION);
        assertThat(testProduct.getGuige()).isEqualTo(DEFAULT_GUIGE);
        assertThat(testProduct.getChengfen()).isEqualTo(DEFAULT_CHENGFEN);
        assertThat(testProduct.getYongfa()).isEqualTo(DEFAULT_YONGFA);
        assertThat(testProduct.getXingzhuang()).isEqualTo(DEFAULT_XINGZHUANG);
        assertThat(testProduct.getGongneng()).isEqualTo(DEFAULT_GONGNENG);
        assertThat(testProduct.getPihao()).isEqualTo(DEFAULT_PIHAO);
        assertThat(testProduct.getBuliangfanying()).isEqualTo(DEFAULT_BULIANGFANYING);
        assertThat(testProduct.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testProduct.getInventory()).isEqualTo(DEFAULT_INVENTORY);
        assertThat(testProduct.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testProduct.isIsNew()).isEqualTo(DEFAULT_IS_NEW);
        assertThat(testProduct.isFavorite()).isEqualTo(DEFAULT_FAVORITE);
        assertThat(testProduct.getProductPlaceholder1()).isEqualTo(DEFAULT_PRODUCT_PLACEHOLDER_1);
        assertThat(testProduct.getProductPlaceholder2()).isEqualTo(DEFAULT_PRODUCT_PLACEHOLDER_2);
        assertThat(testProduct.getProductPlaceholder3()).isEqualTo(DEFAULT_PRODUCT_PLACEHOLDER_3);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Product in ElasticSearch
        Product productEs = productSearchRepository.findOne(testProduct.getId());
        assertThat(productEs).isEqualToComparingFieldByField(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        ProductDTO existingProductDTO = productMapper.productToProductDTO(existingProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].shelfLife").value(hasItem(DEFAULT_SHELF_LIFE.toString())))
            .andExpect(jsonPath("$.[*].produceDate").value(hasItem(sameInstant(DEFAULT_PRODUCE_DATE))))
            .andExpect(jsonPath("$.[*].producer").value(hasItem(DEFAULT_PRODUCER.toString())))
            .andExpect(jsonPath("$.[*].produceLoaction").value(hasItem(DEFAULT_PRODUCE_LOACTION.toString())))
            .andExpect(jsonPath("$.[*].guige").value(hasItem(DEFAULT_GUIGE.toString())))
            .andExpect(jsonPath("$.[*].chengfen").value(hasItem(DEFAULT_CHENGFEN.toString())))
            .andExpect(jsonPath("$.[*].yongfa").value(hasItem(DEFAULT_YONGFA.toString())))
            .andExpect(jsonPath("$.[*].xingzhuang").value(hasItem(DEFAULT_XINGZHUANG.toString())))
            .andExpect(jsonPath("$.[*].gongneng").value(hasItem(DEFAULT_GONGNENG.toString())))
            .andExpect(jsonPath("$.[*].pihao").value(hasItem(DEFAULT_PIHAO.toString())))
            .andExpect(jsonPath("$.[*].buliangfanying").value(hasItem(DEFAULT_BULIANGFANYING.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].inventory").value(hasItem(DEFAULT_INVENTORY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].isNew").value(hasItem(DEFAULT_IS_NEW.booleanValue())))
            .andExpect(jsonPath("$.[*].favorite").value(hasItem(DEFAULT_FAVORITE.booleanValue())))
            .andExpect(jsonPath("$.[*].productPlaceholder1").value(hasItem(DEFAULT_PRODUCT_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].productPlaceholder2").value(hasItem(DEFAULT_PRODUCT_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].productPlaceholder3").value(hasItem(DEFAULT_PRODUCT_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.shelfLife").value(DEFAULT_SHELF_LIFE.toString()))
            .andExpect(jsonPath("$.produceDate").value(sameInstant(DEFAULT_PRODUCE_DATE)))
            .andExpect(jsonPath("$.producer").value(DEFAULT_PRODUCER.toString()))
            .andExpect(jsonPath("$.produceLoaction").value(DEFAULT_PRODUCE_LOACTION.toString()))
            .andExpect(jsonPath("$.guige").value(DEFAULT_GUIGE.toString()))
            .andExpect(jsonPath("$.chengfen").value(DEFAULT_CHENGFEN.toString()))
            .andExpect(jsonPath("$.yongfa").value(DEFAULT_YONGFA.toString()))
            .andExpect(jsonPath("$.xingzhuang").value(DEFAULT_XINGZHUANG.toString()))
            .andExpect(jsonPath("$.gongneng").value(DEFAULT_GONGNENG.toString()))
            .andExpect(jsonPath("$.pihao").value(DEFAULT_PIHAO.toString()))
            .andExpect(jsonPath("$.buliangfanying").value(DEFAULT_BULIANGFANYING.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.inventory").value(DEFAULT_INVENTORY))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.isNew").value(DEFAULT_IS_NEW.booleanValue()))
            .andExpect(jsonPath("$.favorite").value(DEFAULT_FAVORITE.booleanValue()))
            .andExpect(jsonPath("$.productPlaceholder1").value(DEFAULT_PRODUCT_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.productPlaceholder2").value(DEFAULT_PRODUCT_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.productPlaceholder3").value(DEFAULT_PRODUCT_PLACEHOLDER_3.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        productSearchRepository.save(product);
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .img(UPDATED_IMG)
                .link(UPDATED_LINK)
                .price(UPDATED_PRICE)
                .shelfLife(UPDATED_SHELF_LIFE)
                .produceDate(UPDATED_PRODUCE_DATE)
                .producer(UPDATED_PRODUCER)
                .produceLoaction(UPDATED_PRODUCE_LOACTION)
                .guige(UPDATED_GUIGE)
                .chengfen(UPDATED_CHENGFEN)
                .yongfa(UPDATED_YONGFA)
                .xingzhuang(UPDATED_XINGZHUANG)
                .gongneng(UPDATED_GONGNENG)
                .pihao(UPDATED_PIHAO)
                .buliangfanying(UPDATED_BULIANGFANYING)
                .notes(UPDATED_NOTES)
                .inventory(UPDATED_INVENTORY)
                .total(UPDATED_TOTAL)
                .isNew(UPDATED_IS_NEW)
                .favorite(UPDATED_FAVORITE)
                .productPlaceholder1(UPDATED_PRODUCT_PLACEHOLDER_1)
                .productPlaceholder2(UPDATED_PRODUCT_PLACEHOLDER_2)
                .productPlaceholder3(UPDATED_PRODUCT_PLACEHOLDER_3)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ProductDTO productDTO = productMapper.productToProductDTO(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testProduct.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getShelfLife()).isEqualTo(UPDATED_SHELF_LIFE);
        assertThat(testProduct.getProduceDate()).isEqualTo(UPDATED_PRODUCE_DATE);
        assertThat(testProduct.getProducer()).isEqualTo(UPDATED_PRODUCER);
        assertThat(testProduct.getProduceLoaction()).isEqualTo(UPDATED_PRODUCE_LOACTION);
        assertThat(testProduct.getGuige()).isEqualTo(UPDATED_GUIGE);
        assertThat(testProduct.getChengfen()).isEqualTo(UPDATED_CHENGFEN);
        assertThat(testProduct.getYongfa()).isEqualTo(UPDATED_YONGFA);
        assertThat(testProduct.getXingzhuang()).isEqualTo(UPDATED_XINGZHUANG);
        assertThat(testProduct.getGongneng()).isEqualTo(UPDATED_GONGNENG);
        assertThat(testProduct.getPihao()).isEqualTo(UPDATED_PIHAO);
        assertThat(testProduct.getBuliangfanying()).isEqualTo(UPDATED_BULIANGFANYING);
        assertThat(testProduct.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testProduct.getInventory()).isEqualTo(UPDATED_INVENTORY);
        assertThat(testProduct.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testProduct.isIsNew()).isEqualTo(UPDATED_IS_NEW);
        assertThat(testProduct.isFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testProduct.getProductPlaceholder1()).isEqualTo(UPDATED_PRODUCT_PLACEHOLDER_1);
        assertThat(testProduct.getProductPlaceholder2()).isEqualTo(UPDATED_PRODUCT_PLACEHOLDER_2);
        assertThat(testProduct.getProductPlaceholder3()).isEqualTo(UPDATED_PRODUCT_PLACEHOLDER_3);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Product in ElasticSearch
        Product productEs = productSearchRepository.findOne(testProduct.getId());
        assertThat(productEs).isEqualToComparingFieldByField(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        productSearchRepository.save(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean productExistsInEs = productSearchRepository.exists(product.getId());
        assertThat(productExistsInEs).isFalse();

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        productSearchRepository.save(product);

        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].shelfLife").value(hasItem(DEFAULT_SHELF_LIFE.toString())))
            .andExpect(jsonPath("$.[*].produceDate").value(hasItem(sameInstant(DEFAULT_PRODUCE_DATE))))
            .andExpect(jsonPath("$.[*].producer").value(hasItem(DEFAULT_PRODUCER.toString())))
            .andExpect(jsonPath("$.[*].produceLoaction").value(hasItem(DEFAULT_PRODUCE_LOACTION.toString())))
            .andExpect(jsonPath("$.[*].guige").value(hasItem(DEFAULT_GUIGE.toString())))
            .andExpect(jsonPath("$.[*].chengfen").value(hasItem(DEFAULT_CHENGFEN.toString())))
            .andExpect(jsonPath("$.[*].yongfa").value(hasItem(DEFAULT_YONGFA.toString())))
            .andExpect(jsonPath("$.[*].xingzhuang").value(hasItem(DEFAULT_XINGZHUANG.toString())))
            .andExpect(jsonPath("$.[*].gongneng").value(hasItem(DEFAULT_GONGNENG.toString())))
            .andExpect(jsonPath("$.[*].pihao").value(hasItem(DEFAULT_PIHAO.toString())))
            .andExpect(jsonPath("$.[*].buliangfanying").value(hasItem(DEFAULT_BULIANGFANYING.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].inventory").value(hasItem(DEFAULT_INVENTORY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].isNew").value(hasItem(DEFAULT_IS_NEW.booleanValue())))
            .andExpect(jsonPath("$.[*].favorite").value(hasItem(DEFAULT_FAVORITE.booleanValue())))
            .andExpect(jsonPath("$.[*].productPlaceholder1").value(hasItem(DEFAULT_PRODUCT_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].productPlaceholder2").value(hasItem(DEFAULT_PRODUCT_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].productPlaceholder3").value(hasItem(DEFAULT_PRODUCT_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
