package jaep.springframework.petclinic.controllers;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping({"/owners"})
public class OwnerController {
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
}
