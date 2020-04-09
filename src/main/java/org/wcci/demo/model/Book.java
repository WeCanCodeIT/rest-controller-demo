package org.wcci.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @JsonIgnore
    @ManyToOne
    private Campus campus;
    @ManyToMany
    private Set<Author> authors;

    protected Book() {
    }

    public Book(String title, Campus campus, Author... authors) {
        this.title = title;
        this.campus = campus;
        this.authors = new HashSet<>(Arrays.asList(authors));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Campus getCampus() {
        return campus;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(campus, book.campus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, campus);
    }
}
