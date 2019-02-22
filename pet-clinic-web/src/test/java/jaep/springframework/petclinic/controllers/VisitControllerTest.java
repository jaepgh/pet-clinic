package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.Visit;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final String VIEWS_VISITS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";

    @Mock
    VisitService visitService;

    @Mock
    PetService petService;

    @InjectMocks
    VisitController controller;

    Pet pet;
    Visit visit;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        pet = Pet.builder().id(2L).visits(new HashSet<>()).build();

        when(petService.findByID(anyLong())).thenReturn(pet);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initNewVisitForm() throws Exception {

        mockMvc.perform(get("/owners/*/pets/2/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_VISITS_CREATE_OR_UPDATE_FORM))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processNewVisitForm() throws Exception {
        mockMvc.perform(post("/owners/*/pets/2/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2019-01-01")
                .param("description", "some"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));

    }
}