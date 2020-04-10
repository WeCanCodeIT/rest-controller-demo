package org.wcci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wcci.demo.model.Author;
import org.wcci.demo.repositories.AuthorRepository;

import java.util.Collection;
@RestController
public class AuthorController {
    @Autowired
    AuthorRepository authorRepo;
    @GetMapping("/api/authors/")
    public Collection<Author> retrieveAll() {
        return (Collection<Author>) authorRepo.findAll();
    }
    @GetMapping("/api/authors/{id}")
    public Author retrieveById(@PathVariable long id) {
        return authorRepo.findById(id).get();
    }
    @PostMapping("/api/authors/")
    public Author add(@RequestBody Author newAuthor) {
        return authorRepo.save(newAuthor);
    }
}
