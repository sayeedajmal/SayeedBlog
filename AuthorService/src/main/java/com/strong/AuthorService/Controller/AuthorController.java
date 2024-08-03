package com.strong.AuthorService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Service.AuthorService;
import com.strong.AuthorService.Utils.AuthorException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String id) throws AuthorException {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> createAuthor(@RequestBody Author author) throws AuthorException {
        return new ResponseEntity<>(authorService.signUp(author), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> login(@RequestBody Author author) throws AuthorException {
        return ResponseEntity.ok(authorService.authenticate(author));
    }

    @Transactional
    @PreAuthorize("hasAuthority('AUTHOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author authorDetails)
            throws AuthorException {
        Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
        if (updatedAuthor != null) {
            return ResponseEntity.ok(updatedAuthor);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) throws AuthorException {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request) throws AuthorException {
        return authorService.refreshToken(request);
    }
}
