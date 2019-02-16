package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.PetType;
import jaep.springframework.petclinic.services.OwnerService;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping({"/owners/{ownerId}"})
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final OwnerService owners;
    private final PetTypeService petTypes;

    public PetController(PetService petService, OwnerService owners, PetTypeService petTypes) {
        this.petService = petService;
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

    @GetMapping({"/pets/new"})
    public String initCreationForm(Owner owner, Model model){
        Pet pet = Pet.builder().build();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping({"/pets/new"})
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model){

        if(StringUtils.hasLength(pet.getName()) && pet.isNew()
                && owner.getPet(pet.getName(),true) != null){
            result.rejectValue("name","duplicate", "already exist");
        }

        owner.getPets().add(pet);
        pet.setOwner(owner);

        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            this.petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping({"pets/{petId}/edit"})
    public String initUpdateForm(@PathVariable("petId") Long petId, Model model){
        model.addAttribute("pet", this.petService.findByID(petId));
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping({"pets/{petId}/edit"})
    public String processUpdateForm(@PathVariable("petId") Long petId, Model model,
                                    Owner owner, @Valid Pet pet, BindingResult result){
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getPets().add(pet);
            this.petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
