package com.eheart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eheart.service.SubMenuService;
import com.eheart.web.rest.util.HeaderUtil;
import com.eheart.web.rest.util.PaginationUtil;
import com.eheart.service.dto.SubMenuDTO;

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
 * REST controller for managing SubMenu.
 */
@RestController
@RequestMapping("/api")
public class SubMenuResource {

    private final Logger log = LoggerFactory.getLogger(SubMenuResource.class);
        
    @Inject
    private SubMenuService subMenuService;

    /**
     * POST  /sub-menus : Create a new subMenu.
     *
     * @param subMenuDTO the subMenuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subMenuDTO, or with status 400 (Bad Request) if the subMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-menus")
    @Timed
    public ResponseEntity<SubMenuDTO> createSubMenu(@Valid @RequestBody SubMenuDTO subMenuDTO) throws URISyntaxException {
        log.debug("REST request to save SubMenu : {}", subMenuDTO);
        if (subMenuDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subMenu", "idexists", "A new subMenu cannot already have an ID")).body(null);
        }
        SubMenuDTO result = subMenuService.save(subMenuDTO);
        return ResponseEntity.created(new URI("/api/sub-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subMenu", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sub-menus : Updates an existing subMenu.
     *
     * @param subMenuDTO the subMenuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subMenuDTO,
     * or with status 400 (Bad Request) if the subMenuDTO is not valid,
     * or with status 500 (Internal Server Error) if the subMenuDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sub-menus")
    @Timed
    public ResponseEntity<SubMenuDTO> updateSubMenu(@Valid @RequestBody SubMenuDTO subMenuDTO) throws URISyntaxException {
        log.debug("REST request to update SubMenu : {}", subMenuDTO);
        if (subMenuDTO.getId() == null) {
            return createSubMenu(subMenuDTO);
        }
        SubMenuDTO result = subMenuService.save(subMenuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subMenu", subMenuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-menus : get all the subMenus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subMenus in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sub-menus")
    @Timed
    public ResponseEntity<List<SubMenuDTO>> getAllSubMenus(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SubMenus");
        Page<SubMenuDTO> page = subMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sub-menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sub-menus/:id : get the "id" subMenu.
     *
     * @param id the id of the subMenuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subMenuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sub-menus/{id}")
    @Timed
    public ResponseEntity<SubMenuDTO> getSubMenu(@PathVariable Long id) {
        log.debug("REST request to get SubMenu : {}", id);
        SubMenuDTO subMenuDTO = subMenuService.findOne(id);
        return Optional.ofNullable(subMenuDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sub-menus/:id : delete the "id" subMenu.
     *
     * @param id the id of the subMenuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubMenu(@PathVariable Long id) {
        log.debug("REST request to delete SubMenu : {}", id);
        subMenuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subMenu", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sub-menus?query=:query : search for the subMenu corresponding
     * to the query.
     *
     * @param query the query of the subMenu search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sub-menus")
    @Timed
    public ResponseEntity<List<SubMenuDTO>> searchSubMenus(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SubMenus for query {}", query);
        Page<SubMenuDTO> page = subMenuService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sub-menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
