package jaep.springframework.petclinic.repositories;

import jaep.springframework.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Owner findByLastName(String lastName);
    Set<Owner> findAllByLastNameContainingIgnoreCase(String regex);
}
