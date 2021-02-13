package com.niveksys.petclinic.controller;

import java.util.List;

import javax.validation.Valid;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.service.OwnerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String OWNER_CREATE_OR_UPDATE_VIEW = "owners/edit";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({ "", "/", "/index", "index.html" })
    public String list(Model model) {
        log.debug("LIST all Owners.");
        model.addAttribute("owners", this.ownerService.findAll());
        return "owners/list";
    }

    @GetMapping("/new")
    public String newOwner(Model model) {
        log.debug("NEW form for an Owner.");
        model.addAttribute("owner", Owner.builder().build());
        return OWNER_CREATE_OR_UPDATE_VIEW;
    }

    @PostMapping({ "", "/" })
    public String create(@Valid Owner owner, BindingResult result) {
        log.debug("CREATE a new Owner, then redirect to SHOW.");
        if (result.hasErrors()) {
            return OWNER_CREATE_OR_UPDATE_VIEW;
        } else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable("id") Long id) {
        log.debug("SHOW information about an Owner.");
        ModelAndView mav = new ModelAndView("owners/show");
        mav.addObject(this.ownerService.findById(id));
        return mav;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        log.debug("EDIT form for an Owner.");
        model.addAttribute("owner", this.ownerService.findById(id));
        return OWNER_CREATE_OR_UPDATE_VIEW;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid Owner owner, BindingResult result) {
        log.debug("UPDATE an Owner, then redirect to SHOW.");
        if (result.hasErrors()) {
            return OWNER_CREATE_OR_UPDATE_VIEW;
        } else {
            owner.setId(id);
            Owner savedOwner = this.ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/search")
    public String search(Model model) {
        log.debug("SEARCH form for Owner.");
        model.addAttribute("owner", Owner.builder().build());
        return "owners/search";
    }

    @GetMapping("/find")
    public String find(Owner owner, BindingResult result, Model model) {
        log.debug("FIND an Owner(s), then redirect to somewhere.");
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> results = ownerService.findByLastNameContaining(owner.getLastName());

        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/search";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("owners", results);
            return "owners/list";
        }
    }
}
