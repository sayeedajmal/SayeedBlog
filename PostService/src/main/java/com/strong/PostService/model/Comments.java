package com.strong.PostService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Comments {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String postId;

    @NonNull
    private String userId;

    @NonNull
    private String content;

    @NonNull
    private String createdAt;

    private String updatedAt;
}
