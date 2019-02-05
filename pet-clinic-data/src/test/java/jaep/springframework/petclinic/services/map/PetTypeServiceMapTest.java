package jaep.springframework.petclinic.services.map;

import jaep.springframework.petclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeServiceMapTest {
    PetTypeServiceMap service;
    PetType p1;
    PetType p2;

    @BeforeEach
    void setUp() {
        service = new PetTypeServiceMap();
        p1 = new PetType();
        p2 = new PetType();

        p1.setId(4L);
        p2.setId(5L);

        service.save(p1);
        service.save(p2);
    }

    @Test
    void findAll() {
        assertEquals(2, service.findAll().size());
    }

    @Test
    void findByID() {
        Long id = 4L;

        assertNotNull(service.findByID(id));
        assertEquals(id, service.findByID(id).getId());
    }

    @Test
    void save() {
        service.save(new PetType());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void delete() {
        service.delete(p1);

        assertNull(service.findByID(4L));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void deleteById() {
        service.deleteById(4L);

        assertNull(service.findByID(4L));
        assertEquals(1, service.findAll().size());
    }
}