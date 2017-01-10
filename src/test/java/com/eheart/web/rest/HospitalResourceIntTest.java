package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Hospital;
import com.eheart.repository.HospitalRepository;
import com.eheart.service.HospitalService;
import com.eheart.repository.search.HospitalSearchRepository;
import com.eheart.service.dto.HospitalDTO;
import com.eheart.service.mapper.HospitalMapper;

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
 * Test class for the HospitalResource REST controller.
 *
 * @see HospitalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class HospitalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_PLACEHOLDER_3 = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_PLACEHOLDER_3 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private HospitalRepository hospitalRepository;

    @Inject
    private HospitalMapper hospitalMapper;

    @Inject
    private HospitalService hospitalService;

    @Inject
    private HospitalSearchRepository hospitalSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHospitalMockMvc;

    private Hospital hospital;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HospitalResource hospitalResource = new HospitalResource();
        ReflectionTestUtils.setField(hospitalResource, "hospitalService", hospitalService);
        this.restHospitalMockMvc = MockMvcBuilders.standaloneSetup(hospitalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createEntity(EntityManager em) {
        Hospital hospital = new Hospital()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .hospitalPlaceholder1(DEFAULT_HOSPITAL_PLACEHOLDER_1)
                .hospitalPlaceholder2(DEFAULT_HOSPITAL_PLACEHOLDER_2)
                .hospitalPlaceholder3(DEFAULT_HOSPITAL_PLACEHOLDER_3)
                ;
        return hospital;
    }

    @Before
    public void initTest() {
        hospitalSearchRepository.deleteAll();
        hospital = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospital() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.hospitalToHospitalDTO(hospital);

        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isCreated());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate + 1);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHospital.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHospital.getHospitalPlaceholder1()).isEqualTo(DEFAULT_HOSPITAL_PLACEHOLDER_1);
        assertThat(testHospital.getHospitalPlaceholder2()).isEqualTo(DEFAULT_HOSPITAL_PLACEHOLDER_2);
        assertThat(testHospital.getHospitalPlaceholder3()).isEqualTo(DEFAULT_HOSPITAL_PLACEHOLDER_3);
        assertThat(testHospital.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testHospital.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHospital.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testHospital.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Hospital in ElasticSearch
        Hospital hospitalEs = hospitalSearchRepository.findOne(testHospital.getId());
        assertThat(hospitalEs).isEqualToComparingFieldByField(testHospital);
    }

    @Test
    @Transactional
    public void createHospitalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();

        // Create the Hospital with an existing ID
        Hospital existingHospital = new Hospital();
        existingHospital.setId(1L);
        HospitalDTO existingHospitalDTO = hospitalMapper.hospitalToHospitalDTO(existingHospital);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHospitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHospitals() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList
        restHospitalMockMvc.perform(get("/api/hospitals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].hospitalPlaceholder1").value(hasItem(DEFAULT_HOSPITAL_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].hospitalPlaceholder2").value(hasItem(DEFAULT_HOSPITAL_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].hospitalPlaceholder3").value(hasItem(DEFAULT_HOSPITAL_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get the hospital
        restHospitalMockMvc.perform(get("/api/hospitals/{id}", hospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hospital.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.hospitalPlaceholder1").value(DEFAULT_HOSPITAL_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.hospitalPlaceholder2").value(DEFAULT_HOSPITAL_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.hospitalPlaceholder3").value(DEFAULT_HOSPITAL_PLACEHOLDER_3.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHospital() throws Exception {
        // Get the hospital
        restHospitalMockMvc.perform(get("/api/hospitals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        hospitalSearchRepository.save(hospital);
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Update the hospital
        Hospital updatedHospital = hospitalRepository.findOne(hospital.getId());
        updatedHospital
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .hospitalPlaceholder1(UPDATED_HOSPITAL_PLACEHOLDER_1)
                .hospitalPlaceholder2(UPDATED_HOSPITAL_PLACEHOLDER_2)
                .hospitalPlaceholder3(UPDATED_HOSPITAL_PLACEHOLDER_3)
        ;
        HospitalDTO hospitalDTO = hospitalMapper.hospitalToHospitalDTO(updatedHospital);

        restHospitalMockMvc.perform(put("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHospital.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHospital.getHospitalPlaceholder1()).isEqualTo(UPDATED_HOSPITAL_PLACEHOLDER_1);
        assertThat(testHospital.getHospitalPlaceholder2()).isEqualTo(UPDATED_HOSPITAL_PLACEHOLDER_2);
        assertThat(testHospital.getHospitalPlaceholder3()).isEqualTo(UPDATED_HOSPITAL_PLACEHOLDER_3);
        assertThat(testHospital.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testHospital.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHospital.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testHospital.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Hospital in ElasticSearch
        Hospital hospitalEs = hospitalSearchRepository.findOne(testHospital.getId());
        assertThat(hospitalEs).isEqualToComparingFieldByField(testHospital);
    }

    @Test
    @Transactional
    public void updateNonExistingHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.hospitalToHospitalDTO(hospital);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHospitalMockMvc.perform(put("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isCreated());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        hospitalSearchRepository.save(hospital);
        int databaseSizeBeforeDelete = hospitalRepository.findAll().size();

        // Get the hospital
        restHospitalMockMvc.perform(delete("/api/hospitals/{id}", hospital.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean hospitalExistsInEs = hospitalSearchRepository.exists(hospital.getId());
        assertThat(hospitalExistsInEs).isFalse();

        // Validate the database is empty
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        hospitalSearchRepository.save(hospital);

        // Search the hospital
        restHospitalMockMvc.perform(get("/api/_search/hospitals?query=id:" + hospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].hospitalPlaceholder1").value(hasItem(DEFAULT_HOSPITAL_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].hospitalPlaceholder2").value(hasItem(DEFAULT_HOSPITAL_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].hospitalPlaceholder3").value(hasItem(DEFAULT_HOSPITAL_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
