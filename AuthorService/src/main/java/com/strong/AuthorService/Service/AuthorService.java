package com.strong.AuthorService.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Repository.AuthorRepo;
import com.strong.AuthorService.Utils.AuthorException;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepo authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) throws AuthorException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException("Author Not Found by this Id: " + id));
    }

    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public Author createAuthor(Author author) throws AuthorException {
        Author byEmail = findByEmail(author.getEmail());
        if (byEmail == null) {
            author.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
            return authorRepository.save(author);
        } else {
            throw new AuthorException("Already Found This Email: " + author.getEmail());
        }
    }

    public Author updateAuthor(String id, Author authorDetails) throws AuthorException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException("Can't Find Author by : " + id));
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
