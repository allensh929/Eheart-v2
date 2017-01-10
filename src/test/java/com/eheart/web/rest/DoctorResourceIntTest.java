package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Doctor;
import com.eheart.repository.DoctorRepository;
import com.eheart.service.DoctorService;
import com.eheart.repository.search.DoctorSearchRepository;
import com.eheart.service.dto.DoctorDTO;
import com.eheart.service.mapper.DoctorMapper;

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
 * Test class for the DoctorResource REST controller.
 *
 * @see DoctorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class DoctorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORKING_AGE = 1;
    private static final Integer UPDATED_WORKING_AGE = 2;

    private static final String DEFAULT_DOMAIN_CUSTOM = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_CUSTOM = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_PLACEHOLDER_3 = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_PLACEHOLDER_3 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private DoctorRepository doctorRepository;

    @Inject
    private DoctorMapper doctorMapper;

    @Inject
    private DoctorService doctorService;

    @Inject
    private DoctorSearchRepository doctorSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorResource doctorResource = new DoctorResource();
        ReflectionTestUtils.setField(doctorResource, "doctorService", doctorService);
        this.restDoctorMockMvc = MockMvcBuilders.standaloneSetup(doctorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity(EntityManager em) {
        Doctor doctor = new Doctor()
                .name(DEFAULT_NAME)
                .age(DEFAULT_AGE)
                .experience(DEFAULT_EXPERIENCE)
                .workingAge(DEFAULT_WORKING_AGE)
                .domainCustom(DEFAULT_DOMAIN_CUSTOM)
                .phone(DEFAULT_PHONE)
                .email(DEFAULT_EMAIL)
                .description(DEFAULT_DESCRIPTION)
                .doctorPlaceholder1(DEFAULT_DOCTOR_PLACEHOLDER_1)
                .doctorPlaceholder2(DEFAULT_DOCTOR_PLACEHOLDER_2)
                .doctorPlaceholder3(DEFAULT_DOCTOR_PLACEHOLDER_3)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return doctor;
    }

    @Before
    public void initTest() {
        doctorSearchRepository.deleteAll();
        doctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(doctor);

        restDoctorMockMvc.perform(post("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctor.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDoctor.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testDoctor.getWorkingAge()).isEqualTo(DEFAULT_WORKING_AGE);
        assertThat(testDoctor.getDomainCustom()).isEqualTo(DEFAULT_DOMAIN_CUSTOM);
        assertThat(testDoctor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDoctor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDoctor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDoctor.getDoctorPlaceholder1()).isEqualTo(DEFAULT_DOCTOR_PLACEHOLDER_1);
        assertThat(testDoctor.getDoctorPlaceholder2()).isEqualTo(DEFAULT_DOCTOR_PLACEHOLDER_2);
        assertThat(testDoctor.getDoctorPlaceholder3()).isEqualTo(DEFAULT_DOCTOR_PLACEHOLDER_3);
        assertThat(testDoctor.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDoctor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDoctor.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testDoctor.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Doctor in ElasticSearch
        Doctor doctorEs = doctorSearchRepository.findOne(testDoctor.getId());
        assertThat(doctorEs).isEqualToComparingFieldByField(testDoctor);
    }

    @Test
    @Transactional
    public void createDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor with an existing ID
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(1L);
        DoctorDTO existingDoctorDTO = doctorMapper.doctorToDoctorDTO(existingDoctor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc.perform(post("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDoctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList
        restDoctorMockMvc.perform(get("/api/doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].workingAge").value(hasItem(DEFAULT_WORKING_AGE)))
            .andExpect(jsonPath("$.[*].domainCustom").value(hasItem(DEFAULT_DOMAIN_CUSTOM.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].doctorPlaceholder1").value(hasItem(DEFAULT_DOCTOR_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].doctorPlaceholder2").value(hasItem(DEFAULT_DOCTOR_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].doctorPlaceholder3").value(hasItem(DEFAULT_DOCTOR_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.workingAge").value(DEFAULT_WORKING_AGE))
            .andExpect(jsonPath("$.domainCustom").value(DEFAULT_DOMAIN_CUSTOM.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.doctorPlaceholder1").value(DEFAULT_DOCTOR_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.doctorPlaceholder2").value(DEFAULT_DOCTOR_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.doctorPlaceholder3").value(DEFAULT_DOCTOR_PLACEHOLDER_3.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);
        doctorSearchRepository.save(doctor);
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findOne(doctor.getId());
        updatedDoctor
                .name(UPDATED_NAME)
                .age(UPDATED_AGE)
                .experience(UPDATED_EXPERIENCE)
                .workingAge(UPDATED_WORKING_AGE)
                .domainCustom(UPDATED_DOMAIN_CUSTOM)
                .phone(UPDATED_PHONE)
                .email(UPDATED_EMAIL)
                .description(UPDATED_DESCRIPTION)
                .doctorPlaceholder1(UPDATED_DOCTOR_PLACEHOLDER_1)
                .doctorPlaceholder2(UPDATED_DOCTOR_PLACEHOLDER_2)
                .doctorPlaceholder3(UPDATED_DOCTOR_PLACEHOLDER_3)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(updatedDoctor);

        restDoctorMockMvc.perform(put("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDoctor.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testDoctor.getWorkingAge()).isEqualTo(UPDATED_WORKING_AGE);
        assertThat(testDoctor.getDomainCustom()).isEqualTo(UPDATED_DOMAIN_CUSTOM);
        assertThat(testDoctor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDoctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDoctor.getDoctorPlaceholder1()).isEqualTo(UPDATED_DOCTOR_PLACEHOLDER_1);
        assertThat(testDoctor.getDoctorPlaceholder2()).isEqualTo(UPDATED_DOCTOR_PLACEHOLDER_2);
        assertThat(testDoctor.getDoctorPlaceholder3()).isEqualTo(UPDATED_DOCTOR_PLACEHOLDER_3);
        assertThat(testDoctor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDoctor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDoctor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testDoctor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Doctor in ElasticSearch
        Doctor doctorEs = doctorSearchRepository.findOne(testDoctor.getId());
        assertThat(doctorEs).isEqualToComparingFieldByField(testDoctor);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(doctor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorMockMvc.perform(put("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);
        doctorSearchRepository.save(doctor);
        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Get the doctor
        restDoctorMockMvc.perform(delete("/api/doctors/{id}", doctor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean doctorExistsInEs = doctorSearchRepository.exists(doctor.getId());
        assertThat(doctorExistsInEs).isFalse();

        // Validate the database is empty
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);
        doctorSearchRepository.save(doctor);

        // Search the doctor
        restDoctorMockMvc.perform(get("/api/_search/doctors?query=id:" + doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].workingAge").value(hasItem(DEFAULT_WORKING_AGE)))
            .andExpect(jsonPath("$.[*].domainCustom").value(hasItem(DEFAULT_DOMAIN_CUSTOM.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].doctorPlaceholder1").value(hasItem(DEFAULT_DOCTOR_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].doctorPlaceholder2").value(hasItem(DEFAULT_DOCTOR_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].doctorPlaceholder3").value(hasItem(DEFAULT_DOCTOR_PLACEHOLDER_3.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
