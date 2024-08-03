package com.strong.AuthorService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Repository.AuthorRepo;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepo authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(String id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Can't Find Author by : " + id));
        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());
        author.setBio(authorDetails.getBio());
        author.setProfilePicture(authorDetails.getProfilePicture());
        return authorRepository.save(author);
    }

    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }
}
