package jaep.springframework.services;

import jaep.springframework.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetService extends CrudRepository<Vet, Long> {
}
