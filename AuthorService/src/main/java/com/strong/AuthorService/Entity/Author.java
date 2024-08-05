package com.strong.AuthorService.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Author {
    @Id
    @Indexed(unique = true)
    private String _id;

    @Indexed(unique = true)
    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String bio;

    @NonNull
    private String password;

    @NonNull
    private String profilePicture;

    private List<String> blogPosts;

}
