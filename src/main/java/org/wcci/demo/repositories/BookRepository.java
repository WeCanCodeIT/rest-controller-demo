package org.wcci.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wcci.demo.model.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
}
