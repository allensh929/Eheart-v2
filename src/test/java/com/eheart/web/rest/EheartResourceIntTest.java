package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Eheart;
import com.eheart.repository.EheartRepository;
import com.eheart.service.EheartService;
import com.eheart.repository.search.EheartSearchRepository;
import com.eheart.service.dto.EheartDTO;
import com.eheart.service.mapper.EheartMapper;

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
 * Test class for the EheartResource REST controller.
 *
 * @see EheartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class EheartResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT = "BBBBBBBBBB";

    private static final String DEFAULT_COPYRIGHT = "AAAAAAAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_EHEART_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_EHEART_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EHEART_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_EHEART_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EHEART_PLACEHOLDER_3 = "AAAAAAAAAA";
    private static final String UPDATED_EHEART_PLACEHOLDER_3 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private EheartRepository eheartRepository;

    @Inject
    private EheartMapper eheartMapper;

    @Inject
    private EheartService eheartService;

    @Inject
    private EheartSearchRepository eheartSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEheartMockMvc;

    private Eheart eheart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EheartResource eheartResource = new EheartResource();
        ReflectionTestUtils.setField(eheartResource, "eheartService", eheartService);
        this.restEheartMockMvc = MockMvcBuilders.standaloneSetup(eheartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eheart createEntity(EntityManager em) {
        Eheart eheart = new Eheart()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .logo(DEFAULT_LOGO)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .fax(DEFAULT_FAX)
                .address(DEFAULT_ADDRESS)
                .wechat(DEFAULT_WECHAT)
                .copyright(DEFAULT_COPYRIGHT)
                .eheartPlaceholder1(DEFAULT_EHEART_PLACEHOLDER_1)
                .eheartPlaceholder2(DEFAULT_EHEART_PLACEHOLDER_2)
                .eheartPlaceholder3(DEFAULT_EHEART_PLACEHOLDER_3)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return eheart;
    }

    @Before
    public void initTest() {
        eheartSearchRepository.deleteAll();
        eheart = createEntity(em);
    }

    @Test
    @Transactional
    public void createEheart() throws Exception {
        int databaseSizeBeforeCreate = eheartRepository.findAll().size();

        // Create the Eheart
        EheartDTO eheartDTO = eheartMapper.eheartToEheartDTO(eheart);

        restEheartMockMvc.perform(post("/api/ehearts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eheartDTO)))
            .andExpect(status().isCreated());

        // Validate the Eheart in the database
        List<Eheart> eheartList = eheartRepository.findAll();
        assertThat(eheartList).hasSize(databaseSizeBeforeCreate + 1);
        Eheart testEheart = eheartList.get(eheartList.size() - 1);
        assertThat(testEheart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEheart.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEheart.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testEheart.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEheart.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testEheart.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testEheart.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEheart.getWechat()).isEqualTo(DEFAULT_WECHAT);
        assertThat(testEheart.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testEheart.getEheartPlaceholder1()).isEqualTo(DEFAULT_EHEART_PLACEHOLDER_1);
        assertThat(testEheart.getEheartPlaceholder2()).isEqualTo(DEFAULT_EHEART_PLACEHOLDER_2);
        assertThat(testEheart.getEheartPlaceholder3()).isEqualTo(DEFAULT_EHEART_PLACEHOLDER_3);
        assertThat(testEheart.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEheart.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEheart.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testEheart.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Eheart in ElasticSearch
        Eheart eheartEs = eheartSearchRepository.findOne(testEheart.getId());
        assertThat(eheartEs).isEqualToComparingFieldByField(testEheart);
    }

    @Test
    @Transactional
    public void createEheartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eheartRepository.findAll().size();

        // Create the Eheart with an existing ID
        Eheart existingEheart = new Eheart();
        existingEheart.setId(1L);
        EheartDTO existingEheartDTO = eheartMapper.eheartToEheartDTO(existingEheart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEheartMockMvc.perform(post("/api/ehearts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEheartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Eheart> eheartList = eheartRepository.findAll();
        assertThat(eheartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eheartRepository.findAll().size();
        // set the field null
        eheart.setName(null);

        // Create the Eheart, which fails.
        EheartDTO eheartDTO = eheartMapper.eheartToEheartDTO(eheart);

        restEheartMockMvc.perform(post("/api/ehearts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eheartDTO)))
            .andExpect(status().isBadRequest());

        List<Eheart> eheartList = eheartRepository.findAll();
        assertThat(eheartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEhearts() throws Exception {
        // Initialize the database
        eheartRepository.saveAndFlush(eheart);

        // Get all the eheartList
        restEheartMockMvc.perform(get("/api/ehearts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eheart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].wechat").value(hasItem(DEFAULT_WECHAT.toString())))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
            .andExpect(jsonPath("$.[*].eheartPlaceholder1").value(hasItem(DEFAULT_EHEART_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].eheartPlaceholder2").value(hasItem(DEFAULT_EHEART_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].eheartPlaceholder3").value(hasItem(DEFAULT_EHEART_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getEheart() throws Exception {
        // Initialize the database
        eheartRepository.saveAndFlush(eheart);

        // Get the eheart
        restEheartMockMvc.perform(get("/api/ehearts/{id}", eheart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eheart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.wechat").value(DEFAULT_WECHAT.toString()))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT.toString()))
            .andExpect(jsonPath("$.eheartPlaceholder1").value(DEFAULT_EHEART_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.eheartPlaceholder2").value(DEFAULT_EHEART_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.eheartPlaceholder3").value(DEFAULT_EHEART_PLACEHOLDER_3.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEheart() throws Exception {
        // Get the eheart
        restEheartMockMvc.perform(get("/api/ehearts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEheart() throws Exception {
        // Initialize the database
        eheartRepository.saveAndFlush(eheart);
        eheartSearchRepository.save(eheart);
        int databaseSizeBeforeUpdate = eheartRepository.findAll().size();

        // Update the eheart
        Eheart updatedEheart = eheartRepository.findOne(eheart.getId());
        updatedEheart
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .logo(UPDATED_LOGO)
                .email(UPDATED_EMAIL)
                .phone(UPDATED_PHONE)
                .fax(UPDATED_FAX)
                .address(UPDATED_ADDRESS)
                .wechat(UPDATED_WECHAT)
                .copyright(UPDATED_COPYRIGHT)
                .eheartPlaceholder1(UPDATED_EHEART_PLACEHOLDER_1)
                .eheartPlaceholder2(UPDATED_EHEART_PLACEHOLDER_2)
                .eheartPlaceholder3(UPDATED_EHEART_PLACEHOLDER_3)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        EheartDTO eheartDTO = eheartMapper.eheartToEheartDTO(updatedEheart);

        restEheartMockMvc.perform(put("/api/ehearts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eheartDTO)))
            .andExpect(status().isOk());

        // Validate the Eheart in the database
        List<Eheart> eheartList = eheartRepository.findAll();
        assertThat(eheartList).hasSize(databaseSizeBeforeUpdate);
        Eheart testEheart = eheartList.get(eheartList.size() - 1);
        assertThat(testEheart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEheart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEheart.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testEheart.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEheart.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEheart.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEheart.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEheart.getWechat()).isEqualTo(UPDATED_WECHAT);
        assertThat(testEheart.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testEheart.getEheartPlaceholder1()).isEqualTo(UPDATED_EHEART_PLACEHOLDER_1);
        assertThat(testEheart.getEheartPlaceholder2()).isEqualTo(UPDATED_EHEART_PLACEHOLDER_2);
        assertThat(testEheart.getEheartPlaceholder3()).isEqualTo(UPDATED_EHEART_PLACEHOLDER_3);
        assertThat(testEheart.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEheart.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEheart.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testEheart.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Eheart in ElasticSearch
        Eheart eheartEs = eheartSearchRepository.findOne(testEheart.getId());
        assertThat(eheartEs).isEqualToComparingFieldByField(testEheart);
    }

    @Test
    @Transactional
    public void updateNonExistingEheart() throws Exception {
        int databaseSizeBeforeUpdate = eheartRepository.findAll().size();

        // Create the Eheart
        EheartDTO eheartDTO = eheartMapper.eheartToEheartDTO(eheart);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEheartMockMvc.perform(put("/api/ehearts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eheartDTO)))
            .andExpect(status().isCreated());

        // Validate the Eheart in the database
        List<Eheart> eheartList = eheartRepository.findAll();
        assertThat(eheartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEheart() throws Exception {
        // Initialize the database
        eheartRepository.saveAndFlush(eheart);
        eheartSearchRepository.save(eheart);
        int databaseSizeBeforeDelete = eheartRepository.findAll().size();

        // Get the eheart
        restEheartMockMvc.perform(delete("/api/ehearts/{id}", eheart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean eheartExistsInEs = eheartSearchRepository.exists(eheart.getId());
        assertThat(eheartExistsInEs).isFalse();

        // Validate the database is empty
        List<Eheart> eheartList = eheartRepository.findAll();
        assertThat(eheartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEheart() throws Exception {
        // Initialize the database
        eheartRepository.saveAndFlush(eheart);
        eheartSearchRepository.save(eheart);

        // Search the eheart
        restEheartMockMvc.perform(get("/api/_search/ehearts?query=id:" + eheart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eheart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].wechat").value(hasItem(DEFAULT_WECHAT.toString())))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
            .andExpect(jsonPath("$.[*].eheartPlaceholder1").value(hasItem(DEFAULT_EHEART_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].eheartPlaceholder2").value(hasItem(DEFAULT_EHEART_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].eheartPlaceholder3").value(hasItem(DEFAULT_EHEART_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
