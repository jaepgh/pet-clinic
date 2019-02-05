package jaep.springframework.petclinic.services.jpa;

import jaep.springframework.petclinic.model.Vet;
import jaep.springframework.petclinic.repositories.VetRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetJpaServiceTest {

    @InjectMocks
    VetJpaService service;

    @Mock
    VetRepository repository;

    Vet v1;

    @BeforeEach
    void setUp() {
        v1 = Vet.builder().build();
        v1.setId(1L);
    }

    @Test
    void findAll() {
        Set<Vet> vets = new HashSet<>();
        vets.add(v1);

        when(repository.findAll()).thenReturn(vets);

        Set<Vet> result = service.findAll();

        assertNotNull(result);
        assertEquals(vets.size(), result.size());
        assertTrue(result.contains(v1));

        verify(repository, times(1)).findAll();
    }

    @Test
    void findByID() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(v1));

        assertNotNull(service.findByID(v1.getId()));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        when(repository.save(any())).thenReturn(v1);

        Vet result = service.save(v1);

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