package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.services.OwnerService;
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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
       owners = new HashSet<>();
       owners.add(Owner.builder().id(1L).build());
       owners.add(Owner.builder().id(2L).build());

       mockMvc = MockMvcBuilders
               .standaloneSetup(controller)
               .build();
    }

    @Test
    void showOwner() throws Exception {
        when(ownerService.findByID(anyLong()))
                .thenReturn(Owner.builder()
                                    .id(1L)
                                    .build());

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersDetails"))
                .andExpect(model().attribute("owner", hasProperty("id",is(1L))));
    }

    @Test
    void processFindFormReturnMany() throws Exception{
        Set<Owner> ownerTwo = new HashSet<>();
        ownerTwo.add(Owner.builder().id(4L).build());
        ownerTwo.add(Owner.builder().id(5L).build());

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerTwo);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections",hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception{
        Set<Owner> ownerOne = new HashSet<>();
        ownerOne.add(Owner.builder().id(4L).build());

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerOne);

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/4"));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

    }
}