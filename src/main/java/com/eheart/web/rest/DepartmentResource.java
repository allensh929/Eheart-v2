package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.DepartmentService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.DepartmentDTO;

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
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);
        
    @Inject
    private DepartmentService departmentService;

    /**
     * POST  /departments : Create a new department.
     *
     * @param departmentDTO the departmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departmentDTO, or with status 400 (Bad Request) if the department has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departments")
    @Timed
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to save Department : {}", departmentDTO);
        if (departmentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("department", "idexists", "A new department cannot already have an ID")).body(null);
        }
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("department", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departments : Updates an existing department.
     *
     * @param departmentDTO the departmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departmentDTO,
     * or with status 400 (Bad Request) if the departmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the departmentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departments")
    @Timed
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to update Department : {}", departmentDTO);
        if (departmentDTO.getId() == null) {
            return createDepartment(departmentDTO);
        }
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("department", departmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departments : get all the departments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of departments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/departments")
    @Timed
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Departments");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /departments/:id : get the "id" department.
     *
     * @param id the id of the departmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/departments/{id}")
    @Timed
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        DepartmentDTO departmentDTO = departmentService.findOne(id);
        return Optional.ofNullable(departmentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /departments/:id : delete the "id" department.
     *
     * @param id the id of the departmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("department", id.toString())).build();
    }

    /**
     * SEARCH  /_search/departments?query=:query : search for the department corresponding
     * to the query.
     *
     * @param query the query of the department search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/departments")
    @Timed
    public ResponseEntity<List<DepartmentDTO>> searchDepartments(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Departments for query {}", query);
        Page<DepartmentDTO> page = departmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
