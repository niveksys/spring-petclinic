package com.niveksys.petclinic.controller;

import com.niveksys.petclinic.service.VetService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vets")
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({ "", "/", "/index", "/index.html" })
    public String listVats(Model model) {
        model.addAttribute("vets", this.vetService.findAll());
        return "vets/index";
    }

}
