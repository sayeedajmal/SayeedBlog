package com.strong.PostService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.PostService.model.Comments;

@Repository
public interface CommentRepo extends MongoRepository<Comments, String> {

    List<Comments> findByPostId(String postId);

    List<Comments> findByUserId(String userId);

    @Query("{ 'postId': ?0 }")
    long countBypostId(String postId);
}
