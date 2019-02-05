package jaep.springframework.petclinic.services.jpa;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.Vet;
import jaep.springframework.petclinic.model.Visit;
import jaep.springframework.petclinic.repositories.VisitRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitJpaServiceTest {

    @InjectMocks
    VisitJpaService service;

    @Mock
    VisitRepository repository;

    Visit v1;

    @BeforeEach
    void setUp() {
        Owner owner = Owner.builder().id(1L).build();

        Pet pet = Pet.builder().owner(owner).build();
        pet.setId(1L);

        v1 = Visit.builder().pet(pet).build();
        v1.setId(1L);
    }

    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>();
        visits.add(v1);

        when(repository.findAll()).thenReturn(visits);

        Set<Visit> result = service.findAll();

        assertNotNull(result);
        assertEquals(visits.size(), result.size());
        verify(repository).findAll();
    }

    @Test
    void findByID() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(v1));

        assertNotNull(service.findByID(v1.getId()));
        verify(repository, times(1)).findById(anyLong());
    }

    void save() {
        when(repository.save(any())).thenReturn(v1);

        Visit result = service.save(v1);

        assertNotNull(result);
        assertEquals(v1.getId(), result.getId());

        verify(repository, times(1)).save(any());

    }

    @Test
    void delete() {
        service.delete(v1);

        assertEquals(0, service.findAll().size());
        verify(repository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(v1.getId());

        assertEquals(0, service.findAll().size());
        verify(repository).deleteById(anyLong());
    }
}