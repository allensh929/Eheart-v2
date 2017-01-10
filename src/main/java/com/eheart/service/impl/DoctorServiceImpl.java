package com.eheart.service.impl;

import com.eheart.service.DoctorService;
import com.eheart.domain.Doctor;
import com.eheart.repository.DoctorRepository;
import com.eheart.repository.search.DoctorSearchRepository;
import com.eheart.service.dto.DoctorDTO;
import com.eheart.service.mapper.DoctorMapper;
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
 * Service Implementation for managing Doctor.
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{

    private final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);
    
    @Inject
    private DoctorRepository doctorRepository;

    @Inject
    private DoctorMapper doctorMapper;

    @Inject
    private DoctorSearchRepository doctorSearchRepository;

    /**
     * Save a doctor.
     *
     * @param doctorDTO the entity to save
     * @return the persisted entity
     */
    public DoctorDTO save(DoctorDTO doctorDTO) {
        log.debug("Request to save Doctor : {}", doctorDTO);
        Doctor doctor = doctorMapper.doctorDTOToDoctor(doctorDTO);
        doctor = doctorRepository.save(doctor);
        DoctorDTO result = doctorMapper.doctorToDoctorDTO(doctor);
        doctorSearchRepository.save(doctor);
        return result;
    }

    /**
     *  Get all the doctors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DoctorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        Page<Doctor> result = doctorRepository.findAll(pageable);
        return result.map(doctor -> doctorMapper.doctorToDoctorDTO(doctor));
    }

    /**
     *  Get one doctor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DoctorDTO findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        Doctor doctor = doctorRepository.findOneWithEagerRelationships(id);
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(doctor);
        return doctorDTO;
    }

    /**
     *  Delete the  doctor by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.delete(id);
        doctorSearchRepository.delete(id);
    }

    /**
     * Search for the doctor corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DoctorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Doctors for query {}", query);
        Page<Doctor> result = doctorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(doctor -> doctorMapper.doctorToDoctorDTO(doctor));
    }
}
