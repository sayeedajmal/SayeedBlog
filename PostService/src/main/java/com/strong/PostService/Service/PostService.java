package com.strong.PostService.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strong.PostService.Repository.PostRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Likes;
import com.strong.PostService.model.Posts;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private LikeService likeService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public void toggleLike(Likes like) throws BlogException {
        Query query = new Query(Criteria.where("_id").is(like.getPostId()));

        Posts post = mongoTemplate.findOne(query, Posts.class);
        if (post == null) {
            throw new BlogException("Can't find post by PostId: " + like.getPostId());
        }

        Update update;
        if (post.getLikes().contains(like.getUserId())) {
            update = new Update().pull("likes", like.getUserId());
            likeService.removeLike(like.getUserId());
        } else {
            likeService.saveLike(like);
            update = new Update().addToSet("likes", like.getUserId());
        }

        mongoTemplate.updateFirst(query, update, Posts.class);
    }

    public Posts CreatePost(Posts post) {
        post.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        post.setUpdatedAt(null);
        post.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        return postRepo.save(post);
    }

    public List<Posts> getAllPosts() throws BlogException {
        List<Posts> all = postRepo.findAll();
        if (!all.isEmpty()) {
            return all;
        }
        throw new BlogException("There's no blog");
    }

    public Posts findPostById(String postId) throws BlogException {
        return postRepo.findById(postId).orElseThrow();
    }

    public void deletePostbyId(String postId) throws BlogException {
        Posts byId = postRepo.findById(postId).orElseThrow(() -> new BlogException("Can't Found PostId : " + postId));
        postRepo.delete(byId);
    }

}
