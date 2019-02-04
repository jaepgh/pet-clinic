package jaep.springframework.petclinicdata.services.jpa;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.repositories.OwnerRepository;
import jaep.springframework.petclinic.repositories.PetRepository;
import jaep.springframework.petclinic.repositories.PetTypeRepository;
import jaep.springframework.petclinic.services.jpa.OwnerJpaService;
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
class OwnerJpaServiceTest {
    @InjectMocks
    OwnerJpaService ownerJpaService;

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).lastName("Doe").build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);

        assertEquals("Doe",ownerJpaService.findByLastName("Doe").getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(Owner.builder().id(2L).build());
        owners.add(Owner.builder().id(3L).build());

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> ownersReturn = ownerJpaService.findAll();

        assertNotNull(ownersReturn);
        assertEquals(2, ownersReturn.size());
    }

    @Test
    void findByID() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        Owner result = ownerJpaService.findByID(1L);
        assertNotNull(result);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);

        Owner result = ownerJpaService.save(owner);

        assertNotNull(result);
        assertEquals(owner.getId(), result.getId());

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerJpaService.delete(owner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}