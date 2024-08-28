package com.strong.AuthorService.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Service.AuthorService;
import com.strong.AuthorService.Utils.AuthorException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AuthorController class handles HTTP requests related to author operations.
 * This controller uses the @RestController annotation to indicate that it's a
 * controller and automatically serializes the returned objects into JSON
 * format.
 */
@RestController
@RequestMapping("api/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/validateToken/{token}")
    public ResponseEntity<JwtValidationResponse> validateToken(
            @PathVariable("token") String token,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Get the current authentication token from the SecurityContext
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        if (authToken != null && authToken.isAuthenticated()) {
            String username = authToken.getName();
            List<String> roles = authToken.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtValidationResponse response = new JwtValidationResponse();
            response.setUsername(username);
            response.setRoles(roles);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    class JwtValidationResponse {
        private String username;
        private List<String> roles;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    /**
     * GET endpoint to retrieve a list of all authors.
     *
     * @return A {@link ResponseEntity} containing a list of all authors and an
     *         HTTP status code 200 (OK). Access is restricted to users with the
     *         'AUTHOR' authority.
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve an author by their ID.
     *
     * @param id The unique identifier of the author to be retrieved.
     * @return A {@link ResponseEntity} containing the author object and an HTTP
     *         status code 200 (OK). Access is restricted to users with the 'AUTHOR'
     *         or 'ADMIN' authority.
     * @throws AuthorException if the author with the specified ID is not found.
     */
    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String id) throws AuthorException {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve an author by their email.
     *
     * @param email The unique identifier of the author to be retrieved.
     * @return A {@link ResponseEntity} containing the author object and an HTTP
     *         status code 200 (OK). Access is restricted to users with the 'AUTHOR'
     *         or 'ADMIN' authority.
     * @throws AuthorException if the author with the specified email is not found.
     */
    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<Author> getAuthorByEmail(@PathVariable String email) throws AuthorException {
        return new ResponseEntity<>(authorService.findByEmail(email), HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new author.
     *
     * @param name           The name of the author.
     * @param email          The email of the author.
     * @param bio            The biography of the author.
     * @param password       The password for the author account.
     * @param profilePicture The profile picture of the author.
     * @return A {@link ResponseEntity} with a success message and HTTP status
     *         code 201 (Created). This endpoint is accessible to everyone.
     * @throws AuthorException if there is an error during author creation.
     */
    @Transactional
    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> createAuthor(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("bio") String bio,
            @RequestParam("password") String password,
            @RequestParam("profilePicture") MultipartFile profilePicture) throws AuthorException {
        Author author = new Author();
        author.setName(name);
        author.setEmail(email);
        author.setBio(bio);
        author.setPassword(password);
        return new ResponseEntity<>(authorService.signUp(author, profilePicture), HttpStatus.CREATED);
    }

    /**
     * POST endpoint to authenticate an author and log in.
     *
     * @param author The author object containing login credentials.
     * @return A {@link ResponseEntity} containing an authentication token and HTTP
     *         status code 200 (OK). This endpoint is accessible to everyone.
     * @throws AuthorException if there is an error during authentication.
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> login(@RequestBody Author author) throws AuthorException {
        return ResponseEntity.ok(authorService.authenticate(author));
    }

    /**
     * PUT endpoint to update an existing author.
     *
     * @param id            The unique identifier of the author to be updated.
     * @param authorDetails The author object containing updated details.
     * @return A {@link ResponseEntity} containing the updated author object and
     *         HTTP status code 200 (OK) if successful. Returns HTTP status code
     *         404 (Not Found) if the author is not found. Access is restricted to
     *         users with the 'AUTHOR' authority.
     * @throws AuthorException if there is an error during author update.
     */
    @Transactional
    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author authorDetails)
            throws AuthorException {
        Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
        if (updatedAuthor != null) {
            return ResponseEntity.ok(updatedAuthor);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE endpoint to remove an author by their ID.
     *
     * @param id The unique identifier of the author to be deleted.
     * @return A {@link ResponseEntity} with HTTP status code 204 (No Content)
     *         indicating successful deletion. Access is restricted to users with
     *         the 'ADMIN' authority.
     * @throws AuthorException if there is an error during author deletion.
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) throws AuthorException {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * POST endpoint to refresh an authentication token.
     *
     * @param request The HTTP request containing the current token information.
     * @return A {@link ResponseEntity} containing a new authentication token and
     *         HTTP status code 200 (OK). Access is restricted to users with the
     *         'AUTHOR' or 'ADMIN' authority.
     * @throws AuthorException if there is an error during token refresh.
     */
    @PreAuthorize("hasAuthority('AUTHOR') or hasAuthority('ADMIN')")
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request) throws AuthorException {
        return authorService.refreshToken(request);
    }
}
