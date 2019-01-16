package jaep.springframework.petclinic.services;

import jaep.springframework.petclinic.model.Owner;
import org.springframework.stereotype.Service;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
