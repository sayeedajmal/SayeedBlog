package com.strong.AuthorService.Entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Token {

    @Id
    private String _id;

    private String accessToken;

    private String refreshToken;

    private boolean loggedOut;

    @DBRef
    private Author author;

}
