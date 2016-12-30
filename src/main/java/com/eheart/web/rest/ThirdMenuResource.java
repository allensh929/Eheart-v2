package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.ThirdMenuService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.ThirdMenuDTO;

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
 * REST controller for managing ThirdMenu.
 */
@RestController
@RequestMapping("/api")
public class ThirdMenuResource {

    private final Logger log = LoggerFactory.getLogger(ThirdMenuResource.class);
        
    @Inject
    private ThirdMenuService thirdMenuService;

    /**
     * POST  /third-menus : Create a new thirdMenu.
     *
     * @param thirdMenuDTO the thirdMenuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thirdMenuDTO, or with status 400 (Bad Request) if the thirdMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/third-menus")
    @Timed
    public ResponseEntity<ThirdMenuDTO> createThirdMenu(@Valid @RequestBody ThirdMenuDTO thirdMenuDTO) throws URISyntaxException {
        log.debug("REST request to save ThirdMenu : {}", thirdMenuDTO);
        if (thirdMenuDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thirdMenu", "idexists", "A new thirdMenu cannot already have an ID")).body(null);
        }
        ThirdMenuDTO result = thirdMenuService.save(thirdMenuDTO);
        return ResponseEntity.created(new URI("/api/third-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thirdMenu", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /third-menus : Updates an existing thirdMenu.
     *
     * @param thirdMenuDTO the thirdMenuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thirdMenuDTO,
     * or with status 400 (Bad Request) if the thirdMenuDTO is not valid,
     * or with status 500 (Internal Server Error) if the thirdMenuDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/third-menus")
    @Timed
    public ResponseEntity<ThirdMenuDTO> updateThirdMenu(@Valid @RequestBody ThirdMenuDTO thirdMenuDTO) throws URISyntaxException {
        log.debug("REST request to update ThirdMenu : {}", thirdMenuDTO);
        if (thirdMenuDTO.getId() == null) {
            return createThirdMenu(thirdMenuDTO);
        }
        ThirdMenuDTO result = thirdMenuService.save(thirdMenuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thirdMenu", thirdMenuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /third-menus : get all the thirdMenus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thirdMenus in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/third-menus")
    @Timed
    public ResponseEntity<List<ThirdMenuDTO>> getAllThirdMenus(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThirdMenus");
        Page<ThirdMenuDTO> page = thirdMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/third-menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /third-menus/:id : get the "id" thirdMenu.
     *
     * @param id the id of the thirdMenuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thirdMenuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/third-menus/{id}")
    @Timed
    public ResponseEntity<ThirdMenuDTO> getThirdMenu(@PathVariable Long id) {
        log.debug("REST request to get ThirdMenu : {}", id);
        ThirdMenuDTO thirdMenuDTO = thirdMenuService.findOne(id);
        return Optional.ofNullable(thirdMenuDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /third-menus/:id : delete the "id" thirdMenu.
     *
     * @param id the id of the thirdMenuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/third-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteThirdMenu(@PathVariable Long id) {
        log.debug("REST request to delete ThirdMenu : {}", id);
        thirdMenuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thirdMenu", id.toString())).build();
    }

    /**
     * SEARCH  /_search/third-menus?query=:query : search for the thirdMenu corresponding
     * to the query.
     *
     * @param query the query of the thirdMenu search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/third-menus")
    @Timed
    public ResponseEntity<List<ThirdMenuDTO>> searchThirdMenus(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ThirdMenus for query {}", query);
        Page<ThirdMenuDTO> page = thirdMenuService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/third-menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
