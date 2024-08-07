package com.strong.PostService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Tags {

    @Id
    private String tagId;

    @NonNull
    private String name;
    
    @NonNull
    private String createdAt;
}
