package com.strong.PostService.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.strong.PostService.Service.ImageStorageService;
import com.strong.PostService.Utils.BlogException;

import jakarta.transaction.Transactional;

/**
 * REST controller for managing image uploads and retrievals.
 */
@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    @Autowired
    private ImageStorageService imageStorageService;

    /**
     * Uploads multiple images.
     * 
     * @param files the images to be uploaded
     * @return a list of file IDs for the uploaded images
     */
    @Transactional
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Author')")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") MultipartFile[] files) {
        List<String> fileIds = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileId = imageStorageService.uploadImage(file);
            fileIds.add(fileId);
        }
        return new ResponseEntity<>(fileIds, HttpStatus.CREATED);
    }

    /**
     * Retrieves an image by its file ID.
     * 
     * @param fileId the ID of the file to be retrieved
     * @return ResponseEntity containing the image and HTTP status 200 (OK)
     * @throws IOException   if an I/O error occurs
     * @throws BlogException if an error occurs while retrieving the image
     */
    @GetMapping("/{fileId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileId) throws IOException, BlogException {
        InputStreamResource resource = imageStorageService.getImageStream(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(resource);
    }

    /**
     * Retrieves a list of all image file IDs.
     * 
     * @return ResponseEntity containing a list of all image file IDs and HTTP
     *         status 200 (OK)
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Author')")
    public ResponseEntity<List<String>> getAllImages() {
        List<String> imageIds = imageStorageService.listAllImages();
        return new ResponseEntity<>(imageIds, HttpStatus.OK);
    }

    /**
     * Deletes an image by its file ID.
     * 
     * @param fileId the ID of the file to be deleted
     * @return ResponseEntity with HTTP status 200 (OK) if the image is successfully
     *         deleted
     */
    @Transactional
    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Author')")
    public ResponseEntity<Void> removeImageById(@PathVariable String fileId) {
        imageStorageService.deleteImage(fileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
