package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.ThirdMenu;
import com.eheart.repository.ThirdMenuRepository;
import com.eheart.service.ThirdMenuService;
import com.eheart.repository.search.ThirdMenuSearchRepository;
import com.eheart.service.dto.ThirdMenuDTO;
import com.eheart.service.mapper.ThirdMenuMapper;

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
 * Test class for the ThirdMenuResource REST controller.
 *
 * @see ThirdMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class ThirdMenuResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private ThirdMenuRepository thirdMenuRepository;

    @Inject
    private ThirdMenuMapper thirdMenuMapper;

    @Inject
    private ThirdMenuService thirdMenuService;

    @Inject
    private ThirdMenuSearchRepository thirdMenuSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restThirdMenuMockMvc;

    private ThirdMenu thirdMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThirdMenuResource thirdMenuResource = new ThirdMenuResource();
        ReflectionTestUtils.setField(thirdMenuResource, "thirdMenuService", thirdMenuService);
        this.restThirdMenuMockMvc = MockMvcBuilders.standaloneSetup(thirdMenuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdMenu createEntity(EntityManager em) {
        ThirdMenu thirdMenu = new ThirdMenu()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .seq(DEFAULT_SEQ)
                .link(DEFAULT_LINK)
                .content(DEFAULT_CONTENT)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return thirdMenu;
    }

    @Before
    public void initTest() {
        thirdMenuSearchRepository.deleteAll();
        thirdMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createThirdMenu() throws Exception {
        int databaseSizeBeforeCreate = thirdMenuRepository.findAll().size();

        // Create the ThirdMenu
        ThirdMenuDTO thirdMenuDTO = thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu);

        restThirdMenuMockMvc.perform(post("/api/third-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the ThirdMenu in the database
        List<ThirdMenu> thirdMenuList = thirdMenuRepository.findAll();
        assertThat(thirdMenuList).hasSize(databaseSizeBeforeCreate + 1);
        ThirdMenu testThirdMenu = thirdMenuList.get(thirdMenuList.size() - 1);
        assertThat(testThirdMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThirdMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testThirdMenu.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testThirdMenu.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testThirdMenu.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testThirdMenu.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testThirdMenu.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testThirdMenu.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testThirdMenu.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the ThirdMenu in ElasticSearch
        ThirdMenu thirdMenuEs = thirdMenuSearchRepository.findOne(testThirdMenu.getId());
        assertThat(thirdMenuEs).isEqualToComparingFieldByField(testThirdMenu);
    }

    @Test
    @Transactional
    public void createThirdMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thirdMenuRepository.findAll().size();

        // Create the ThirdMenu with an existing ID
        ThirdMenu existingThirdMenu = new ThirdMenu();
        existingThirdMenu.setId(1L);
        ThirdMenuDTO existingThirdMenuDTO = thirdMenuMapper.thirdMenuToThirdMenuDTO(existingThirdMenu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdMenuMockMvc.perform(post("/api/third-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingThirdMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ThirdMenu> thirdMenuList = thirdMenuRepository.findAll();
        assertThat(thirdMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thirdMenuRepository.findAll().size();
        // set the field null
        thirdMenu.setName(null);

        // Create the ThirdMenu, which fails.
        ThirdMenuDTO thirdMenuDTO = thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu);

        restThirdMenuMockMvc.perform(post("/api/third-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdMenuDTO)))
            .andExpect(status().isBadRequest());

        List<ThirdMenu> thirdMenuList = thirdMenuRepository.findAll();
        assertThat(thirdMenuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThirdMenus() throws Exception {
        // Initialize the database
        thirdMenuRepository.saveAndFlush(thirdMenu);

        // Get all the thirdMenuList
        restThirdMenuMockMvc.perform(get("/api/third-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getThirdMenu() throws Exception {
        // Initialize the database
        thirdMenuRepository.saveAndFlush(thirdMenu);

        // Get the thirdMenu
        restThirdMenuMockMvc.perform(get("/api/third-menus/{id}", thirdMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thirdMenu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThirdMenu() throws Exception {
        // Get the thirdMenu
        restThirdMenuMockMvc.perform(get("/api/third-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdMenu() throws Exception {
        // Initialize the database
        thirdMenuRepository.saveAndFlush(thirdMenu);
        thirdMenuSearchRepository.save(thirdMenu);
        int databaseSizeBeforeUpdate = thirdMenuRepository.findAll().size();

        // Update the thirdMenu
        ThirdMenu updatedThirdMenu = thirdMenuRepository.findOne(thirdMenu.getId());
        updatedThirdMenu
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .seq(UPDATED_SEQ)
                .link(UPDATED_LINK)
                .content(UPDATED_CONTENT)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ThirdMenuDTO thirdMenuDTO = thirdMenuMapper.thirdMenuToThirdMenuDTO(updatedThirdMenu);

        restThirdMenuMockMvc.perform(put("/api/third-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdMenuDTO)))
            .andExpect(status().isOk());

        // Validate the ThirdMenu in the database
        List<ThirdMenu> thirdMenuList = thirdMenuRepository.findAll();
        assertThat(thirdMenuList).hasSize(databaseSizeBeforeUpdate);
        ThirdMenu testThirdMenu = thirdMenuList.get(thirdMenuList.size() - 1);
        assertThat(testThirdMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThirdMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testThirdMenu.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testThirdMenu.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testThirdMenu.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testThirdMenu.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testThirdMenu.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testThirdMenu.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testThirdMenu.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the ThirdMenu in ElasticSearch
        ThirdMenu thirdMenuEs = thirdMenuSearchRepository.findOne(testThirdMenu.getId());
        assertThat(thirdMenuEs).isEqualToComparingFieldByField(testThirdMenu);
    }

    @Test
    @Transactional
    public void updateNonExistingThirdMenu() throws Exception {
        int databaseSizeBeforeUpdate = thirdMenuRepository.findAll().size();

        // Create the ThirdMenu
        ThirdMenuDTO thirdMenuDTO = thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThirdMenuMockMvc.perform(put("/api/third-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the ThirdMenu in the database
        List<ThirdMenu> thirdMenuList = thirdMenuRepository.findAll();
        assertThat(thirdMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThirdMenu() throws Exception {
        // Initialize the database
        thirdMenuRepository.saveAndFlush(thirdMenu);
        thirdMenuSearchRepository.save(thirdMenu);
        int databaseSizeBeforeDelete = thirdMenuRepository.findAll().size();

        // Get the thirdMenu
        restThirdMenuMockMvc.perform(delete("/api/third-menus/{id}", thirdMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean thirdMenuExistsInEs = thirdMenuSearchRepository.exists(thirdMenu.getId());
        assertThat(thirdMenuExistsInEs).isFalse();

        // Validate the database is empty
        List<ThirdMenu> thirdMenuList = thirdMenuRepository.findAll();
        assertThat(thirdMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchThirdMenu() throws Exception {
        // Initialize the database
        thirdMenuRepository.saveAndFlush(thirdMenu);
        thirdMenuSearchRepository.save(thirdMenu);

        // Search the thirdMenu
        restThirdMenuMockMvc.perform(get("/api/_search/third-menus?query=id:" + thirdMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
