package com.strong.PostService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.PostService.model.Comments;

@Repository
public interface CommentRepo extends MongoRepository<Comments, String> {

}
