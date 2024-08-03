package com.strong.PostService.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.PostService.model.Posts;

@Repository
public interface PostRepo extends MongoRepository<Posts, String> {

    List<Posts> findByAuthorId(String authorId);

    List<Posts> findByTags(String tag);

    List<Posts> findByTitle(String title);

}
