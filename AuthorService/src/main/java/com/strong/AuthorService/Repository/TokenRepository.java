package com.strong.AuthorService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.AuthorService.Entity.Token;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findByAuthor(String authorId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);

}