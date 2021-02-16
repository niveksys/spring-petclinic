package com.niveksys.petclinic.controller;

import com.niveksys.petclinic.service.VetService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({ "", "/", "/index", "index.html" })
    public String list(Model model) {
        log.debug("LIST all Vets.");
        model.addAttribute("vets", this.vetService.findAll());
        return "vets/list";
    }

}
