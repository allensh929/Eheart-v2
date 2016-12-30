package com.eheart.service.impl;

import com.eheart.service.ThirdMenuService;
import com.eheart.domain.ThirdMenu;
import com.eheart.repository.ThirdMenuRepository;
import com.eheart.repository.search.ThirdMenuSearchRepository;
import com.eheart.service.dto.ThirdMenuDTO;
import com.eheart.service.mapper.ThirdMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ThirdMenu.
 */
@Service
@Transactional
public class ThirdMenuServiceImpl implements ThirdMenuService{

    private final Logger log = LoggerFactory.getLogger(ThirdMenuServiceImpl.class);
    
    @Inject
    private ThirdMenuRepository thirdMenuRepository;

    @Inject
    private ThirdMenuMapper thirdMenuMapper;

    @Inject
    private ThirdMenuSearchRepository thirdMenuSearchRepository;

    /**
     * Save a thirdMenu.
     *
     * @param thirdMenuDTO the entity to save
     * @return the persisted entity
     */
    public ThirdMenuDTO save(ThirdMenuDTO thirdMenuDTO) {
        log.debug("Request to save ThirdMenu : {}", thirdMenuDTO);
        ThirdMenu thirdMenu = thirdMenuMapper.thirdMenuDTOToThirdMenu(thirdMenuDTO);
        thirdMenu = thirdMenuRepository.save(thirdMenu);
        ThirdMenuDTO result = thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu);
        thirdMenuSearchRepository.save(thirdMenu);
        return result;
    }

    /**
     *  Get all the thirdMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ThirdMenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThirdMenus");
        Page<ThirdMenu> result = thirdMenuRepository.findAll(pageable);
        return result.map(thirdMenu -> thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu));
    }

    /**
     *  Get one thirdMenu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ThirdMenuDTO findOne(Long id) {
        log.debug("Request to get ThirdMenu : {}", id);
        ThirdMenu thirdMenu = thirdMenuRepository.findOne(id);
        ThirdMenuDTO thirdMenuDTO = thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu);
        return thirdMenuDTO;
    }

    /**
     *  Delete the  thirdMenu by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ThirdMenu : {}", id);
        thirdMenuRepository.delete(id);
        thirdMenuSearchRepository.delete(id);
    }

    /**
     * Search for the thirdMenu corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ThirdMenuDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ThirdMenus for query {}", query);
        Page<ThirdMenu> result = thirdMenuSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(thirdMenu -> thirdMenuMapper.thirdMenuToThirdMenuDTO(thirdMenu));
    }
}
