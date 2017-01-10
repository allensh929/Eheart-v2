package com.eheart.service.impl;

import com.eheart.service.HospitalService;
import com.eheart.domain.Hospital;
import com.eheart.repository.HospitalRepository;
import com.eheart.repository.search.HospitalSearchRepository;
import com.eheart.service.dto.HospitalDTO;
import com.eheart.service.mapper.HospitalMapper;
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
 * Service Implementation for managing Hospital.
 */
@Service
@Transactional
public class HospitalServiceImpl implements HospitalService{

    private final Logger log = LoggerFactory.getLogger(HospitalServiceImpl.class);
    
    @Inject
    private HospitalRepository hospitalRepository;

    @Inject
    private HospitalMapper hospitalMapper;

    @Inject
    private HospitalSearchRepository hospitalSearchRepository;

    /**
     * Save a hospital.
     *
     * @param hospitalDTO the entity to save
     * @return the persisted entity
     */
    public HospitalDTO save(HospitalDTO hospitalDTO) {
        log.debug("Request to save Hospital : {}", hospitalDTO);
        Hospital hospital = hospitalMapper.hospitalDTOToHospital(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        HospitalDTO result = hospitalMapper.hospitalToHospitalDTO(hospital);
        hospitalSearchRepository.save(hospital);
        return result;
    }

    /**
     *  Get all the hospitals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HospitalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hospitals");
        Page<Hospital> result = hospitalRepository.findAll(pageable);
        return result.map(hospital -> hospitalMapper.hospitalToHospitalDTO(hospital));
    }

    /**
     *  Get one hospital by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HospitalDTO findOne(Long id) {
        log.debug("Request to get Hospital : {}", id);
        Hospital hospital = hospitalRepository.findOne(id);
        HospitalDTO hospitalDTO = hospitalMapper.hospitalToHospitalDTO(hospital);
        return hospitalDTO;
    }

    /**
     *  Delete the  hospital by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Hospital : {}", id);
        hospitalRepository.delete(id);
        hospitalSearchRepository.delete(id);
    }

    /**
     * Search for the hospital corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HospitalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Hospitals for query {}", query);
        Page<Hospital> result = hospitalSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(hospital -> hospitalMapper.hospitalToHospitalDTO(hospital));
    }
}
