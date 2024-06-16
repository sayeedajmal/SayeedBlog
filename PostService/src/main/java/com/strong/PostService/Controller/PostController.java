package com.strong.PostService.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.PostService;
import com.strong.PostService.model.Posts;

import jakarta.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("savePost")
    public void createPost(@RequestBody @Valid Posts post) {
        post.setPostId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        postService.CreatePost(post);
    }
}
