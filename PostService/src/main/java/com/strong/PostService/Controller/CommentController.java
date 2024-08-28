package com.strong.PostService.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.CommentService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Comments;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

/**
 * REST controller for managing comments.
 */
@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Creates a new comment.
     * 
     * @param comment the comment to be created
     * @return ResponseEntity containing the created comment and HTTP status 201
     *         (Created)
     * @throws BlogException if an error occurs while saving the comment
     */
    @PostMapping
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AUTHOR')")
    public ResponseEntity<Comments> createComment(@RequestBody @Valid Comments comment) throws BlogException {
        comment.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        Comments savedComment = commentService.saveComment(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    /**
     * Retrieves all comments for a specific post.
     * 
     * @param postId the ID of the post for which comments are to be retrieved
     * @return ResponseEntity containing the list of comments and HTTP status 200
     *         (OK)
     * @throws BlogException if an error occurs while retrieving comments
     */
    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Comments>> getAllCommentsByPostId(@RequestParam("postId") String postId)
            throws BlogException {
        List<Comments> comments = commentService.getAllByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Retrieves a comment by its ID.
     * 
     * @param cmtId the ID of the comment to be retrieved
     * @return ResponseEntity containing the comment and HTTP status 200 (OK)
     * @throws BlogException if the comment cannot be found or an error occurs
     */
    @GetMapping("/{cmtId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<Comments> getCommentById(@PathVariable String cmtId) throws BlogException {
        Comments comment = commentService.findCmtById(cmtId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * Retrieves comments by the user ID.
     * 
     * @param userId the ID of the user whose comments are to be retrieved
     * @return ResponseEntity containing the comments and HTTP status 200 (OK)
     * @throws BlogException if an error occurs while retrieving comments
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Comments>> getCommentsByUserId(@PathVariable String userId) throws BlogException {
        List<Comments> comments = commentService.findByAuthor(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Deletes a comment by its ID.
     * 
     * @param cmtId the ID of the comment to be deleted
     * @return ResponseEntity with HTTP status 204 (No Content) if the comment is
     *         successfully deleted
     * @throws BlogException if the comment cannot be found or an error occurs
     */
    @DeleteMapping("/{cmtId}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> removeCommentById(@PathVariable String cmtId) throws BlogException {
        commentService.removeCmtById(cmtId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Counts the number of comments for a specific post.
     * 
     * @param postId the ID of the post for which comment count is to be retrieved
     * @return ResponseEntity containing the count of comments and HTTP status 200
     *         (OK)
     * @throws BlogException if an error occurs while counting comments
     */
    @GetMapping("/count")
    @PreAuthorize("permitAll")
    public ResponseEntity<Long> countComments(@RequestParam("postId") String postId) throws BlogException {
        long count = commentService.countCmt(postId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
