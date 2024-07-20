package com.strong.PostService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Comments {

    @Id
    private String _id;

    private String postId;

    private String userId;

    private String content;

    private String createdAt;

    private String updatedAt;
}
