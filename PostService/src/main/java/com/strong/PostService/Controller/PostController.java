package com.strong.PostService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.PostService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Posts;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Posts> createPost(@RequestBody @Valid Posts post) {
        return new ResponseEntity<>(postService.CreatePost(post), HttpStatus.CREATED);
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<Posts>> showAllPosts() throws BlogException {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/byPostId")
    public ResponseEntity<Posts> findPostbyId(@RequestParam("postId") String postId) throws BlogException {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/byAuthorId")
    public ResponseEntity<List<Posts>> findPostbyAuthorId(@RequestParam("authorId") String authorId)
            throws BlogException {
        return new ResponseEntity<>(postService.findPostByAuthorId(authorId), HttpStatus.OK);
    }

    @GetMapping("/byTitle")
    public ResponseEntity<List<Posts>> findPostbyTitle(@RequestParam("Title") String title)
            throws BlogException {
        return new ResponseEntity<>(postService.findPostByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/byTag")
    public ResponseEntity<List<Posts>> findPostbyTag(@RequestParam("Tag") String tag)
            throws BlogException {
        return new ResponseEntity<>(postService.findPostByTags(tag), HttpStatus.OK);
    }

    @DeleteMapping("/byPostId")
    public ResponseEntity<?> deletePostById(@RequestParam("postId") String postId) throws BlogException {
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/byPostId")
    public ResponseEntity<?> updatePostById(@RequestParam("postId") String postId, @RequestBody @Valid Posts newPost)
            throws BlogException {
        return new ResponseEntity<>(postService.updatePostById(postId, newPost), HttpStatus.OK);
    }
}
