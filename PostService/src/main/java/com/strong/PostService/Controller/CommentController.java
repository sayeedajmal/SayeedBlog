package com.strong.PostService.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.CommentService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Comments;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("comment")
    @Transactional
    public ResponseEntity<?> CreateComment(@RequestBody @Valid Comments comment) throws BlogException {
        comment.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        return new ResponseEntity<>(commentService.SaveComment(comment), HttpStatus.CREATED);
    }

    @GetMapping("comment")
    public ResponseEntity<List<Comments>> showAllCmt(@RequestParam("postId") String postId) throws BlogException {
        return new ResponseEntity<>(commentService.getAllByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("byId")
    public ResponseEntity<Comments> findById(@RequestParam("cmtId") String cmtId) throws BlogException {
        return new ResponseEntity<>(commentService.findCmtById(cmtId), HttpStatus.OK);
    }

    @GetMapping("byUserId")
    public ResponseEntity<Comments> ByUserId(@RequestParam("userId") String userId) throws BlogException {
        return new ResponseEntity<>(commentService.findByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("byId")
    @Transactional
    public ResponseEntity<?> removeById(@RequestParam("cmtId") String cmtId) throws BlogException {
        commentService.removeCmtById(cmtId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("countcmt")
    public ResponseEntity<?> countLike(@RequestParam("postId") String postId) throws BlogException {
        return new ResponseEntity<>(commentService.countCmt(postId), HttpStatus.CREATED);
    }
}
