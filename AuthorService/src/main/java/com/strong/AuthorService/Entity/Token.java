package com.strong.AuthorService.Entity;

import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private String _id;

    @Indexed(unique = true)
    private String accessToken;

    @Indexed(unique = true)
    private String refreshToken;

    private boolean loggedOut;

    @DBRef
    private Author author;

}
