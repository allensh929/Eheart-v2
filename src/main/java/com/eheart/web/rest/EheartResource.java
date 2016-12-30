package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.EheartService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.EheartDTO;

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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Eheart.
 */
@RestController
@RequestMapping("/api")
public class EheartResource {

    private final Logger log = LoggerFactory.getLogger(EheartResource.class);
        
    @Inject
    private EheartService eheartService;

    /**
     * POST  /ehearts : Create a new eheart.
     *
     * @param eheartDTO the eheartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eheartDTO, or with status 400 (Bad Request) if the eheart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ehearts")
    @Timed
    public ResponseEntity<EheartDTO> createEheart(@Valid @RequestBody EheartDTO eheartDTO) throws URISyntaxException {
        log.debug("REST request to save Eheart : {}", eheartDTO);
        if (eheartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("eheart", "idexists", "A new eheart cannot already have an ID")).body(null);
        }
        EheartDTO result = eheartService.save(eheartDTO);
        return ResponseEntity.created(new URI("/api/ehearts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eheart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ehearts : Updates an existing eheart.
     *
     * @param eheartDTO the eheartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eheartDTO,
     * or with status 400 (Bad Request) if the eheartDTO is not valid,
     * or with status 500 (Internal Server Error) if the eheartDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ehearts")
    @Timed
    public ResponseEntity<EheartDTO> updateEheart(@Valid @RequestBody EheartDTO eheartDTO) throws URISyntaxException {
        log.debug("REST request to update Eheart : {}", eheartDTO);
        if (eheartDTO.getId() == null) {
            return createEheart(eheartDTO);
        }
        EheartDTO result = eheartService.save(eheartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eheart", eheartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ehearts : get all the ehearts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ehearts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/ehearts")
    @Timed
    public ResponseEntity<List<EheartDTO>> getAllEhearts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ehearts");
        Page<EheartDTO> page = eheartService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ehearts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ehearts/:id : get the "id" eheart.
     *
     * @param id the id of the eheartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eheartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ehearts/{id}")
    @Timed
    public ResponseEntity<EheartDTO> getEheart(@PathVariable Long id) {
        log.debug("REST request to get Eheart : {}", id);
        EheartDTO eheartDTO = eheartService.findOne(id);
        return Optional.ofNullable(eheartDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ehearts/:id : delete the "id" eheart.
     *
     * @param id the id of the eheartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ehearts/{id}")
    @Timed
    public ResponseEntity<Void> deleteEheart(@PathVariable Long id) {
        log.debug("REST request to delete Eheart : {}", id);
        eheartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eheart", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ehearts?query=:query : search for the eheart corresponding
     * to the query.
     *
     * @param query the query of the eheart search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/ehearts")
    @Timed
    public ResponseEntity<List<EheartDTO>> searchEhearts(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Ehearts for query {}", query);
        Page<EheartDTO> page = eheartService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ehearts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
