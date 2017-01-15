package com.eheart.service.impl;

import com.eheart.service.ClinicService;
import com.eheart.domain.Clinic;
import com.eheart.repository.ClinicRepository;
import com.eheart.repository.search.ClinicSearchRepository;
import com.eheart.service.dto.ClinicDTO;
import com.eheart.service.mapper.ClinicMapper;
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
 * Service Implementation for managing Clinic.
 */
@Service
@Transactional
public class ClinicServiceImpl implements ClinicService{

    private final Logger log = LoggerFactory.getLogger(ClinicServiceImpl.class);
    
    @Inject
    private ClinicRepository clinicRepository;

    @Inject
    private ClinicMapper clinicMapper;

    @Inject
    private ClinicSearchRepository clinicSearchRepository;

    /**
     * Save a clinic.
     *
     * @param clinicDTO the entity to save
     * @return the persisted entity
     */
    public ClinicDTO save(ClinicDTO clinicDTO) {
        log.debug("Request to save Clinic : {}", clinicDTO);
        Clinic clinic = clinicMapper.clinicDTOToClinic(clinicDTO);
        clinic = clinicRepository.save(clinic);
        ClinicDTO result = clinicMapper.clinicToClinicDTO(clinic);
        clinicSearchRepository.save(clinic);
        return result;
    }

    /**
     *  Get all the clinics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ClinicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clinics");
        Page<Clinic> result = clinicRepository.findAll(pageable);
        return result.map(clinic -> clinicMapper.clinicToClinicDTO(clinic));
    }

    /**
     *  Get one clinic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClinicDTO findOne(Long id) {
        log.debug("Request to get Clinic : {}", id);
        Clinic clinic = clinicRepository.findOneWithEagerRelationships(id);
        ClinicDTO clinicDTO = clinicMapper.clinicToClinicDTO(clinic);
        return clinicDTO;
    }

    /**
     *  Delete the  clinic by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Clinic : {}", id);
        clinicRepository.delete(id);
        clinicSearchRepository.delete(id);
    }

    /**
     * Search for the clinic corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClinicDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clinics for query {}", query);
        Page<Clinic> result = clinicSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(clinic -> clinicMapper.clinicToClinicDTO(clinic));
    }
}
