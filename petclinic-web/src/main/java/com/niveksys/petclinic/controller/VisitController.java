package com.niveksys.petclinic.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

import javax.validation.Valid;

import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.model.Visit;
import com.niveksys.petclinic.service.PetService;
import com.niveksys.petclinic.service.VisitService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method. 2 goals: -
     * Make sure we always have fresh data - Since we do not use the session scope,
     * make sure that Pet object always has an id (Even though id is not part of the
     * form fields)
     *
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
        Pet pet = this.petService.findById(petId);
        model.addAttribute("pet", pet);
        Visit visit = Visit.builder().build();
        pet.addVisit(visit);
        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
    // called
    @GetMapping("/visits/new")
    public String newVisit(@PathVariable("petId") Long petId, Model model) {
        log.debug("NEW form for a Visit.");
        return "pets/visit";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
    // called
    @PostMapping("/visits")
    public String create(@Valid Visit visit, BindingResult result) {
        log.debug("CREATE a new Visit, then redirect to SHOW.");
        if (result.hasErrors()) {
            return "pets/visit";
        } else {
            Visit savedVisit = visitService.save(visit);
            return "redirect:/owners/" + savedVisit.getPet().getOwner().getId();
        }
    }
}
