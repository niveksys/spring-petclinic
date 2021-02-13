package com.niveksys.petclinic.controller;

import java.util.Collection;

import javax.validation.Valid;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.model.PetType;
import com.niveksys.petclinic.service.OwnerService;
import com.niveksys.petclinic.service.PetService;
import com.niveksys.petclinic.service.PetTypeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String PET_CREATE_OR_UPDATE_VIEW = "pets/edit";

    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @GetMapping("/pets/new")
    public String newPet(Owner owner, Model model) {
        log.debug("NEW form for a Pet.");
        Pet pet = Pet.builder().build();
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return PET_CREATE_OR_UPDATE_VIEW;
    }

    @PostMapping("/pets")
    public String create(Owner owner, @Valid Pet pet, BindingResult result, Model model) {
        log.debug("CREATE a new Pet, then redirect to SHOW.");
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.addPet(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return PET_CREATE_OR_UPDATE_VIEW;
        } else {
            Owner savedOwner = this.ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/pets/{id}/edit")
    public String edit(Owner owner, @PathVariable Long id, Model model) {
        log.debug("EDIT form for a Pet.");
        Pet pet = this.petService.findById(id);
        if (pet == null) {
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("pet", pet);
            return PET_CREATE_OR_UPDATE_VIEW;
        }
    }

    @PostMapping("/pets/{id}")
    public String update(@Valid Pet pet, BindingResult result, Owner owner, Model model) {
        log.debug("UPDATE a Pet, then redirect to SHOW.");
        pet.setOwner(owner);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return PET_CREATE_OR_UPDATE_VIEW;
        } else {
            Pet savedPet = this.petService.save(pet);
            return "redirect:/owners/" + savedPet.getOwner().getId();
        }
    }
}
