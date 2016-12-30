package com.eheart.service;

import com.eheart.service.dto.SubMenuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing SubMenu.
 */
public interface SubMenuService {

    /**
     * Save a subMenu.
     *
     * @param subMenuDTO the entity to save
     * @return the persisted entity
     */
    SubMenuDTO save(SubMenuDTO subMenuDTO);

    /**
     *  Get all the subMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SubMenuDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" subMenu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SubMenuDTO findOne(Long id);

    /**
     *  Delete the "id" subMenu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the subMenu corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SubMenuDTO> search(String query, Pageable pageable);
}
