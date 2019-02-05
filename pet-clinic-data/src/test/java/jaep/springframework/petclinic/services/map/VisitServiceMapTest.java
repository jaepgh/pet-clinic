package jaep.springframework.petclinic.services.map;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceMapTest {

    VisitServiceMap service;

    Visit v1;
    Visit v2;

    @BeforeEach
    void setUp() {
        service = new VisitServiceMap();

        v1 = new Visit();
        v2 = new Visit();

        Owner o1 = Owner.builder().id(1L).build();
        Pet p1 = Pet.builder().owner(o1).build();
        p1.setId(1L);

        v1.setId(4L);
        v1.setPet(p1);
        v2.setId(5L);
        v2.setPet(p1);

        service.save(v1);
        service.save(v2);

    }

    @Test
    void findAll() {
        assertEquals(2,service.findAll().size());
    }

    @Test
    void deleteById() {
        service.deleteById(4L);

        assertEquals(1, service.findAll().size());
        assertNull(service.findByID(4L));
    }

    @Test
    void delete() {
        service.delete(v1);

        assertEquals(1, service.findAll().size());
        assertNull(service.findByID(4L));
    }

    @Test
    void save() {
        Visit v3 = new Visit();
        v3.setId(7L);

        Owner o1 = Owner.builder().id(1L).build();
        Pet p1 = Pet.builder().owner(o1).build();
        p1.setId(1L);

        v3.setPet(p1);
        service.save(v3);

        assertEquals(3, service.findAll().size());
        assertNotNull(service.findByID(7L));

    }

    @Test
    void findByID() {
        Long id = 4L;

        assertNotNull(service.findByID(id));
        assertEquals(id, service.findByID(id).getId());
    }
}