package com.eheart.service.impl;

import com.eheart.service.MenuService;
import com.eheart.domain.Menu;
import com.eheart.repository.MenuRepository;
import com.eheart.repository.search.MenuSearchRepository;
import com.eheart.service.dto.MenuDTO;
import com.eheart.service.mapper.MenuMapper;
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
 * Service Implementation for managing Menu.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService{

    private final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
    
    @Inject
    private MenuRepository menuRepository;

    @Inject
    private MenuMapper menuMapper;

    @Inject
    private MenuSearchRepository menuSearchRepository;

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save
     * @return the persisted entity
     */
    public MenuDTO save(MenuDTO menuDTO) {
        log.debug("Request to save Menu : {}", menuDTO);
        Menu menu = menuMapper.menuDTOToMenu(menuDTO);
        menu = menuRepository.save(menu);
        MenuDTO result = menuMapper.menuToMenuDTO(menu);
        menuSearchRepository.save(menu);
        return result;
    }

    /**
     *  Get all the menus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Menus");
        Page<Menu> result = menuRepository.findAll(pageable);
        return result.map(menu -> menuMapper.menuToMenuDTO(menu));
    }

    /**
     *  Get one menu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MenuDTO findOne(Long id) {
        log.debug("Request to get Menu : {}", id);
        Menu menu = menuRepository.findOne(id);
        MenuDTO menuDTO = menuMapper.menuToMenuDTO(menu);
        return menuDTO;
    }

    /**
     *  Delete the  menu by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.delete(id);
        menuSearchRepository.delete(id);
    }

    /**
     * Search for the menu corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MenuDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Menus for query {}", query);
        Page<Menu> result = menuSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(menu -> menuMapper.menuToMenuDTO(menu));
    }
}
