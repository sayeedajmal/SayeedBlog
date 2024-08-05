package com.strong.PostService.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Posts {

    @Id
    private String _id;

    @NonNull
    private String title;

    @NonNull
    @Size(max = 250, min = 20)
    private String summary;

    @NonNull
    private String authorId;

    @NonNull
    private String createdAt;

    private String updatedAt;

    private List<String> comments;

    private List<String> likes;

    private List<String> tags;

    @NonNull
    private String content;

    private List<String> images;
}
