package org.wcci.demo;

import org.springframework.stereotype.Component;
import org.wcci.demo.model.Author;
import org.wcci.demo.model.Book;
import org.wcci.demo.model.Campus;
import org.wcci.demo.repositories.AuthorRepository;
import org.wcci.demo.repositories.BookRepository;
import org.wcci.demo.repositories.CampusRepository;

@Component
public class Populator implements org.springframework.boot.CommandLineRunner {
    private final AuthorRepository authorStorage;
    private final BookRepository bookStorage;
    private final CampusRepository campusStorage;

    public Populator(AuthorRepository authorStorage, BookRepository bookStorage, CampusRepository campusStorage) {
        this.authorStorage = authorStorage;
        this.bookStorage = bookStorage;
        this.campusStorage = campusStorage;
    }

    @Override
    public void run(String... args) {
        Author kathy = new Author("Kathy", "Sierra");
        authorStorage.save(kathy);
        Author bert = new Author("Bert", "Bates");
        authorStorage.save(bert);
        Author ben = new Author("Benjamin", "Williams");
        authorStorage.save(ben);
        Author kent = new Author("Kent", "Beck");
        authorStorage.save(kent);
        Author martin = new Author("Martin", "Fowler");
        authorStorage.save(martin);
        Author micah = new Author("Micah", "Martin");
        authorStorage.save(micah);
        Author eric = new Author("Eric", "Freeman");
        authorStorage.save(eric);

        Campus columbus = new Campus("Columbus");
        campusStorage.save(columbus);
        Campus cleveland = new Campus("Cleveland");
        campusStorage.save(cleveland);

        Book headFirstJava = new Book("Head First Java", columbus, kathy, bert);
        bookStorage.save(headFirstJava);
        Book headFirstDesignPatterns = new Book("Head First Design Patterns", columbus, eric, bert);
        bookStorage.save(headFirstDesignPatterns);
        Book badAss = new Book("Badass: Making Users Awesome", columbus, kathy);
        bookStorage.save(badAss);
        Book tddByExample = new Book("Test Driven Development: By Example", columbus, kent);
        bookStorage.save(tddByExample);
        Book refactoring = new Book("Refactoring", columbus, martin);
        bookStorage.save(refactoring);
        Book apppc = new Book("Agile Principles, Patterns, and Practices in C#", cleveland, micah);
        bookStorage.save(apppc);
        Book headFirstDesignPatternsClev = new Book("Head First Design Patterns", cleveland, eric, bert);
        bookStorage.save(headFirstDesignPatternsClev);
    }

}
