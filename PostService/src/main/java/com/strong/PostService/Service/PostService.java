package com.strong.PostService.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strong.PostService.Repository.PostRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Posts;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public void CreatePost(Posts post) {
        post.setPostId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        post.setUpdatedAt(null);
        post.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        postRepo.save(post);
    }

    public List<Posts> getAllPosts() throws BlogException {
        List<Posts> all = postRepo.findAll();
        if (!all.isEmpty()) {
            return all;
        }
        throw new BlogException("There's no blog");
    }

    public Posts findPostById(String postId) throws BlogException {
        Posts blog = postRepo.findById(postId).orElseThrow();
        if (blog != null) {
            return blog;
        }
        throw new BlogException("There's no blog with " + postId + " postId");
    }

    public void deletePostbyId(String postId) throws BlogException {
        Posts byId = postRepo.findById(postId).orElseThrow();
        if (byId != null) {
            postRepo.delete(byId);
        } else
            throw new BlogException("There's no blog with " + postId + " postId");
    }

    public void deleteAllPostbyauthorId(String authorId) {

    }
}
