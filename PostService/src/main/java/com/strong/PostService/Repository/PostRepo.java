package com.strong.PostService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.PostService.model.Posts;

@Repository
public interface PostRepo extends MongoRepository<Posts, String> {

}
