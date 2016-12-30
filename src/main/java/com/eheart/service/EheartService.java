package com.eheart.service;

import com.eheart.service.dto.EheartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Eheart.
 */
public interface EheartService {

    /**
     * Save a eheart.
     *
     * @param eheartDTO the entity to save
     * @return the persisted entity
     */
    EheartDTO save(EheartDTO eheartDTO);

    /**
     *  Get all the ehearts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EheartDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" eheart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EheartDTO findOne(Long id);

    /**
     *  Delete the "id" eheart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the eheart corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EheartDTO> search(String query, Pageable pageable);
}
