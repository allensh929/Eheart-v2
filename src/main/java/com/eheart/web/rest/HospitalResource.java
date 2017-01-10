package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.HospitalService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.HospitalDTO;

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
 * REST controller for managing Hospital.
 */
@RestController
@RequestMapping("/api")
public class HospitalResource {

    private final Logger log = LoggerFactory.getLogger(HospitalResource.class);
        
    @Inject
    private HospitalService hospitalService;

    /**
     * POST  /hospitals : Create a new hospital.
     *
     * @param hospitalDTO the hospitalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hospitalDTO, or with status 400 (Bad Request) if the hospital has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hospitals")
    @Timed
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody HospitalDTO hospitalDTO) throws URISyntaxException {
        log.debug("REST request to save Hospital : {}", hospitalDTO);
        if (hospitalDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hospital", "idexists", "A new hospital cannot already have an ID")).body(null);
        }
        HospitalDTO result = hospitalService.save(hospitalDTO);
        return ResponseEntity.created(new URI("/api/hospitals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hospital", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hospitals : Updates an existing hospital.
     *
     * @param hospitalDTO the hospitalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hospitalDTO,
     * or with status 400 (Bad Request) if the hospitalDTO is not valid,
     * or with status 500 (Internal Server Error) if the hospitalDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hospitals")
    @Timed
    public ResponseEntity<HospitalDTO> updateHospital(@RequestBody HospitalDTO hospitalDTO) throws URISyntaxException {
        log.debug("REST request to update Hospital : {}", hospitalDTO);
        if (hospitalDTO.getId() == null) {
            return createHospital(hospitalDTO);
        }
        HospitalDTO result = hospitalService.save(hospitalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hospital", hospitalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hospitals : get all the hospitals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hospitals in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/hospitals")
    @Timed
    public ResponseEntity<List<HospitalDTO>> getAllHospitals(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Hospitals");
        Page<HospitalDTO> page = hospitalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hospitals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hospitals/:id : get the "id" hospital.
     *
     * @param id the id of the hospitalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hospitalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hospitals/{id}")
    @Timed
    public ResponseEntity<HospitalDTO> getHospital(@PathVariable Long id) {
        log.debug("REST request to get Hospital : {}", id);
        HospitalDTO hospitalDTO = hospitalService.findOne(id);
        return Optional.ofNullable(hospitalDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hospitals/:id : delete the "id" hospital.
     *
     * @param id the id of the hospitalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hospitals/{id}")
    @Timed
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        log.debug("REST request to delete Hospital : {}", id);
        hospitalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hospital", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hospitals?query=:query : search for the hospital corresponding
     * to the query.
     *
     * @param query the query of the hospital search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/hospitals")
    @Timed
    public ResponseEntity<List<HospitalDTO>> searchHospitals(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Hospitals for query {}", query);
        Page<HospitalDTO> page = hospitalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hospitals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
