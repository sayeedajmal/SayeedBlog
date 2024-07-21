package com.strong.PostService.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> uploadImages(@RequestParam("files") MultipartFile[] files) {
        List<String> fieldId = new ArrayList<>();
        for (MultipartFile file : files) {
            String uploadImage = imageStorageService.uploadImage(file);
            fieldId.add(uploadImage);
        }
        return fieldId;
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