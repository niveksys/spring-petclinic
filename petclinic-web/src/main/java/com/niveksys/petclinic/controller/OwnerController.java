package com.niveksys.petclinic.controller;

import com.niveksys.petclinic.service.OwnerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({ "", "/", "/index", "index.html" })
    public String list(Model model) {
        log.debug("LIST all Owners.");
        model.addAttribute("owners", this.ownerService.findAll());
        return "owners/list";
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable("id") Long id) {
        log.debug("SHOW information about an Owner.");
        ModelAndView mav = new ModelAndView("owners/show");
        mav.addObject(this.ownerService.findById(id));
        return mav;
    }

    @RequestMapping("/search")
    public String search() {
        return "notimplemented";
    }
}
