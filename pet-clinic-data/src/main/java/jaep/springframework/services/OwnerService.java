package jaep.springframework.services;

import jaep.springframework.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerService extends CrudRepository<Owner, Long> {
    Owner findByLastName(String lastName);
}
