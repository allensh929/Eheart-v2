package com.eheart.service;

import com.eheart.service.dto.UploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service Interface for upload file.
 */
public interface UploadService {

    UploadDTO saveFile(MultipartFile file) throws IOException;
//    UploadDTO importUsers(MultipartFile file) throws IOException;
//    UploadDTO importUsersByTrip(MultipartFile file, Long tripId) throws IOException;
}
