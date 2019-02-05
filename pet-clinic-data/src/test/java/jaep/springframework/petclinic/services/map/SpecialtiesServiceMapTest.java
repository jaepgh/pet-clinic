package jaep.springframework.petclinic.services.map;

import jaep.springframework.petclinic.model.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtiesServiceMapTest {

    SpecialtiesServiceMap service;
    Specialty s1;
    Specialty s2;

    @BeforeEach
    void setUp() {
        service = new SpecialtiesServiceMap();
        s1 = new Specialty();
        s2 = new Specialty();

        s1.setId(4L);
        s2.setId(5L);

        service.save(s1);
        service.save(s2);
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
        service.save(new Specialty());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void delete() {
        service.delete(s1);

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