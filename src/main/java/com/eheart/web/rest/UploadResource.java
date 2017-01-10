package com.eheart.web.rest;

import com.eheart.service.UploadService;
import com.eheart.service.dto.UploadDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * REST controller for managing upload file.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {

    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    @Inject
    private UploadService uploadService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadDTO> uploadFile(
        @RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException, IOException {

        log.debug("path:" + System.getProperty("user.dir"));

        UploadDTO uploadFile = uploadService.saveFile(file);

        if (StringUtils.isNotEmpty(uploadFile.getFile())) {

            return ResponseEntity.ok(uploadFile);
        } else {

            return ResponseEntity.badRequest().body(null);
        }

    }

//    @RequestMapping(value = "/upload/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<UploadDTO> importUsers(
//        @RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException, IOException {
//
//        log.debug("path:" + System.getProperty("user.dir"));
//
//        UploadDTO uploadFile = uploadService.importUsers(file);
//
//        if (StringUtils.isNotEmpty(uploadFile.getFile())) {
//
//            return ResponseEntity.ok(uploadFile);
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//    }

}
