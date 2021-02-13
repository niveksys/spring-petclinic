package com.niveksys.petclinic.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.model.Visit;
import com.niveksys.petclinic.service.PetService;
import com.niveksys.petclinic.service.VisitService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = VisitController.class)
public class VisitControllerTests {

    @MockBean
    VisitService visitService;

    @MockBean
    PetService petService;

    @Autowired
    MockMvc mockMvc;

    Pet pet;

    @BeforeEach
    public void setUp() {
        this.pet = Pet.builder().id(2L).owner(Owner.builder().id(1L).build()).build();
    }

    @Test
    public void newVisit() throws Exception {
        // given
        when(this.petService.findById(anyLong())).thenReturn(this.pet);

        // when
        mockMvc.perform(get("/owners/1/pets/2/visits/new")).andExpect(status().isOk())
                .andExpect(view().name("pets/visit"));
    }

    @Test
    public void create() throws Exception {
        // given
        when(this.petService.findById(anyLong())).thenReturn(this.pet);
        when(this.visitService.save(any())).thenReturn(Visit.builder().pet(this.pet).build());

        // when
        mockMvc.perform(post("/owners/1/pets/2/visits").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2018-11-11").param("description", "yet another visit"))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/1"));
    }
}
