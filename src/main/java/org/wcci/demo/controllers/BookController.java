package org.wcci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wcci.demo.model.Author;
import org.wcci.demo.model.Book;
import org.wcci.demo.repositories.AuthorRepository;
import org.wcci.demo.repositories.BookRepository;
import org.wcci.demo.repositories.CampusRepository;

import java.util.Collection;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private AuthorRepository authorRepo;
    @Autowired
    private CampusRepository campusRepo;

    @GetMapping("/api/books/")
    public Collection<Book> retrieveAll() {
        return (Collection<Book>) bookRepo.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Book retrieveById(@PathVariable long id) {
        return bookRepo.findById(id).get();
    }

    @PostMapping("/api/books/")
    public Book add(@RequestBody Book bookToAdd) {
        if (bookToAdd.getCampus() != null && bookToAdd.getCampus().getId() == null) {
            campusRepo.save(bookToAdd.getCampus());
        }
        for (Author authorToAdd : bookToAdd.getAuthors()) {
            if (authorToAdd.getId() == null) {
                authorRepo.save(authorToAdd);
            }
        }
        return bookRepo.save(bookToAdd);
    }

    @DeleteMapping("/api/books/{id}")
    public void remove(@PathVariable long id) {
        bookRepo.deleteById(id);
    }
}
