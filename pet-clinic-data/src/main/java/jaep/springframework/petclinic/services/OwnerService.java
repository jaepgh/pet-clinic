package jaep.springframework.petclinic.services;

import jaep.springframework.petclinic.model.Owner;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
    Set<Owner> findAllByLastNameLike(String lastName);
}
