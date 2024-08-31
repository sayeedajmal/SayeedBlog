package com.strong.AuthorService.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strong.AuthorService.Service.ImageStorageService;
import com.strong.AuthorService.Utils.AuthorException;

import jakarta.transaction.Transactional;

/**
 * ImageUploadController class handles HTTP requests related to image
 * operations.
 * This controller uses the @RestController annotation to indicate that it's a
 * controller and automatically serializes the returned objects into JSON
 * format.
 */
@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    /**
     * GET endpoint to retrieve an image by its file ID.
     *
     * @param fileId The unique identifier of the image to be retrieved.
     * @return A {@link ResponseEntity} containing the image data as an
     *         {@link InputStreamResource} and an HTTP status code 200 (OK).
     *         The image is served with a content type of "image/jpeg". This
     *         endpoint is accessible to everyone.
     * @throws IOException     if there is an error reading the image file.
     * @throws AuthorException if the image cannot be found or accessed.
     */
    @GetMapping("/{fileId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileId)
            throws IOException, AuthorException {
        InputStreamResource resource = imageStorageService.getImageStream(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(resource);
    }

    /**
     * DELETE endpoint to remove an image by its file ID.
     *
     * @param fieldId The unique identifier of the image to be deleted.
     * @return A {@link ResponseEntity} with HTTP status code 200 (OK)
     *         indicating successful deletion. Access is restricted to users
     *         with the 'AUTHOR' or 'ADMIN' authority.
     * @throws AuthorException if there is an error during image deletion.
     */
    @Transactional
    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @DeleteMapping("/{fieldId}")
    public ResponseEntity<?> removeImgById(@PathVariable String fieldId) throws AuthorException {
        imageStorageService.deleteImage(fieldId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
