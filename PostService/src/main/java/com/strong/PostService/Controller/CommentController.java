package com.strong.PostService.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.CommentService;
import com.strong.PostService.model.Comments;

import jakarta.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("saveComment")
    public void CreateComment(@RequestBody @Valid Comments comment) {
        comment.setPostId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        commentService.SaveComment(comment);
    }
}
