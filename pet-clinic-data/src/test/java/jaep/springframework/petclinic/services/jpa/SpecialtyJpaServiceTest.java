package jaep.springframework.petclinic.services.jpa;

import jaep.springframework.petclinic.model.Specialty;
import jaep.springframework.petclinic.repositories.SpecialtyRepository;
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
class SpecialtyJpaServiceTest {
    @InjectMocks
    SpecialtyJpaService service;

    @Mock
    SpecialtyRepository repository;

    Specialty s1;

    @BeforeEach
    void setUp() {
        s1 = Specialty.builder().description("S1").build();
        s1.setId(1L);
    }

    @Test
    void findAll() {
        Set<Specialty> specialtySet = new HashSet<>();
        specialtySet.add(s1);

        when(repository.findAll()).thenReturn(specialtySet);

        Set<Specialty> result = service.findAll();

        assertNotNull(result);
        assertEquals(specialtySet.size(), result.size());
    }

    @Test
    void findByID() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(s1));

        assertNotNull(service.findByID(s1.getId()));
        verify(repository).findById(anyLong());
    }

    @Test
    void save() {
        when(repository.save(any())).thenReturn(s1);

        Specialty saved = service.save(s1);
        assertNotNull(saved);
        assertEquals(s1.getId(), saved.getId());

        verify(repository, times(1)).save(any());
    }

    void delete() {
        service.delete(s1);

        assertEquals(0,service.findAll().size());
        verify(repository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(s1.getId());

        assertEquals(0,service.findAll().size());
        verify(repository).deleteById(anyLong());
    }
}