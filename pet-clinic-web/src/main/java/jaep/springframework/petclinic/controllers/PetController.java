package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.PetType;
import jaep.springframework.petclinic.services.OwnerService;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping({"/owners/{ownerId}"})
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetService pets;
    private final OwnerService owners;
    private final PetTypeService petTypes;

    public PetController(PetService pets, OwnerService owners, PetTypeService petTypes) {
        this.pets = pets;
        this.owners = owners;
        this.petTypes = petTypes;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.petTypes.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return this.owners.findByID(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
}
