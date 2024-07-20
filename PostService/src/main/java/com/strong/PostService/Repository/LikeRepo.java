package com.strong.PostService.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.PostService.model.Likes;

@Repository
public interface LikeRepo extends MongoRepository<Likes, String> {

    Optional<Likes> findByUserId(String userId);
}
