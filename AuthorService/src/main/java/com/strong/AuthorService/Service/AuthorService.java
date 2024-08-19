package com.strong.AuthorService.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Entity.Token;
import com.strong.AuthorService.Repository.AuthorRepo;
import com.strong.AuthorService.Repository.TokenRepository;
import com.strong.AuthorService.Utils.AuthorException;
import com.strong.AuthorService.Utils.JwtUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthorService implements UserDetailsService {

    @Autowired
    private AuthorRepo authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) throws AuthorException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException("Author Not Found by this Id: " + id));
    }

    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Author not found"));
    }

    public String signUp(Author author, MultipartFile profilePicture) throws AuthorException {
        Optional<Author> existingAuthor = authorRepository.findByEmail(author.getEmail());
        if (existingAuthor.isPresent()) {
            throw new AuthorException("Email already in use: " + author.getEmail());
        }

        if (profilePicture == null || profilePicture.isEmpty()) {
            throw new AuthorException("Profile picture must be provided");
        }

        String fileId = imageStorageService.uploadImage(profilePicture);
        author.setProfilePicture(fileId);

        author.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setAuthorities(List.of("AUTHOR"));
        author.setAccountNonExpired(true);
        author.setAccountNonLocked(true);
        author.setCredentialsNonExpired(true);
        author.setEnabled(true);

        authorRepository.save(author);

        String accessToken = jwtUtil.generateAccessToken(author);
        String refreshToken = jwtUtil.generateRefreshToken(author);

        saveToken(accessToken, refreshToken, author);

        return accessToken;
    }

    public String authenticate(Author author) throws AuthorException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    author.getEmail(),
                    author.getPassword()));
        } catch (AuthenticationException e) {
            throw new JwtException("Incorrect email or password: " + e.getMessage());
        }

        Author authenticatedAuthor = authorRepository.findByEmail(author.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Author not found"));

        String accessToken = jwtUtil.generateAccessToken(authenticatedAuthor);
        String refreshToken = jwtUtil.generateRefreshToken(authenticatedAuthor);

        revokeAllTokens(authenticatedAuthor);
        saveToken(accessToken, refreshToken, authenticatedAuthor);
        return accessToken;
    }

    public Author updateAuthor(String id, Author authorDetails) throws AuthorException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException("Can't Find Author by Id: " + id));

        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());
        author.setBio(authorDetails.getBio());
        author.setProfilePicture(authorDetails.getProfilePicture());

        return authorRepository.save(author);
    }

    public void deleteAuthor(String id) throws AuthorException {
        if (!authorRepository.existsById(id)) {
            throw new AuthorException("Can't Find Author by Id: " + id);
        }
        authorRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Author not found"));
    }

    private void saveToken(String accessToken, String refreshToken, Author author) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setAuthor(author);
        tokenRepository.save(token);
    }

    public ResponseEntity<String> refreshToken(HttpServletRequest request) throws AuthorException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Refresh token is missing or malformed", HttpStatus.UNAUTHORIZED);
        }

        String refreshToken = authHeader.substring(7);
        String email = jwtUtil.extractUserEmail(refreshToken);

        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Author not found"));

        if (jwtUtil.isValidRefreshToken(refreshToken, author)) {
            String newAccessToken = jwtUtil.generateAccessToken(author);
            String newRefreshToken = jwtUtil.generateRefreshToken(author);

            revokeAllTokens(author);
            saveToken(newAccessToken, newRefreshToken, author);

            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid refresh token", HttpStatus.UNAUTHORIZED);
    }

    private void revokeAllTokens(Author author) {
        List<Token> validTokens = tokenRepository.findByAuthor(author.get_id());
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            tokenRepository.delete(token);
        });
    }
}
