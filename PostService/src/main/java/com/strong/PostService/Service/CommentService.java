package com.strong.PostService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strong.PostService.Repository.CommentRepo;
import com.strong.PostService.model.Comments;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public void SaveComment(Comments comment) {
        commentRepo.save(comment);
    }
}
