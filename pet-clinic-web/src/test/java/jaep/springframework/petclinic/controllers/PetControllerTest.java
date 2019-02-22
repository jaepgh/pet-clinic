package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.PetType;
import jaep.springframework.petclinic.services.OwnerService;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    @Mock
    PetService petService;
    @Mock
    OwnerService ownerService;
    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController controller;

    MockMvc mockMvc;

    Owner owner;

    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        controller = new PetController(petService, ownerService, petTypeService);
        owner = Owner.builder().id(2L).build();
        petTypes = new HashSet<>();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void initCreationForm() throws Exception {

        when(ownerService.findByID(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);


        mockMvc.perform(get("/owners/2/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processCreationForm() throws Exception {

        when(ownerService.findByID(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/2/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/2"));

        verify(petService, times(1)).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        when(ownerService.findByID(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findByID(anyLong())).thenReturn(Pet.builder().id(1L).build());


        mockMvc.perform(get("/owners/2/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.findByID(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/2/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/2"));

        verify(petService, times(1)).save(any());
    }
}