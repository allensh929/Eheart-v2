package com.eheart.service;

import com.eheart.service.dto.HospitalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Hospital.
 */
public interface HospitalService {

    /**
     * Save a hospital.
     *
     * @param hospitalDTO the entity to save
     * @return the persisted entity
     */
    HospitalDTO save(HospitalDTO hospitalDTO);

    /**
     *  Get all the hospitals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HospitalDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" hospital.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HospitalDTO findOne(Long id);

    /**
     *  Delete the "id" hospital.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hospital corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HospitalDTO> search(String query, Pageable pageable);
}
