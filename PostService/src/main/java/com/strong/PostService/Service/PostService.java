package com.strong.PostService.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strong.PostService.Repository.PostRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Posts;
import com.strong.PostService.model.Posts.ContentBlock;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional
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
        return postRepo.findById(postId).orElseThrow(() -> new BlogException("Cant' Found Post by postId: " + postId));
    }

    @Transactional
    public Posts updatePostById(String postId, Posts newPost) throws BlogException {
        Query query = new Query(Criteria.where("_id").is(postId));

        Update update = new Update();
        if (newPost.getTitle() != null) {
            update.set("title", newPost.getTitle());
        }
        if (newPost.getSummary() != null) {
            update.set("summary", newPost.getSummary());
        }
        if (newPost.getContent() != null) {
            update.set("content", newPost.getContent());
        }
        if (newPost.getTags() != null) {
            update.set("tags", newPost.getTags());
        }
        update.set("updatedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        mongoTemplate.updateFirst(query, update, Posts.class);
        return mongoTemplate.findOne(query, Posts.class);
    }

    @Transactional
    public void deletePostById(String postId) throws BlogException {
        Posts post = postRepo.findById(postId)
                .orElseThrow(() -> new BlogException("Can't find post by PostId: " + postId));

        // Delete comments
        if (post.getComments() != null && !post.getComments().isEmpty()) {
            for (String commentId : post.getComments()) {
                commentService.removeCmtById(commentId);
            }
        }

        // Delete Images
        if (post.getContent() != null && !post.getContent().isEmpty()) {
            List<String> imageUrls = post.getContent().stream()
                    .map(ContentBlock::getImageUrl)
                    .collect(Collectors.toList());

            for (String fieldId : imageUrls) {
                imageStorageService.deleteImage(fieldId);
            }
        }

        // Delete associated likes
        if (post.getLikes() != null && !post.getLikes().isEmpty()) {
            for (String userId : post.getLikes()) {
                likeService.removeLike(userId);
            }
        }

        postRepo.delete(post);
    }

}
