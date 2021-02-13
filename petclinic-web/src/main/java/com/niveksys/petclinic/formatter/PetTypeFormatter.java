package com.niveksys.petclinic.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import com.niveksys.petclinic.model.PetType;
import com.niveksys.petclinic.service.PetTypeService;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> findPetTypes = this.petTypeService.findAll();

        for (PetType type : findPetTypes) {
            if (type.getName().equals(text)) {
                return type;
            }
        }

        throw new ParseException("Type not found with text: " + text, 0);
    }
}
