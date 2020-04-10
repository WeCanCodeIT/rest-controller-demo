package org.wcci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wcci.demo.model.Campus;
import org.wcci.demo.repositories.CampusRepository;

import java.util.Collection;

@RestController
public class CampusController {
    @Autowired
    private CampusRepository campusRepo;

    @GetMapping("/api/campuses/")
    public Collection<Campus> retrieveAll() {
        return (Collection<Campus>) campusRepo.findAll();
    }

    @GetMapping("/api/campuses/{id}")
    public Campus retrieveById(@PathVariable long id) {
        return campusRepo.findById(id).get();
    }

    @PostMapping("api/campuses/")
    public Campus add(Campus newCampus) {
        return campusRepo.save(newCampus);
    }
}
