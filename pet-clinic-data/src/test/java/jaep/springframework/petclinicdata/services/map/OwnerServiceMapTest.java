package jaep.springframework.petclinicdata.services.map;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.services.map.OwnerServiceMap;
import jaep.springframework.petclinic.services.map.PetServiceMap;
import jaep.springframework.petclinic.services.map.PetTypeServiceMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(),new PetServiceMap());

        ownerServiceMap.save(Owner.builder().id(1L).firstName("Juan").lastName("Perez").build());
        ownerServiceMap.save(Owner.builder().id(2L).firstName("John").lastName("Doe").build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();

        assertEquals(2,owners.size());
    }

    @Test
    void findByID() {
        Long searchId = 2L;
        assertEquals("John", ownerServiceMap.findByID(searchId).getFirstName());
    }

    @Test
    void save() {
        Long testId = 3L;
        ownerServiceMap.save(Owner.builder().id(testId).build());
        assertEquals(testId, ownerServiceMap.findByID(testId).getId());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(2L);
        assertEquals(1, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        Owner owner = Owner.builder().id(4L).firstName("Other").lastName("Second").build();

        ownerServiceMap.save(owner);
        assertNotNull(ownerServiceMap.findByID(4L));


        ownerServiceMap.delete(owner);
        assertNull(ownerServiceMap.findByID(4L));
    }

    @Test
    void findByLastName() {
        String lastName = "Doe";
        Owner owner = ownerServiceMap.findByLastName(lastName);

        assertNotNull(owner);
    }
}