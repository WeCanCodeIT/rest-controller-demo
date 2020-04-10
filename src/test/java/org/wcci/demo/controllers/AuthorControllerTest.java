package org.wcci.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.wcci.demo.model.Author;
import org.wcci.demo.repositories.AuthorRepository;


import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorControllerTest {
    @Mock
    private AuthorRepository authorRepo ;
    @InjectMocks
    private AuthorController underTest;
    private Author testAuthor;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
       MockitoAnnotations.initMocks(this);
        testAuthor = new Author("Joe", "Testa");
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void shouldReturnAllAuthors() {
        when(authorRepo.findAll()).thenReturn(Collections.singletonList(testAuthor));
        Collection<Author> result = underTest.retrieveAll();
        assertThat(result).containsOnly(testAuthor);
    }

    @Test
    public void retrieveAllEndPointReturnsAllAuthors() throws Exception {
        when(authorRepo.findAll()).thenReturn(Collections.singletonList(testAuthor));
        mockMvc.perform(get("/api/authors/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].firstName", is("Joe")))
               .andExpect(jsonPath("$[0].lastName", is("Testa")));
    }

    @Test
    public void shouldReturnAuthorById() {
        when(authorRepo.findById(1L)).thenReturn(Optional.of(testAuthor));
        assertThat(underTest.retrieveById(1L)).isEqualTo(testAuthor);
    }

    @Test
    public void retrieveByIdEndpointFetchesAuthorById() throws Exception {
        when(authorRepo.findById(1L)).thenReturn(Optional.of(testAuthor));
        mockMvc.perform(get("/api/authors/1"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.firstName", is("Joe")))
               .andExpect(jsonPath("$.lastName", is("Testa")));
    }

    @Test
    public void addShouldAddTheGivenCampusToTheApi() {
        Author newAuthor = new Author("Tester", "Testy");
        when(authorRepo.save(newAuthor)).thenReturn(newAuthor);
        Author addedCampus = underTest.add(newAuthor);
        assertThat(addedCampus).isEqualTo(newAuthor);
    }

    @Test
    public void addEndpointReturnsTheNewAuthor() throws Exception {
        Author newAuthor = new Author("Tester", "Testy");
        when(authorRepo.save(newAuthor)).thenReturn(newAuthor);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String campusJson = mapper.writeValueAsString(newAuthor);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mockMvc.perform(post("/api/authors/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("utf-8")
                .content(campusJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.firstName", is("Tester")))
               .andExpect(jsonPath("$.lastName", is("Testy")));
    }
}
