package jaep.springframework.petclinic.services.jpa;

import jaep.springframework.petclinic.model.PetType;
import jaep.springframework.petclinic.repositories.PetTypeRepository;
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
class PetTypeJpaServiceTest {
    @InjectMocks
    PetTypeJpaService service;

    @Mock
    PetTypeRepository repository;

    PetType p1;

    @BeforeEach
    void setUp() {
        p1 = PetType.builder().name("one").build();
        p1.setId(1L);
    }

    @Test
    void findAll() {
        Set<PetType> pets = new HashSet<>();
        pets.add(p1);

        when(repository.findAll()).thenReturn(pets);

        Set<PetType> petsReturn = service.findAll();

        assertNotNull(petsReturn);
        assertEquals(pets.size(), petsReturn.size());
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

        PetType petResult = service.save(p1);

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