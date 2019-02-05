package jaep.springframework.petclinic.services.map;

import jaep.springframework.petclinic.model.Vet;
import jaep.springframework.petclinic.services.SpecialtyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class VetServiceMapTest {
    VetServiceMap service;

    @Autowired
    SpecialtyService specialtyService;

    Vet v1;
    Vet v2;

    @BeforeEach
    void setUp() {
        service = new VetServiceMap(specialtyService);

        v1 = new Vet();
        v2 = new Vet();

        v1.setId(2L);
        v2.setId(3L);

        service.save(v1);
        service.save(v2);
    }

    @Test
    void findAll() {
        assertEquals(2, service.findAll().size());
    }

    @Test
    void findByID() {
        Long id = 2L;

        assertNotNull(service.findByID(id));
        assertEquals(id, service.findByID(id).getId());
    }

    @Test
    void save() {
        service.save(new Vet());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void delete() {
        service.delete(v1);

        assertNull(service.findByID(4L));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void deleteById() {
        service.deleteById(2L);

        assertNull(service.findByID(2L));
        assertEquals(1, service.findAll().size());
    }
}