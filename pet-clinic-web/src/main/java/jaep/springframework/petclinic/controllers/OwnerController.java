package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping({"/owners"})
public class OwnerController {
    private static final String VIEW_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedField(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"/find"})
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){
        if (owner.getLastName() == null){
            owner.setLastName("");
        }

        Set<Owner> results = this.ownerService.findAllByLastNameLike(owner.getLastName());

        if (results.isEmpty()){
            result.rejectValue("lastName","notFound","not found");
            return "owners/findOwners";
        } else if(results.size() == 1){
            owner = results.iterator().next();
            return "redirect:/owners/"+owner.getId();
        } else {
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping({"/{ownerId}"})
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownersDetails");
        mav.addObject(ownerService.findByID(ownerId));

        return mav;
    }

    @GetMapping({"/new"})
    public String initCreationForm(Model model){
        model.addAttribute("owner", Owner.builder().build());

        return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping({"/new"})
    public String processCreationForm(@Valid Owner owner, BindingResult result){
        if (result.hasErrors()){
            return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner saved = this.ownerService.save(owner);
            return "redirect:/owners/" + saved.getId();
        }
    }

    @GetMapping({"/{ownerId}/edit"})
    public String initUpdateForm(@PathVariable("ownerId") Long ownerId, Model model){
        Owner owner = ownerService.findByID(ownerId);
        model.addAttribute("owner", owner);
        return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping({"/{ownerId}/edit"})
    public String processUpdateForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") Long ownerId){
        if (result.hasErrors()){
            return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            Owner saved = this.ownerService.save(owner);
            return "redirect:/owners/" + saved.getId();
        }
    }
}
