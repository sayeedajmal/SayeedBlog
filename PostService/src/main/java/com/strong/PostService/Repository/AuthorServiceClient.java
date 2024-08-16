package com.strong.PostService.Repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.strong.PostService.Utils.JwtValidationResponse;
import com.strong.PostService.Utils.MongoFeignConfig;
import com.strong.PostService.model.Author;

@FeignClient(name = "AuthorService", configuration = MongoFeignConfig.class)
public interface AuthorServiceClient {

    @GetMapping("/api/author/{id}")
    Author getAuthorById(@PathVariable("id") String id);

    @GetMapping("/api/author/email/{email}")
    Author findByEmail(@PathVariable("email") String email);

    @GetMapping("/api/author/validateToken/{token}")
    ResponseEntity<JwtValidationResponse> validateToken(@PathVariable("token") String token,
            @RequestHeader("Authorization") String authorizationHeader);
}
