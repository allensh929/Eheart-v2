package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Clinic;
import com.eheart.repository.ClinicRepository;
import com.eheart.service.ClinicService;
import com.eheart.repository.search.ClinicSearchRepository;
import com.eheart.service.dto.ClinicDTO;
import com.eheart.service.mapper.ClinicMapper;

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
 * Test class for the ClinicResource REST controller.
 *
 * @see ClinicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class ClinicResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private ClinicRepository clinicRepository;

    @Inject
    private ClinicMapper clinicMapper;

    @Inject
    private ClinicService clinicService;

    @Inject
    private ClinicSearchRepository clinicSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClinicMockMvc;

    private Clinic clinic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicResource clinicResource = new ClinicResource();
        ReflectionTestUtils.setField(clinicResource, "clinicService", clinicService);
        this.restClinicMockMvc = MockMvcBuilders.standaloneSetup(clinicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinic createEntity(EntityManager em) {
        Clinic clinic = new Clinic()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .img(DEFAULT_IMG)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return clinic;
    }

    @Before
    public void initTest() {
        clinicSearchRepository.deleteAll();
        clinic = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinic() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic
        ClinicDTO clinicDTO = clinicMapper.clinicToClinicDTO(clinic);

        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicDTO)))
            .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate + 1);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClinic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClinic.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testClinic.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClinic.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClinic.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testClinic.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Clinic in ElasticSearch
        Clinic clinicEs = clinicSearchRepository.findOne(testClinic.getId());
        assertThat(clinicEs).isEqualToComparingFieldByField(testClinic);
    }

    @Test
    @Transactional
    public void createClinicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic with an existing ID
        Clinic existingClinic = new Clinic();
        existingClinic.setId(1L);
        ClinicDTO existingClinicDTO = clinicMapper.clinicToClinicDTO(existingClinic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClinicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClinics() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get all the clinicList
        restClinicMockMvc.perform(get("/api/clinics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", clinic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinic() throws Exception {
        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);
        clinicSearchRepository.save(clinic);
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic
        Clinic updatedClinic = clinicRepository.findOne(clinic.getId());
        updatedClinic
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .img(UPDATED_IMG)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ClinicDTO clinicDTO = clinicMapper.clinicToClinicDTO(updatedClinic);

        restClinicMockMvc.perform(put("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicDTO)))
            .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClinic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClinic.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testClinic.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClinic.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClinic.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testClinic.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Clinic in ElasticSearch
        Clinic clinicEs = clinicSearchRepository.findOne(testClinic.getId());
        assertThat(clinicEs).isEqualToComparingFieldByField(testClinic);
    }

    @Test
    @Transactional
    public void updateNonExistingClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Create the Clinic
        ClinicDTO clinicDTO = clinicMapper.clinicToClinicDTO(clinic);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClinicMockMvc.perform(put("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicDTO)))
            .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);
        clinicSearchRepository.save(clinic);
        int databaseSizeBeforeDelete = clinicRepository.findAll().size();

        // Get the clinic
        restClinicMockMvc.perform(delete("/api/clinics/{id}", clinic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean clinicExistsInEs = clinicSearchRepository.exists(clinic.getId());
        assertThat(clinicExistsInEs).isFalse();

        // Validate the database is empty
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);
        clinicSearchRepository.save(clinic);

        // Search the clinic
        restClinicMockMvc.perform(get("/api/_search/clinics?query=id:" + clinic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
