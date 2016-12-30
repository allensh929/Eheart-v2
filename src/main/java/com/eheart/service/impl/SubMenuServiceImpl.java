package com.eheart.service.impl;

import com.eheart.service.SubMenuService;
import com.eheart.domain.SubMenu;
import com.eheart.repository.SubMenuRepository;
import com.eheart.repository.search.SubMenuSearchRepository;
import com.eheart.service.dto.SubMenuDTO;
import com.eheart.service.mapper.SubMenuMapper;
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
 * Service Implementation for managing SubMenu.
 */
@Service
@Transactional
public class SubMenuServiceImpl implements SubMenuService{

    private final Logger log = LoggerFactory.getLogger(SubMenuServiceImpl.class);
    
    @Inject
    private SubMenuRepository subMenuRepository;

    @Inject
    private SubMenuMapper subMenuMapper;

    @Inject
    private SubMenuSearchRepository subMenuSearchRepository;

    /**
     * Save a subMenu.
     *
     * @param subMenuDTO the entity to save
     * @return the persisted entity
     */
    public SubMenuDTO save(SubMenuDTO subMenuDTO) {
        log.debug("Request to save SubMenu : {}", subMenuDTO);
        SubMenu subMenu = subMenuMapper.subMenuDTOToSubMenu(subMenuDTO);
        subMenu = subMenuRepository.save(subMenu);
        SubMenuDTO result = subMenuMapper.subMenuToSubMenuDTO(subMenu);
        subMenuSearchRepository.save(subMenu);
        return result;
    }

    /**
     *  Get all the subMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SubMenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubMenus");
        Page<SubMenu> result = subMenuRepository.findAll(pageable);
        return result.map(subMenu -> subMenuMapper.subMenuToSubMenuDTO(subMenu));
    }

    /**
     *  Get one subMenu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SubMenuDTO findOne(Long id) {
        log.debug("Request to get SubMenu : {}", id);
        SubMenu subMenu = subMenuRepository.findOne(id);
        SubMenuDTO subMenuDTO = subMenuMapper.subMenuToSubMenuDTO(subMenu);
        return subMenuDTO;
    }

    /**
     *  Delete the  subMenu by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubMenu : {}", id);
        subMenuRepository.delete(id);
        subMenuSearchRepository.delete(id);
    }

    /**
     * Search for the subMenu corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SubMenuDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SubMenus for query {}", query);
        Page<SubMenu> result = subMenuSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(subMenu -> subMenuMapper.subMenuToSubMenuDTO(subMenu));
    }
}
