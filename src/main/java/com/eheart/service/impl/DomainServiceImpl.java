package com.eheart.service.impl;

import com.eheart.service.DomainService;
import com.eheart.domain.Domain;
import com.eheart.repository.DomainRepository;
import com.eheart.repository.search.DomainSearchRepository;
import com.eheart.service.dto.DomainDTO;
import com.eheart.service.mapper.DomainMapper;
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
 * Service Implementation for managing Domain.
 */
@Service
@Transactional
public class DomainServiceImpl implements DomainService{

    private final Logger log = LoggerFactory.getLogger(DomainServiceImpl.class);
    
    @Inject
    private DomainRepository domainRepository;

    @Inject
    private DomainMapper domainMapper;

    @Inject
    private DomainSearchRepository domainSearchRepository;

    /**
     * Save a domain.
     *
     * @param domainDTO the entity to save
     * @return the persisted entity
     */
    public DomainDTO save(DomainDTO domainDTO) {
        log.debug("Request to save Domain : {}", domainDTO);
        Domain domain = domainMapper.domainDTOToDomain(domainDTO);
        domain = domainRepository.save(domain);
        DomainDTO result = domainMapper.domainToDomainDTO(domain);
        domainSearchRepository.save(domain);
        return result;
    }

    /**
     *  Get all the domains.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Domains");
        Page<Domain> result = domainRepository.findAll(pageable);
        return result.map(domain -> domainMapper.domainToDomainDTO(domain));
    }

    /**
     *  Get one domain by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DomainDTO findOne(Long id) {
        log.debug("Request to get Domain : {}", id);
        Domain domain = domainRepository.findOne(id);
        DomainDTO domainDTO = domainMapper.domainToDomainDTO(domain);
        return domainDTO;
    }

    /**
     *  Delete the  domain by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Domain : {}", id);
        domainRepository.delete(id);
        domainSearchRepository.delete(id);
    }

    /**
     * Search for the domain corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DomainDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Domains for query {}", query);
        Page<Domain> result = domainSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(domain -> domainMapper.domainToDomainDTO(domain));
    }
}
