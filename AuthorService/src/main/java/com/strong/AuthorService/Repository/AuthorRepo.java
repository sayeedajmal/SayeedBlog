package com.strong.AuthorService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.strong.AuthorService.Entity.Author;

public interface AuthorRepo extends MongoRepository<Author, String> {
}
