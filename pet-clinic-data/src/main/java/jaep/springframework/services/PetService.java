package jaep.springframework.services;

import jaep.springframework.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetService extends CrudRepository<Pet, Long> {
}
