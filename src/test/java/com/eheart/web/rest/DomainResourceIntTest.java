package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Domain;
import com.eheart.repository.DomainRepository;
import com.eheart.service.DomainService;
import com.eheart.repository.search.DomainSearchRepository;
import com.eheart.service.dto.DomainDTO;
import com.eheart.service.mapper.DomainMapper;

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
 * Test class for the DomainResource REST controller.
 *
 * @see DomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class DomainResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_PLACEHOLDER_3 = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_PLACEHOLDER_3 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private DomainRepository domainRepository;

    @Inject
    private DomainMapper domainMapper;

    @Inject
    private DomainService domainService;

    @Inject
    private DomainSearchRepository domainSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDomainMockMvc;

    private Domain domain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DomainResource domainResource = new DomainResource();
        ReflectionTestUtils.setField(domainResource, "domainService", domainService);
        this.restDomainMockMvc = MockMvcBuilders.standaloneSetup(domainResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domain createEntity(EntityManager em) {
        Domain domain = new Domain()
                .name(DEFAULT_NAME)
                .domainPlaceholder1(DEFAULT_DOMAIN_PLACEHOLDER_1)
                .domainPlaceholder2(DEFAULT_DOMAIN_PLACEHOLDER_2)
                .domainPlaceholder3(DEFAULT_DOMAIN_PLACEHOLDER_3)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return domain;
    }

    @Before
    public void initTest() {
        domainSearchRepository.deleteAll();
        domain = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomain() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // Create the Domain
        DomainDTO domainDTO = domainMapper.domainToDomainDTO(domain);

        restDomainMockMvc.perform(post("/api/domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isCreated());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeCreate + 1);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDomain.getDomainPlaceholder1()).isEqualTo(DEFAULT_DOMAIN_PLACEHOLDER_1);
        assertThat(testDomain.getDomainPlaceholder2()).isEqualTo(DEFAULT_DOMAIN_PLACEHOLDER_2);
        assertThat(testDomain.getDomainPlaceholder3()).isEqualTo(DEFAULT_DOMAIN_PLACEHOLDER_3);
        assertThat(testDomain.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDomain.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDomain.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testDomain.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Domain in ElasticSearch
        Domain domainEs = domainSearchRepository.findOne(testDomain.getId());
        assertThat(domainEs).isEqualToComparingFieldByField(testDomain);
    }

    @Test
    @Transactional
    public void createDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // Create the Domain with an existing ID
        Domain existingDomain = new Domain();
        existingDomain.setId(1L);
        DomainDTO existingDomainDTO = domainMapper.domainToDomainDTO(existingDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomainMockMvc.perform(post("/api/domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDomains() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get all the domainList
        restDomainMockMvc.perform(get("/api/domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domain.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].domainPlaceholder1").value(hasItem(DEFAULT_DOMAIN_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].domainPlaceholder2").value(hasItem(DEFAULT_DOMAIN_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].domainPlaceholder3").value(hasItem(DEFAULT_DOMAIN_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get the domain
        restDomainMockMvc.perform(get("/api/domains/{id}", domain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domain.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.domainPlaceholder1").value(DEFAULT_DOMAIN_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.domainPlaceholder2").value(DEFAULT_DOMAIN_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.domainPlaceholder3").value(DEFAULT_DOMAIN_PLACEHOLDER_3.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDomain() throws Exception {
        // Get the domain
        restDomainMockMvc.perform(get("/api/domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);
        domainSearchRepository.save(domain);
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain
        Domain updatedDomain = domainRepository.findOne(domain.getId());
        updatedDomain
                .name(UPDATED_NAME)
                .domainPlaceholder1(UPDATED_DOMAIN_PLACEHOLDER_1)
                .domainPlaceholder2(UPDATED_DOMAIN_PLACEHOLDER_2)
                .domainPlaceholder3(UPDATED_DOMAIN_PLACEHOLDER_3)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        DomainDTO domainDTO = domainMapper.domainToDomainDTO(updatedDomain);

        restDomainMockMvc.perform(put("/api/domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDomain.getDomainPlaceholder1()).isEqualTo(UPDATED_DOMAIN_PLACEHOLDER_1);
        assertThat(testDomain.getDomainPlaceholder2()).isEqualTo(UPDATED_DOMAIN_PLACEHOLDER_2);
        assertThat(testDomain.getDomainPlaceholder3()).isEqualTo(UPDATED_DOMAIN_PLACEHOLDER_3);
        assertThat(testDomain.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDomain.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDomain.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testDomain.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Domain in ElasticSearch
        Domain domainEs = domainSearchRepository.findOne(testDomain.getId());
        assertThat(domainEs).isEqualToComparingFieldByField(testDomain);
    }

    @Test
    @Transactional
    public void updateNonExistingDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Create the Domain
        DomainDTO domainDTO = domainMapper.domainToDomainDTO(domain);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDomainMockMvc.perform(put("/api/domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isCreated());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);
        domainSearchRepository.save(domain);
        int databaseSizeBeforeDelete = domainRepository.findAll().size();

        // Get the domain
        restDomainMockMvc.perform(delete("/api/domains/{id}", domain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean domainExistsInEs = domainSearchRepository.exists(domain.getId());
        assertThat(domainExistsInEs).isFalse();

        // Validate the database is empty
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);
        domainSearchRepository.save(domain);

        // Search the domain
        restDomainMockMvc.perform(get("/api/_search/domains?query=id:" + domain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domain.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].domainPlaceholder1").value(hasItem(DEFAULT_DOMAIN_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].domainPlaceholder2").value(hasItem(DEFAULT_DOMAIN_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].domainPlaceholder3").value(hasItem(DEFAULT_DOMAIN_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
