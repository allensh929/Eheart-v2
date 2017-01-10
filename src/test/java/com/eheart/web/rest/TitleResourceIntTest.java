package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.Title;
import com.eheart.repository.TitleRepository;
import com.eheart.service.TitleService;
import com.eheart.repository.search.TitleSearchRepository;
import com.eheart.service.dto.TitleDTO;
import com.eheart.service.mapper.TitleMapper;

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
 * Test class for the TitleResource REST controller.
 *
 * @see TitleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class TitleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_PLACEHOLDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_PLACEHOLDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_PLACEHOLDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_PLACEHOLDER_2 = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private TitleRepository titleRepository;

    @Inject
    private TitleMapper titleMapper;

    @Inject
    private TitleService titleService;

    @Inject
    private TitleSearchRepository titleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTitleMockMvc;

    private Title title;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TitleResource titleResource = new TitleResource();
        ReflectionTestUtils.setField(titleResource, "titleService", titleService);
        this.restTitleMockMvc = MockMvcBuilders.standaloneSetup(titleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Title createEntity(EntityManager em) {
        Title title = new Title()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .titlePlaceholder1(DEFAULT_TITLE_PLACEHOLDER_1)
                .titlePlaceholder2(DEFAULT_TITLE_PLACEHOLDER_2)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return title;
    }

    @Before
    public void initTest() {
        titleSearchRepository.deleteAll();
        title = createEntity(em);
    }

    @Test
    @Transactional
    public void createTitle() throws Exception {
        int databaseSizeBeforeCreate = titleRepository.findAll().size();

        // Create the Title
        TitleDTO titleDTO = titleMapper.titleToTitleDTO(title);

        restTitleMockMvc.perform(post("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titleDTO)))
            .andExpect(status().isCreated());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeCreate + 1);
        Title testTitle = titleList.get(titleList.size() - 1);
        assertThat(testTitle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTitle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTitle.getTitlePlaceholder1()).isEqualTo(DEFAULT_TITLE_PLACEHOLDER_1);
        assertThat(testTitle.getTitlePlaceholder2()).isEqualTo(DEFAULT_TITLE_PLACEHOLDER_2);
        assertThat(testTitle.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTitle.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTitle.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testTitle.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the Title in ElasticSearch
        Title titleEs = titleSearchRepository.findOne(testTitle.getId());
        assertThat(titleEs).isEqualToComparingFieldByField(testTitle);
    }

    @Test
    @Transactional
    public void createTitleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = titleRepository.findAll().size();

        // Create the Title with an existing ID
        Title existingTitle = new Title();
        existingTitle.setId(1L);
        TitleDTO existingTitleDTO = titleMapper.titleToTitleDTO(existingTitle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTitleMockMvc.perform(post("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTitleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTitles() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

        // Get all the titleList
        restTitleMockMvc.perform(get("/api/titles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(title.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].titlePlaceholder1").value(hasItem(DEFAULT_TITLE_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].titlePlaceholder2").value(hasItem(DEFAULT_TITLE_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(title.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.titlePlaceholder1").value(DEFAULT_TITLE_PLACEHOLDER_1.toString()))
            .andExpect(jsonPath("$.titlePlaceholder2").value(DEFAULT_TITLE_PLACEHOLDER_2.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTitle() throws Exception {
        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);
        titleSearchRepository.save(title);
        int databaseSizeBeforeUpdate = titleRepository.findAll().size();

        // Update the title
        Title updatedTitle = titleRepository.findOne(title.getId());
        updatedTitle
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .titlePlaceholder1(UPDATED_TITLE_PLACEHOLDER_1)
                .titlePlaceholder2(UPDATED_TITLE_PLACEHOLDER_2)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TitleDTO titleDTO = titleMapper.titleToTitleDTO(updatedTitle);

        restTitleMockMvc.perform(put("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titleDTO)))
            .andExpect(status().isOk());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeUpdate);
        Title testTitle = titleList.get(titleList.size() - 1);
        assertThat(testTitle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTitle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTitle.getTitlePlaceholder1()).isEqualTo(UPDATED_TITLE_PLACEHOLDER_1);
        assertThat(testTitle.getTitlePlaceholder2()).isEqualTo(UPDATED_TITLE_PLACEHOLDER_2);
        assertThat(testTitle.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTitle.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTitle.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testTitle.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the Title in ElasticSearch
        Title titleEs = titleSearchRepository.findOne(testTitle.getId());
        assertThat(titleEs).isEqualToComparingFieldByField(testTitle);
    }

    @Test
    @Transactional
    public void updateNonExistingTitle() throws Exception {
        int databaseSizeBeforeUpdate = titleRepository.findAll().size();

        // Create the Title
        TitleDTO titleDTO = titleMapper.titleToTitleDTO(title);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTitleMockMvc.perform(put("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titleDTO)))
            .andExpect(status().isCreated());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);
        titleSearchRepository.save(title);
        int databaseSizeBeforeDelete = titleRepository.findAll().size();

        // Get the title
        restTitleMockMvc.perform(delete("/api/titles/{id}", title.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean titleExistsInEs = titleSearchRepository.exists(title.getId());
        assertThat(titleExistsInEs).isFalse();

        // Validate the database is empty
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);
        titleSearchRepository.save(title);

        // Search the title
        restTitleMockMvc.perform(get("/api/_search/titles?query=id:" + title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(title.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].titlePlaceholder1").value(hasItem(DEFAULT_TITLE_PLACEHOLDER_1.toString())))
            .andExpect(jsonPath("$.[*].titlePlaceholder2").value(hasItem(DEFAULT_TITLE_PLACEHOLDER_2.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
