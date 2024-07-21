package com.strong.PostService.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        String uploadImage = imageStorageService.uploadImage(file);
        return uploadImage;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileId) throws IOException, BlogException {
        InputStreamResource resource = imageStorageService.getImageStream(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(resource);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(imageStorageService.listAllImages(), HttpStatus.OK);
    }

    @DeleteMapping("byId")
    public ResponseEntity<?> removeImgById(@RequestParam("imgId") String filedId) {
        imageStorageService.deleteImage(filedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}