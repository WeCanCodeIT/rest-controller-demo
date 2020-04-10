package org.wcci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wcci.demo.model.Book;
import org.wcci.demo.repositories.BookRepository;

import java.util.Collection;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepo;

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
        return bookRepo.save(bookToAdd);
    }
    @DeleteMapping("/api/books/{id}")
    public void remove(@PathVariable long id) {
        bookRepo.deleteById(id);
    }
}
