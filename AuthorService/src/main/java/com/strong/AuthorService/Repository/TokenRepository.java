package com.strong.AuthorService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.strong.AuthorService.Entity.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findByAuthor(String authorId);

    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByRefreshToken(String refreshToken);

}