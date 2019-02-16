package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.Visit;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    private static final String VIEWS_VISITS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";

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
        visit.setPet(pet);

        return visit;
    }

    @GetMapping({"/owners/*/pets/{petId}/visits/new"})
    public String initNewVisitForm(@PathVariable("petId") Long petId){
        return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping({"/owners/{ownerId}/pets/{petId}/visits/new"})
    public String processNewVisitForm(@Valid Visit visit, BindingResult result){
        if (result.hasErrors()) {
            return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
        } else {
            this.visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }

}
