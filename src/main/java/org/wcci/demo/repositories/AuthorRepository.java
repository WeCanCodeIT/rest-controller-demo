package org.wcci.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wcci.demo.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
