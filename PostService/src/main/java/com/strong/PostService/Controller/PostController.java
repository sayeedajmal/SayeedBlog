package com.strong.PostService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.PostService;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Posts;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

/**
 * REST controller for managing posts.
 */
@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Creates a new post.
     * 
     * @param post the post to create
     * @return ResponseEntity containing the created post and HTTP status 201
     *         (Created)
     * @throws BlogException if an error occurs during post creation
     */
    @Transactional
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AUTHOR')")
    public ResponseEntity<Posts> createPost(@RequestBody @Valid Posts post) throws BlogException {
        Posts createdPost = postService.CreatePost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * Retrieves all posts.
     * 
     * @return ResponseEntity containing a list of all posts and HTTP status 200
     *         (OK)
     * @throws BlogException if an error occurs during retrieval
     */
    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Posts>> getAllPosts() throws BlogException {
        List<Posts> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Retrieves a post by its ID.
     * 
     * @param postId the ID of the post to retrieve
     * @return ResponseEntity containing the post with the given ID and HTTP status
     *         200 (OK)
     * @throws BlogException if the post is not found or an error occurs
     */
    @GetMapping("/{postId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<Posts> getPostById(@PathVariable("postId") String postId) throws BlogException {
        Posts post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    /**
     * Retrieves posts by the author's ID.
     * 
     * @param authorId the ID of the author
     * @return ResponseEntity containing a list of posts by the given author and
     *         HTTP status 200 (OK)
     * @throws BlogException if an error occurs during retrieval
     */
    @GetMapping("/author/{authorId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Posts>> getPostsByAuthorId(@PathVariable("authorId") String authorId)
            throws BlogException {
        List<Posts> posts = postService.findPostByAuthorId(authorId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Retrieves posts by their title.
     * 
     * @param title the title of the posts to retrieve
     * @return ResponseEntity containing a list of posts with the given title and
     *         HTTP status 200 (OK)
     * @throws BlogException if an error occurs during retrieval
     */
    @GetMapping("/title")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Posts>> getPostsByTitle(@RequestParam("title") String title) throws BlogException {
        List<Posts> posts = postService.findPostByTitle(title);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Retrieves posts by their tag.
     * 
     * @param tag the tag of the posts to retrieve
     * @return ResponseEntity containing a list of posts with the given tag and HTTP
     *         status 200 (OK)
     * @throws BlogException if an error occurs during retrieval
     */
    @GetMapping("/tag")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<Posts>> getPostsByTag(@RequestParam("tag") String tag) throws BlogException {
        List<Posts> posts = postService.findPostByTags(tag);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Deletes a post by its ID.
     * 
     * @param postId the ID of the post to delete
     * @return ResponseEntity with HTTP status 204 (No Content) if the deletion was
     *         successful
     * @throws BlogException if the post is not found or an error occurs
     */
    @Transactional
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AUTHOR')")
    public ResponseEntity<Void> deletePostById(@PathVariable("postId") String postId) throws BlogException {
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates a post by its ID.
     * 
     * @param postId      the ID of the post to update
     * @param updatedPost the updated post data
     * @return ResponseEntity containing the updated post and HTTP status 200 (OK)
     * @throws BlogException if the post is not found or an error occurs
     */
    @Transactional
    @PatchMapping("/{postId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AUTHOR')")
    public ResponseEntity<Posts> updatePostById(@PathVariable("postId") String postId,
            @RequestBody @Valid Posts updatedPost)
            throws BlogException {
        Posts post = postService.updatePostById(postId, updatedPost);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
