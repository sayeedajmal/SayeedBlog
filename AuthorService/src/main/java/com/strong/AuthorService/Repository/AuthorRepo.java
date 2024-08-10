package com.strong.AuthorService.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.strong.AuthorService.Entity.Author;

@Repository
public interface AuthorRepo extends MongoRepository<Author, String> {

    Optional<Author> findByEmail(String email);
}
