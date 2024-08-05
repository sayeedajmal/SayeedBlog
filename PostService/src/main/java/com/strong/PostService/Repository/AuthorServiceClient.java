package com.strong.PostService.Repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.strong.PostService.Utils.MongoConfig;
import com.strong.PostService.model.Author;

@FeignClient(name = "author-service", url = "http://localhost:8081", configuration = MongoConfig.class)
public interface AuthorServiceClient {
    @GetMapping("/api/author/{id}")
    Author getAuthorById(@PathVariable("id") String id);
}
