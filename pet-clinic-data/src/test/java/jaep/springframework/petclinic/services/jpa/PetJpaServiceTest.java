package jaep.springframework.petclinic.services.jpa;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetJpaServiceTest {

    @InjectMocks
    PetJpaService service;

    @Mock
    PetRepository repository;

    Pet p1;

    @BeforeEach
    void setUp() {
        Owner o1 = Owner.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        p1 = Pet.builder()
                .owner(o1)
                .name("Kitty")
                .build();
        p1.setId(2L);
    }

    @Test
    void findAll() {
        Set<Pet> pets = new HashSet<>();
        pets.add(p1);

        when(repository.findAll()).thenReturn(pets);

        Set<Pet> petsReturn = service.findAll();

        assertNotNull(petsReturn);
        assertEquals(petsReturn.size(),service.findAll().size());

    }

    @Test
    void findByID() {
        when(repository.findById(any())).thenReturn(Optional.of(p1));

        assertNotNull(service.findByID(p1.getId()));
        verify(repository).findById(anyLong());
    }

    @Test
    void save() {
        when(repository.save(any())).thenReturn(p1);

        Pet petResult = service.save(p1);

        assertNotNull(petResult);
        assertEquals(p1.getId(),petResult.getId());

        verify(repository).save(any());
    }

    @Test
    void delete() {
        service.delete(p1);

        assertEquals(0,service.findAll().size());
        verify(repository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(p1.getId());

        assertEquals(0,service.findAll().size());
        verify(repository).deleteById(anyLong());
    }
}