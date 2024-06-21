package com.strong.PostService.model;

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
public class Posts {

    @Id
    private String postId;

    private String title;

    private String authorId;

    private String createdAt;

    private String updatedAt;

    private List<Comments> comments;

    private List<Likes> likes;

    private List<String> tags;

    private List<ContentBlock> content;
}
