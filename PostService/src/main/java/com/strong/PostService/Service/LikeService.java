package com.strong.PostService.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strong.PostService.Repository.LikeRepo;
import com.strong.PostService.Repository.PostRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Likes;
import com.strong.PostService.model.Posts;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public long toggleLike(Likes like) throws BlogException {
        if (like == null) {
            throw new IllegalArgumentException("Like cannot be null");
        }

        // Query to find the post by ID
        Query query = new Query(Criteria.where("_id").is(like.getPostId()));

        // Fetch the post from the database
        Posts post = mongoTemplate.findOne(query, Posts.class);
        if (post == null) {
            throw new BlogException("Can't find post by PostId: " + like.getPostId());
        }

        // Create an update object
        Update update = new Update();
        boolean isLiked = post.getLikes() != null && post.getLikes().contains(like.getUserId());

        // Toggle logic
        if (isLiked) {
            // User has already liked the post; remove the like
            update.pull("likes", like.getUserId());
            removeLike(like.getUserId());
        } else {
            // User has not liked the post; add the like
            update.addToSet("likes", like.getUserId());
            saveLike(like);
        }

        // Update the post in the database
        mongoTemplate.updateFirst(query, update, Posts.class);

        // Fetch the updated post to get the new like count
        Posts updatedPost = mongoTemplate.findOne(query, Posts.class);
        if (updatedPost == null) {
            throw new BlogException("Can't find updated post by PostId: " + like.getPostId());
        }

        // Return the total count of likes after the operation
        return updatedPost.getLikes() != null ? updatedPost.getLikes().size() : 0;
    }

    @Transactional
    public void saveLike(Likes like) throws BlogException {
        Optional<Posts> postbyId = postRepo.findById(like.getPostId());
        if (postbyId.isPresent()) {
            likeRepo.save(like);
        } else
            throw new BlogException("can't Found post by PostId: " + like.getPostId());
    }

    public long count(String postId) throws BlogException {
        return likeRepo.countBypostId(postId);
    }

    @Transactional
    public void removeLike(String userId) throws BlogException {
        likeRepo.delete(findByUserId(userId));
    }

    public Likes findByUserId(String userId) throws BlogException {
        return likeRepo.findByUserId(userId)
                .orElseThrow(() -> new BlogException("Can't Found Like by UserId : " + userId));
    }
}
