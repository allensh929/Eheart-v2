package com.eheart.service.impl;

import com.eheart.service.EheartService;
import com.eheart.domain.Eheart;
import com.eheart.repository.EheartRepository;
import com.eheart.repository.search.EheartSearchRepository;
import com.eheart.service.dto.EheartDTO;
import com.eheart.service.mapper.EheartMapper;
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
 * Service Implementation for managing Eheart.
 */
@Service
@Transactional
public class EheartServiceImpl implements EheartService{

    private final Logger log = LoggerFactory.getLogger(EheartServiceImpl.class);
    
    @Inject
    private EheartRepository eheartRepository;

    @Inject
    private EheartMapper eheartMapper;

    @Inject
    private EheartSearchRepository eheartSearchRepository;

    /**
     * Save a eheart.
     *
     * @param eheartDTO the entity to save
     * @return the persisted entity
     */
    public EheartDTO save(EheartDTO eheartDTO) {
        log.debug("Request to save Eheart : {}", eheartDTO);
        Eheart eheart = eheartMapper.eheartDTOToEheart(eheartDTO);
        eheart = eheartRepository.save(eheart);
        EheartDTO result = eheartMapper.eheartToEheartDTO(eheart);
        eheartSearchRepository.save(eheart);
        return result;
    }

    /**
     *  Get all the ehearts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EheartDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ehearts");
        Page<Eheart> result = eheartRepository.findAll(pageable);
        return result.map(eheart -> eheartMapper.eheartToEheartDTO(eheart));
    }

    /**
     *  Get one eheart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EheartDTO findOne(Long id) {
        log.debug("Request to get Eheart : {}", id);
        Eheart eheart = eheartRepository.findOne(id);
        EheartDTO eheartDTO = eheartMapper.eheartToEheartDTO(eheart);
        return eheartDTO;
    }

    /**
     *  Delete the  eheart by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Eheart : {}", id);
        eheartRepository.delete(id);
        eheartSearchRepository.delete(id);
    }

    /**
     * Search for the eheart corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EheartDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ehearts for query {}", query);
        Page<Eheart> result = eheartSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(eheart -> eheartMapper.eheartToEheartDTO(eheart));
    }
}
