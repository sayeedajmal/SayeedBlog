package com.strong.PostService.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.LikeService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Likes;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

/**
 * REST controller for managing likes on posts.
 */
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * Creates or toggles a like for a post.
     * 
     * @param like the like information
     * @return ResponseEntity with HTTP status 201 (Created) if the like is
     *         successfully processed
     * @throws BlogException if an error occurs during processing
     */
    @Transactional
    @PostMapping
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Author')")
    public ResponseEntity<?> saveLike(@RequestBody @Valid Likes like) throws BlogException {
        like.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        return new ResponseEntity<>(likeService.toggleLike(like), HttpStatus.CREATED);
    }

    /**
     * Retrieves the count of likes for a specific post.
     * 
     * @param postId the ID of the post
     * @return ResponseEntity containing the count of likes and HTTP status 200 (OK)
     * @throws BlogException if an error occurs during retrieval
     */
    @GetMapping("/count")
    @PreAuthorize("permitAll")
    public ResponseEntity<Long> countLikes(@RequestParam("postId") String postId) throws BlogException {
        long likeCount = likeService.count(postId);
        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }
}
