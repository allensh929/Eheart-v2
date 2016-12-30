package com.eheart.service;

import com.eheart.service.dto.ThirdMenuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ThirdMenu.
 */
public interface ThirdMenuService {

    /**
     * Save a thirdMenu.
     *
     * @param thirdMenuDTO the entity to save
     * @return the persisted entity
     */
    ThirdMenuDTO save(ThirdMenuDTO thirdMenuDTO);

    /**
     *  Get all the thirdMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ThirdMenuDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" thirdMenu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ThirdMenuDTO findOne(Long id);

    /**
     *  Delete the "id" thirdMenu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the thirdMenu corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ThirdMenuDTO> search(String query, Pageable pageable);
}
