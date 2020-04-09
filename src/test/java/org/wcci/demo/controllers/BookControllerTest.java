//package org.wcci.demo.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.wcci.library.model.Author;
//import org.wcci.library.model.Book;
//import org.wcci.library.model.Campus;
//import org.wcci.library.storage.BookStorage;
//
//import java.util.Collection;
//import java.util.Collections;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class BookControllerTest {
//
//    private BookStorage bookStorage;
//    private BookController underTest;
//    private Book testBook;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        bookStorage = mock(BookStorage.class);
//        underTest = new BookControllerImpl(bookStorage);
//        testBook = new Book("Testing In Spring", new Campus("Test Town"), new Author("Joe", "Testa"));
//        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
//
//    }
//
//    @Test
//    public void shouldReturnAllBooks() {
//        when(bookStorage.fetchAll()).thenReturn(Collections.singletonList(testBook));
//        Collection<Book> result = underTest.retrieveAll();
//        assertThat(result).containsOnly(testBook);
//    }
//
//    @Test
//    public void fetchAllEndpointReturnsAllBooks() throws Exception {
//        when(bookStorage.fetchAll()).thenReturn(Collections.singletonList(testBook));
//        mockMvc.perform(get("/api/books/"))
//               .andExpect(status().isOk())
//               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//               .andExpect(jsonPath("$", hasSize(1)))
//               .andExpect(jsonPath("$[0].title", is("Testing In Spring")));
//    }
//
//    @Test
//    public void shouldReturnASpecificBook() {
//        when(bookStorage.fetchById(1L)).thenReturn(testBook);
//        Book result = underTest.retrieveById(1L);
//        assertThat(result).isEqualTo(testBook);
//    }
//
//    @Test
//    public void fetchByIdEndpointReturnASpecificBook() throws Exception {
//        when(bookStorage.fetchById(1L)).thenReturn(testBook);
//        mockMvc.perform(get("/api/books/1"))
//               .andExpect(status().isOk())
//               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//               .andExpect(jsonPath("$.title", is("Testing In Spring")));
//    }
//
//    @Test
//    public void addWillAddBookToApi() {
//        Campus testCampus = new Campus("Test Town");
//        Author testAuthor = new Author("Testy", "Exammer");
//        Book bookToAdd = new Book("On Testing", testCampus, testAuthor);
//        when(bookStorage.store(bookToAdd)).thenReturn(bookToAdd);
//        Book addedBook = underTest.add(bookToAdd);
//        assertThat(addedBook).isEqualTo(bookToAdd);
//    }
//
//    @Test
//    public void addEndPointWillReturnNewlyAddedBook() throws Exception {
//        Campus testCampus = new Campus("Test Town");
//        Author testAuthor = new Author("Testy", "Exammer");
//        Book bookToAdd = new Book("On Testing", testCampus, testAuthor);
//        when(bookStorage.store(any(Book.class))).thenReturn(bookToAdd);
//        ObjectMapper mapper = new ObjectMapper();
//        String bookJson = mapper.writeValueAsString(bookToAdd);
//        mockMvc.perform(post("/api/books/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(bookJson)
//                .characterEncoding("utf-8"))
//               .andDo(print())
//               .andExpect(status().isOk())
//               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//               .andExpect(jsonPath("$.title", is("On Testing")))
//               .andExpect(jsonPath("$.authors[0].firstName", is("Testy")));
//    }
//
//    @Test
//    public void removeShouldRemoveBookFromApi() {
//        underTest.remove(1L);
//        verify(bookStorage).delete(1L);
//    }
//
//    @Test
//    public void removeEndpointIsWiredUp() throws Exception {
//        mockMvc.perform(delete("/api/books/1/"))
//               .andExpect(status().isOk());
//        verify(bookStorage).delete(1L);
//    }
//}
