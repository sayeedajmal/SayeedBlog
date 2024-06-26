package com.strong.PostService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.PostService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Posts;

import jakarta.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("savePost")
    public void createPost(@RequestBody @Valid Posts post) {
        postService.CreatePost(post);
    }

    @GetMapping("showAll")
    public ResponseEntity<List<Posts>> showAllPosts() throws BlogException {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("ByPostId")
    public ResponseEntity<Posts> findPostbyId(@RequestParam("postId") String postId) throws BlogException {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @DeleteMapping("ByPostId")
    public void deletePostById(@RequestParam("postId") String postId) throws BlogException {
        postService.deletePostbyId(postId);
    }

}
