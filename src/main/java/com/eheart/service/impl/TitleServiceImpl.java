package com.eheart.service.impl;

import com.eheart.service.TitleService;
import com.eheart.domain.Title;
import com.eheart.repository.TitleRepository;
import com.eheart.repository.search.TitleSearchRepository;
import com.eheart.service.dto.TitleDTO;
import com.eheart.service.mapper.TitleMapper;
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
 * Service Implementation for managing Title.
 */
@Service
@Transactional
public class TitleServiceImpl implements TitleService{

    private final Logger log = LoggerFactory.getLogger(TitleServiceImpl.class);
    
    @Inject
    private TitleRepository titleRepository;

    @Inject
    private TitleMapper titleMapper;

    @Inject
    private TitleSearchRepository titleSearchRepository;

    /**
     * Save a title.
     *
     * @param titleDTO the entity to save
     * @return the persisted entity
     */
    public TitleDTO save(TitleDTO titleDTO) {
        log.debug("Request to save Title : {}", titleDTO);
        Title title = titleMapper.titleDTOToTitle(titleDTO);
        title = titleRepository.save(title);
        TitleDTO result = titleMapper.titleToTitleDTO(title);
        titleSearchRepository.save(title);
        return result;
    }

    /**
     *  Get all the titles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TitleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Titles");
        Page<Title> result = titleRepository.findAll(pageable);
        return result.map(title -> titleMapper.titleToTitleDTO(title));
    }

    /**
     *  Get one title by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TitleDTO findOne(Long id) {
        log.debug("Request to get Title : {}", id);
        Title title = titleRepository.findOne(id);
        TitleDTO titleDTO = titleMapper.titleToTitleDTO(title);
        return titleDTO;
    }

    /**
     *  Delete the  title by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Title : {}", id);
        titleRepository.delete(id);
        titleSearchRepository.delete(id);
    }

    /**
     * Search for the title corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TitleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Titles for query {}", query);
        Page<Title> result = titleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(title -> titleMapper.titleToTitleDTO(title));
    }
}
