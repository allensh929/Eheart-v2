package com.eheart.service.impl;

import com.eheart.service.DepartmentService;
import com.eheart.domain.Department;
import com.eheart.repository.DepartmentRepository;
import com.eheart.repository.search.DepartmentSearchRepository;
import com.eheart.service.dto.DepartmentDTO;
import com.eheart.service.mapper.DepartmentMapper;
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
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    
    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentMapper departmentMapper;

    @Inject
    private DepartmentSearchRepository departmentSearchRepository;

    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save
     * @return the persisted entity
     */
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.departmentDTOToDepartment(departmentDTO);
        department = departmentRepository.save(department);
        DepartmentDTO result = departmentMapper.departmentToDepartmentDTO(department);
        departmentSearchRepository.save(department);
        return result;
    }

    /**
     *  Get all the departments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        Page<Department> result = departmentRepository.findAll(pageable);
        return result.map(department -> departmentMapper.departmentToDepartmentDTO(department));
    }

    /**
     *  Get one department by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DepartmentDTO findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        Department department = departmentRepository.findOneWithEagerRelationships(id);
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);
        return departmentDTO;
    }

    /**
     *  Delete the  department by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.delete(id);
        departmentSearchRepository.delete(id);
    }

    /**
     * Search for the department corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Departments for query {}", query);
        Page<Department> result = departmentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(department -> departmentMapper.departmentToDepartmentDTO(department));
    }
}
