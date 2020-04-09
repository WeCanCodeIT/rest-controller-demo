package org.wcci.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wcci.demo.model.Author;
import org.wcci.demo.model.Book;
import org.wcci.demo.model.Campus;
import org.wcci.demo.repositories.AuthorRepository;
import org.wcci.demo.repositories.BookRepository;
import org.wcci.demo.repositories.CampusRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaWiring {
    @Autowired
    private CampusRepository campusRepo;
    @Autowired
    private AuthorRepository authorRepo;
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private TestEntityManager entityManager;
    private Campus testCampus;
    private Author testAuthor1;
    private Author testAuthor2;
    private Book testBook1;
    private Book testBook2;

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @Before
    public void setUp() {
        testCampus = new Campus("Clintonville");
        testAuthor1 = new Author("Testy", "McTester");
        testAuthor2 = new Author("Testa", "Exama");
        testBook1 = new Book("Testing Stuff", testCampus, testAuthor1);
        testBook2 = new Book("Testing Stuff, Again", testCampus, testAuthor1, testAuthor2);
        campusRepo.save(testCampus);
        authorRepo.save(testAuthor1);
        authorRepo.save(testAuthor2);
        bookRepo.save(testBook1);
        bookRepo.save(testBook2);
        flushAndClear();
    }

    @Test
    public void campusShouldHaveBooks() {
        Campus retrievedCampus = campusRepo.findById(testCampus.getId()).get();
        assertThat(retrievedCampus.getBooks()).contains(testBook1, testBook2);
    }

    @Test
    public void BooksAndAuthorsHaveAManyToManyRelationship() {
        Author retrievedAuthor1 = authorRepo.findById(testAuthor1.getId()).get();
        Author retrievedAuthor2 = authorRepo.findById(testAuthor2.getId()).get();
        assertThat(retrievedAuthor1.getBooks()).contains(testBook1, testBook2);
        assertThat(retrievedAuthor2.getBooks()).contains(testBook2);

    }
}
