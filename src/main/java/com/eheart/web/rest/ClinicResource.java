package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.ClinicService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.ClinicDTO;

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
 * REST controller for managing Clinic.
 */
@RestController
@RequestMapping("/api")
public class ClinicResource {

    private final Logger log = LoggerFactory.getLogger(ClinicResource.class);
        
    @Inject
    private ClinicService clinicService;

    /**
     * POST  /clinics : Create a new clinic.
     *
     * @param clinicDTO the clinicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinicDTO, or with status 400 (Bad Request) if the clinic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinics")
    @Timed
    public ResponseEntity<ClinicDTO> createClinic(@RequestBody ClinicDTO clinicDTO) throws URISyntaxException {
        log.debug("REST request to save Clinic : {}", clinicDTO);
        if (clinicDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clinic", "idexists", "A new clinic cannot already have an ID")).body(null);
        }
        ClinicDTO result = clinicService.save(clinicDTO);
        return ResponseEntity.created(new URI("/api/clinics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clinic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinics : Updates an existing clinic.
     *
     * @param clinicDTO the clinicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinicDTO,
     * or with status 400 (Bad Request) if the clinicDTO is not valid,
     * or with status 500 (Internal Server Error) if the clinicDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinics")
    @Timed
    public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO clinicDTO) throws URISyntaxException {
        log.debug("REST request to update Clinic : {}", clinicDTO);
        if (clinicDTO.getId() == null) {
            return createClinic(clinicDTO);
        }
        ClinicDTO result = clinicService.save(clinicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clinic", clinicDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinics : get all the clinics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clinics in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/clinics")
    @Timed
    public ResponseEntity<List<ClinicDTO>> getAllClinics(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Clinics");
        Page<ClinicDTO> page = clinicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clinics/:id : get the "id" clinic.
     *
     * @param id the id of the clinicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clinics/{id}")
    @Timed
    public ResponseEntity<ClinicDTO> getClinic(@PathVariable Long id) {
        log.debug("REST request to get Clinic : {}", id);
        ClinicDTO clinicDTO = clinicService.findOne(id);
        return Optional.ofNullable(clinicDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clinics/:id : delete the "id" clinic.
     *
     * @param id the id of the clinicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinics/{id}")
    @Timed
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        log.debug("REST request to delete Clinic : {}", id);
        clinicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clinic", id.toString())).build();
    }

    /**
     * SEARCH  /_search/clinics?query=:query : search for the clinic corresponding
     * to the query.
     *
     * @param query the query of the clinic search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/clinics")
    @Timed
    public ResponseEntity<List<ClinicDTO>> searchClinics(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Clinics for query {}", query);
        Page<ClinicDTO> page = clinicService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clinics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
