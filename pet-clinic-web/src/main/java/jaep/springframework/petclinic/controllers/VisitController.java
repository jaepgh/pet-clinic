package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.Visit;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWIthVisit(@PathVariable("petId") Long petId, Model model){
        Pet pet = petService.findByID(petId);
        model.addAttribute("pet", pet);
        Visit visit = Visit.builder().build();
        pet.getVisits().add(visit);

        return visit;
    }

}
