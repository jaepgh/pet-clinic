package jaep.springframework.petclinic.repositories;

import jaep.springframework.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

}
