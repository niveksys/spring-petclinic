package com.niveksys.petclinic.controller;

import java.util.Set;

import com.niveksys.petclinic.model.Vet;
import com.niveksys.petclinic.service.VetService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({ "/vets", "/vets/", "/vets/index", "/vets/index.html" })
    public String list(Model model) {
        log.debug("LIST all Vets.");
        model.addAttribute("vets", this.vetService.findAll());
        return "vets/list";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> listJson() {
        log.debug("LIST all Vets (JSON).");
        return vetService.findAll();
    }
}
