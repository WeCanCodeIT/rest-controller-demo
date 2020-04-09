package org.wcci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
