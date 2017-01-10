package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.TitleService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.TitleDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Title.
 */
@RestController
@RequestMapping("/api")
public class TitleResource {

    private final Logger log = LoggerFactory.getLogger(TitleResource.class);
        
    @Inject
    private TitleService titleService;

    /**
     * POST  /titles : Create a new title.
     *
     * @param titleDTO the titleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new titleDTO, or with status 400 (Bad Request) if the title has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/titles")
    @Timed
    public ResponseEntity<TitleDTO> createTitle(@RequestBody TitleDTO titleDTO) throws URISyntaxException {
        log.debug("REST request to save Title : {}", titleDTO);
        if (titleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("title", "idexists", "A new title cannot already have an ID")).body(null);
        }
        TitleDTO result = titleService.save(titleDTO);
        return ResponseEntity.created(new URI("/api/titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("title", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /titles : Updates an existing title.
     *
     * @param titleDTO the titleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated titleDTO,
     * or with status 400 (Bad Request) if the titleDTO is not valid,
     * or with status 500 (Internal Server Error) if the titleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/titles")
    @Timed
    public ResponseEntity<TitleDTO> updateTitle(@RequestBody TitleDTO titleDTO) throws URISyntaxException {
        log.debug("REST request to update Title : {}", titleDTO);
        if (titleDTO.getId() == null) {
            return createTitle(titleDTO);
        }
        TitleDTO result = titleService.save(titleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("title", titleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /titles : get all the titles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of titles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/titles")
    @Timed
    public ResponseEntity<List<TitleDTO>> getAllTitles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Titles");
        Page<TitleDTO> page = titleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/titles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /titles/:id : get the "id" title.
     *
     * @param id the id of the titleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the titleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/titles/{id}")
    @Timed
    public ResponseEntity<TitleDTO> getTitle(@PathVariable Long id) {
        log.debug("REST request to get Title : {}", id);
        TitleDTO titleDTO = titleService.findOne(id);
        return Optional.ofNullable(titleDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /titles/:id : delete the "id" title.
     *
     * @param id the id of the titleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/titles/{id}")
    @Timed
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) {
        log.debug("REST request to delete Title : {}", id);
        titleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("title", id.toString())).build();
    }

    /**
     * SEARCH  /_search/titles?query=:query : search for the title corresponding
     * to the query.
     *
     * @param query the query of the title search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/titles")
    @Timed
    public ResponseEntity<List<TitleDTO>> searchTitles(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Titles for query {}", query);
        Page<TitleDTO> page = titleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/titles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
