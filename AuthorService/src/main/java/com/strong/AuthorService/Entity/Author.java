package com.strong.AuthorService.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Author {
    @Id
    private String _id;

    private String name;

    private String email;

    private String bio;

    private String password;

    private String profilePicture;

    private List<String> blogPosts;

}
