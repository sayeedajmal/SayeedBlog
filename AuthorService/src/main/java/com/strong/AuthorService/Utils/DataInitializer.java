package com.strong.AuthorService.Utils;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Repository.AuthorRepo;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AuthorRepo authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.count() == 0) {
            Author admin = new Author();
            admin.setName("Admin");
            admin.setEmail("Admin@gmail.com");
            admin.setBio("Admin of the system");
            admin.setPassword(passwordEncoder.encode("Admin"));
            admin.setAuthorities(Collections.singletonList("ADMIN"));
            admin.setProfilePicture("classpath:static/images/author.png");

            authorRepository.save(admin);
            System.out.println(
                    "======================== Admin user created. PLEASE CHANGE ADMIN USERNAME AND PASSWORD..===============");
        }
    }
}
