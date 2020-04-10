package org.wcci.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.wcci.demo.model.Author;
import org.wcci.demo.model.Book;
import org.wcci.demo.model.Campus;
import org.wcci.demo.repositories.BookRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {
    @Mock
    private BookRepository bookRepo;
    @InjectMocks
    private BookController underTest;
    private Book testBook;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testBook = new Book("Testing In Spring", new Campus("Test Town"), new Author("Joe", "Testa"));
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();

    }

    @Test
    public void shouldReturnAllBooks() {
        when(bookRepo.findAll()).thenReturn(Collections.singletonList(testBook));
        Collection<Book> result = underTest.retrieveAll();
        assertThat(result).containsOnly(testBook);
    }

    @Test
    public void fetchAllEndpointReturnsAllBooks() throws Exception {
        when(bookRepo.findAll()).thenReturn(Collections.singletonList(testBook));
        mockMvc.perform(get("/api/books/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Testing In Spring")));
    }

    @Test
    public void shouldReturnASpecificBook() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(testBook));
        Book result = underTest.retrieveById(1L);
        assertThat(result).isEqualTo(testBook);
    }

    @Test
    public void fetchByIdEndpointReturnASpecificBook() throws Exception {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(testBook));
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("Testing In Spring")));
    }

    @Test
    public void addWillAddBookToApi() {
        Campus testCampus = new Campus("Test Town");
        Author testAuthor = new Author("Testy", "Exammer");
        Book bookToAdd = new Book("On Testing", testCampus, testAuthor);
        when(bookRepo.save(bookToAdd)).thenReturn(bookToAdd);
        Book addedBook = underTest.add(bookToAdd);
        assertThat(addedBook).isEqualTo(bookToAdd);
        verify(bookRepo).save(bookToAdd);
    }

    @Test
    public void addEndPointWillReturnNewlyAddedBook() throws Exception {
        Campus testCampus = new Campus("Test Town");
        Author testAuthor = new Author("Testy", "Exammer");
        Book bookToAdd = new Book("On Testing", testCampus, testAuthor);
        when(bookRepo.save(any(Book.class))).thenReturn(bookToAdd);
        ObjectMapper mapper = new ObjectMapper();
        String bookJson = mapper.writeValueAsString(bookToAdd);
        mockMvc.perform(post("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("On Testing")))
                .andExpect(jsonPath("$.authors[0].firstName", is("Testy")));
    }

    @Test
    public void removeShouldRemoveBookFromApi() {
        underTest.remove(1L);
        verify(bookRepo).deleteById(1L);
    }

    @Test
    public void removeEndpointIsWiredUp() throws Exception {
        mockMvc.perform(delete("/api/books/1/"))
                .andExpect(status().isOk());
        verify(bookRepo).deleteById(1L);
    }
}
