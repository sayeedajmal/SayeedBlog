package com.strong.PostService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strong.PostService.Repository.PostRepo;
import com.strong.PostService.model.Posts;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public void CreatePost(Posts post) {
        postRepo.save(post);
    }
}
