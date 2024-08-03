package com.strong.AuthorService.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.AuthorService.Entity.Token;
import com.strong.AuthorService.Repository.TokenRepository;

@RestController
@RequestMapping("/api")
public class Actuator {
    @Autowired
    TokenRepository tokenRepository;

    @GetMapping("actuator/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("UP", HttpStatus.OK);
    }

    @GetMapping("accessToken")
    public ResponseEntity<String> accessToken(@RequestParam("token") String token) {
        Optional<Token> byAccessToken = tokenRepository.findByAccessToken(token);
        return new ResponseEntity<String>(byAccessToken.get().getAccessToken(), HttpStatus.OK);
    }
}