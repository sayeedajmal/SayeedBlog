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

import com.strong.PostService.Repository.CommentRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Comments;
import com.strong.PostService.model.Posts;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public Comments saveComment(Comments comment) throws BlogException {
        comment.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        comment.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        comment.setUpdatedAt(null);
        addCommentToPost(comment);
        return commentRepo.save(comment);
    }

    @Transactional
    public void addCommentToPost(Comments comment) throws BlogException {
        Query query = new Query(Criteria.where("_id").is(comment.getPostId()));

        Posts post = mongoTemplate.findOne(query, Posts.class);
        if (post == null) {
            throw new BlogException("Can't find post with PostId: " + comment.getPostId());
        }
        Update update = new Update().addToSet("comments", comment.get_id());
        mongoTemplate.updateFirst(query, update, Posts.class);
    }

    public List<Comments> getAllByPostId(String postId) throws BlogException {
        List<Comments> comments = commentRepo.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new BlogException("No comments found for PostId: " + postId);
        }
        return comments;
    }

    public long countCmt(String postId) {
        return commentRepo.countByPostId(postId);
    }

    public List<Comments> findByPostAndAuthor(String postId, String authorId) {
        return null;
    }

    public Comments findCmtById(String cmtId) throws BlogException {
        return commentRepo.findById(cmtId)
                .orElseThrow(() -> new BlogException("Comment not found with ID: " + cmtId));
    }

    public List<Comments> findByAuthor(String userId) {
        return null;
    }

    @Transactional
    public void removeCmtById(String cmtId) throws BlogException {
        Comments comment = findCmtById(cmtId);

        // Remove comment ID from the associated post
        Query query = new Query(Criteria.where("_id").is(comment.getPostId()));
        Posts post = mongoTemplate.findOne(query, Posts.class);
        if (post == null) {
            throw new BlogException("Can't find post with PostId: " + comment.getPostId());
        }
        Update update = new Update().pull("comments", comment.get_id());
        mongoTemplate.updateFirst(query, update, Posts.class);

        commentRepo.delete(comment);
    }

    @Transactional
    public void updateCmt(Comments comment) throws BlogException {
        Comments existingComment = findCmtById(comment.get_id());
        existingComment.setContent(comment.getContent());
        existingComment.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        commentRepo.save(existingComment);
    }
}
