package com.eheart.web.rest;

import com.eheart.EheartApp;

import com.eheart.domain.SubMenu;
import com.eheart.repository.SubMenuRepository;
import com.eheart.service.SubMenuService;
import com.eheart.repository.search.SubMenuSearchRepository;
import com.eheart.service.dto.SubMenuDTO;
import com.eheart.service.mapper.SubMenuMapper;

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
 * Test class for the SubMenuResource REST controller.
 *
 * @see SubMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EheartApp.class)
public class SubMenuResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private SubMenuRepository subMenuRepository;

    @Inject
    private SubMenuMapper subMenuMapper;

    @Inject
    private SubMenuService subMenuService;

    @Inject
    private SubMenuSearchRepository subMenuSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSubMenuMockMvc;

    private SubMenu subMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubMenuResource subMenuResource = new SubMenuResource();
        ReflectionTestUtils.setField(subMenuResource, "subMenuService", subMenuService);
        this.restSubMenuMockMvc = MockMvcBuilders.standaloneSetup(subMenuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubMenu createEntity(EntityManager em) {
        SubMenu subMenu = new SubMenu()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .seq(DEFAULT_SEQ)
                .link(DEFAULT_LINK)
                .createdDate(DEFAULT_CREATED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return subMenu;
    }

    @Before
    public void initTest() {
        subMenuSearchRepository.deleteAll();
        subMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubMenu() throws Exception {
        int databaseSizeBeforeCreate = subMenuRepository.findAll().size();

        // Create the SubMenu
        SubMenuDTO subMenuDTO = subMenuMapper.subMenuToSubMenuDTO(subMenu);

        restSubMenuMockMvc.perform(post("/api/sub-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the SubMenu in the database
        List<SubMenu> subMenuList = subMenuRepository.findAll();
        assertThat(subMenuList).hasSize(databaseSizeBeforeCreate + 1);
        SubMenu testSubMenu = subMenuList.get(subMenuList.size() - 1);
        assertThat(testSubMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubMenu.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testSubMenu.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testSubMenu.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSubMenu.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSubMenu.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testSubMenu.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the SubMenu in ElasticSearch
        SubMenu subMenuEs = subMenuSearchRepository.findOne(testSubMenu.getId());
        assertThat(subMenuEs).isEqualToComparingFieldByField(testSubMenu);
    }

    @Test
    @Transactional
    public void createSubMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subMenuRepository.findAll().size();

        // Create the SubMenu with an existing ID
        SubMenu existingSubMenu = new SubMenu();
        existingSubMenu.setId(1L);
        SubMenuDTO existingSubMenuDTO = subMenuMapper.subMenuToSubMenuDTO(existingSubMenu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubMenuMockMvc.perform(post("/api/sub-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSubMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SubMenu> subMenuList = subMenuRepository.findAll();
        assertThat(subMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subMenuRepository.findAll().size();
        // set the field null
        subMenu.setName(null);

        // Create the SubMenu, which fails.
        SubMenuDTO subMenuDTO = subMenuMapper.subMenuToSubMenuDTO(subMenu);

        restSubMenuMockMvc.perform(post("/api/sub-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subMenuDTO)))
            .andExpect(status().isBadRequest());

        List<SubMenu> subMenuList = subMenuRepository.findAll();
        assertThat(subMenuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubMenus() throws Exception {
        // Initialize the database
        subMenuRepository.saveAndFlush(subMenu);

        // Get all the subMenuList
        restSubMenuMockMvc.perform(get("/api/sub-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getSubMenu() throws Exception {
        // Initialize the database
        subMenuRepository.saveAndFlush(subMenu);

        // Get the subMenu
        restSubMenuMockMvc.perform(get("/api/sub-menus/{id}", subMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subMenu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubMenu() throws Exception {
        // Get the subMenu
        restSubMenuMockMvc.perform(get("/api/sub-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubMenu() throws Exception {
        // Initialize the database
        subMenuRepository.saveAndFlush(subMenu);
        subMenuSearchRepository.save(subMenu);
        int databaseSizeBeforeUpdate = subMenuRepository.findAll().size();

        // Update the subMenu
        SubMenu updatedSubMenu = subMenuRepository.findOne(subMenu.getId());
        updatedSubMenu
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .seq(UPDATED_SEQ)
                .link(UPDATED_LINK)
                .createdDate(UPDATED_CREATED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        SubMenuDTO subMenuDTO = subMenuMapper.subMenuToSubMenuDTO(updatedSubMenu);

        restSubMenuMockMvc.perform(put("/api/sub-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subMenuDTO)))
            .andExpect(status().isOk());

        // Validate the SubMenu in the database
        List<SubMenu> subMenuList = subMenuRepository.findAll();
        assertThat(subMenuList).hasSize(databaseSizeBeforeUpdate);
        SubMenu testSubMenu = subMenuList.get(subMenuList.size() - 1);
        assertThat(testSubMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubMenu.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testSubMenu.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testSubMenu.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSubMenu.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSubMenu.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSubMenu.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the SubMenu in ElasticSearch
        SubMenu subMenuEs = subMenuSearchRepository.findOne(testSubMenu.getId());
        assertThat(subMenuEs).isEqualToComparingFieldByField(testSubMenu);
    }

    @Test
    @Transactional
    public void updateNonExistingSubMenu() throws Exception {
        int databaseSizeBeforeUpdate = subMenuRepository.findAll().size();

        // Create the SubMenu
        SubMenuDTO subMenuDTO = subMenuMapper.subMenuToSubMenuDTO(subMenu);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubMenuMockMvc.perform(put("/api/sub-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the SubMenu in the database
        List<SubMenu> subMenuList = subMenuRepository.findAll();
        assertThat(subMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubMenu() throws Exception {
        // Initialize the database
        subMenuRepository.saveAndFlush(subMenu);
        subMenuSearchRepository.save(subMenu);
        int databaseSizeBeforeDelete = subMenuRepository.findAll().size();

        // Get the subMenu
        restSubMenuMockMvc.perform(delete("/api/sub-menus/{id}", subMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean subMenuExistsInEs = subMenuSearchRepository.exists(subMenu.getId());
        assertThat(subMenuExistsInEs).isFalse();

        // Validate the database is empty
        List<SubMenu> subMenuList = subMenuRepository.findAll();
        assertThat(subMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSubMenu() throws Exception {
        // Initialize the database
        subMenuRepository.saveAndFlush(subMenu);
        subMenuSearchRepository.save(subMenu);

        // Search the subMenu
        restSubMenuMockMvc.perform(get("/api/_search/sub-menus?query=id:" + subMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }
}
