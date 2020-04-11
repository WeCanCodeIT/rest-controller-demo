package org.wcci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wcci.demo.model.Book;
import org.wcci.demo.model.Campus;
import org.wcci.demo.repositories.BookRepository;
import org.wcci.demo.repositories.CampusRepository;

import java.util.Collection;

@RestController
public class CampusController {
    @Autowired
    private CampusRepository campusRepo;
    @Autowired
    private BookRepository bookRepo;
    @GetMapping("/api/campuses/")
    public Collection<Campus> retrieveAll() {
        return (Collection<Campus>) campusRepo.findAll();
    }

    @GetMapping("/api/campuses/{id}")
    public Campus retrieveById(@PathVariable long id) {
        return campusRepo.findById(id).get();
    }

    @PostMapping("/api/campuses/")
    public Campus add(Campus newCampus) {
        return campusRepo.save(newCampus);
    }

    @PatchMapping("/api/campuses/{campusId}/book/")
    public Campus addBookToCampus(@RequestBody Book testBook, @PathVariable long campusId) {
        Campus campus = campusRepo.findById(campusId).get();
        bookRepo.save(testBook);
        campus.addBook(testBook);
        campusRepo.save(campus);
        return campus;
    }
}
