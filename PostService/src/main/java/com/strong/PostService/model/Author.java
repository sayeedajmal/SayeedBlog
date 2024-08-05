package com.strong.PostService.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
