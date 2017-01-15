package com.eheart.service;

import com.eheart.service.dto.ClinicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Clinic.
 */
public interface ClinicService {

    /**
     * Save a clinic.
     *
     * @param clinicDTO the entity to save
     * @return the persisted entity
     */
    ClinicDTO save(ClinicDTO clinicDTO);

    /**
     *  Get all the clinics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClinicDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" clinic.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClinicDTO findOne(Long id);

    /**
     *  Delete the "id" clinic.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the clinic corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClinicDTO> search(String query, Pageable pageable);
}
