package com.strong.PostService.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.LikeService;
import com.strong.PostService.Service.PostService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Likes;

import jakarta.validation.Valid;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private PostService postService;

    @PostMapping("like")
    public ResponseEntity<?> saveLike(@RequestBody @Valid Likes like) throws BlogException {
        like.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        postService.toggleLike(like);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("countLike")
    public ResponseEntity<?> countLike(@RequestParam("postId") String postId) throws BlogException {
        return new ResponseEntity<>(likeService.count(postId), HttpStatus.CREATED);
    }
}
