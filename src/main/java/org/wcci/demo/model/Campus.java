package org.wcci.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Campus {
    @Id
    @GeneratedValue
    private Long id;
    private String location;

    @OneToMany(mappedBy = "campus")
    private Set<Book> books;

    protected Campus() {
    }

    public Campus(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campus campus = (Campus) o;
        return Objects.equals(id, campus.id) &&
                Objects.equals(location, campus.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location);
    }

    @Override
    public String toString() {
        return "Campus{" +
                "id=" + id +
                ", location='" + location + '\'' +
                '}';
    }

    public void addBook(Book book) {
        if(books == null){
            books = new HashSet<>();
        }
        books.add(book);
    }
}
