package com.eheart.service;

import com.eheart.service.dto.TitleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Title.
 */
public interface TitleService {

    /**
     * Save a title.
     *
     * @param titleDTO the entity to save
     * @return the persisted entity
     */
    TitleDTO save(TitleDTO titleDTO);

    /**
     *  Get all the titles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TitleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" title.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TitleDTO findOne(Long id);

    /**
     *  Delete the "id" title.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the title corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TitleDTO> search(String query, Pageable pageable);
}
