package org.wcci.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.wcci.demo.model.Campus;
import org.wcci.demo.repositories.CampusRepository;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CampusControllerTest {
    @Mock
    private CampusRepository campusRepo;
    @InjectMocks
    private CampusController underTest;
    private Campus testCampus;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testCampus = new Campus("Test Town");
    }

    @Test
    public void shouldReturnAllCampuses() {
        when(campusRepo.findAll()).thenReturn(Collections.singletonList(testCampus));
        Collection<Campus> result = underTest.retrieveAll();
        assertThat(result).containsOnly(testCampus);
    }

    @Test
    public void fetchAllEndpointReturnsAllBooks() throws Exception {
        when(campusRepo.findAll()).thenReturn(Collections.singletonList(testCampus));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mockMvc.perform(get("/api/campuses/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].location", is("Test Town")));

    }

//    @Test
//    public void retrieveByIdShouldReturnASpecificCampusById() {
//        when(campusRepo.fetchById(1L)).thenReturn(testCampus);
//        Campus result = underTest.retrieveById(1L);
//        assertThat(result).isEqualTo(testCampus);
//    }
//
//    @Test
//    public void fetchByIdEndpointReturnASpecificBook() throws Exception {
//        when(campusRepo.fetchById(1L)).thenReturn(testCampus);
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
//        mockMvc.perform(get("/api/campuses/1"))
//               .andExpect(status().isOk())
//               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//               .andExpect(jsonPath("$.location", is("Test Town")));
//    }
//
//    @Test
//    public void addShouldAddTheGivenCampusToTheApi() {
//        Campus newCampus = new Campus("Testerville");
//        when(campusRepo.store(newCampus)).thenReturn(newCampus);
//        Campus addedCampus = underTest.add(newCampus);
//        assertThat(addedCampus).isEqualTo(newCampus);
//    }
//
//    @Test
//    public void addEndpointReturnsTheNewCampus() throws Exception {
//        Campus newCampus = new Campus("Testerville");
//        when(campusRepo.store(newCampus)).thenReturn(newCampus);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String campusJson = mapper.writeValueAsString(newCampus);
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
//        mockMvc.perform(post("/api/campuses/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("utf-8")
//                .content(campusJson))
//               .andDo(print())
//               .andExpect(status().isOk())
//               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//               .andExpect(jsonPath("$.location", is("Testerville")));
//    }
}
