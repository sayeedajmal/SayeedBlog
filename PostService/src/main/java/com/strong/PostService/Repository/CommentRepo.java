package com.strong.PostService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.PostService.model.Comments;

@Repository
public interface CommentRepo extends MongoRepository<Comments, String> {

    List<Comments> findByPostId(String postId);

   /*  @Query("{ 'author._id': ?0 }")
    List<Comments> findByAuthorId(String authorId); */

    @Query("{ 'postId': ?0 }")
    long countByPostId(String postId);

    /*
     * @Query("{ 'postId': ?0, 'author.author_Id': ?1 }")
     * List<Comments> findByPostIdAndAuthorId(String postId, String authorId);
     */
}
