package jaep.springframework.petclinic.services.map;

import jaep.springframework.petclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceMapTest {

    PetServiceMap service;
    @BeforeEach
    void setUp() {
        service = new PetServiceMap();
        Pet p1 = new Pet();
        p1.setId(3L);
        service.save(p1);

        Pet p2 = new Pet();
        p2.setId(4L);
        service.save(p2);
    }

    @Test
    void findAll() {
        assertEquals(2, service.findAll().size());
    }

    @Test
    void deleteById() {
        service.deleteById(3L);
        assertNull(service.findByID(3L));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void delete() {
        Pet p1 = new Pet();
        p1.setId(5L);

        service.save(p1);
        assertNotNull(service.findByID(5L));

        service.delete(p1);
        assertNull(service.findByID(5L));
        assertEquals(2, service.findAll().size());
    }

    @Test
    void findByID() {
        Pet aux = service.findByID(3L);
        assertNotNull(aux);
    }

    @Test
    void save() {
        Long id = 6L;
        Pet p1 = new Pet();
        p1.setId(id);

        service.save(p1);
        assertEquals(id, service.findByID(6L).getId());
        assertEquals(3,service.findAll().size());
    }
}