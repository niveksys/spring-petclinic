package com.niveksys.petclinic.bootstrap;

import java.time.LocalDate;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.model.PetType;
import com.niveksys.petclinic.model.Speciality;
import com.niveksys.petclinic.model.Vet;
import com.niveksys.petclinic.model.Visit;
import com.niveksys.petclinic.service.OwnerService;
import com.niveksys.petclinic.service.PetTypeService;
import com.niveksys.petclinic.service.SpecialityService;
import com.niveksys.petclinic.service.VetService;
import com.niveksys.petclinic.service.VisitService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
            SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = this.petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = this.petTypeService.save(cat);

        PetType bear = new PetType();
        bear.setName("Bear");
        PetType savedBearPetType = this.petTypeService.save(bear);

        PetType rabbit = new PetType();
        rabbit.setName("Rabbit");
        PetType savedRabbitPetType = this.petTypeService.save(rabbit);

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = this.specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        Speciality savedSurgery = this.specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        Speciality savedDentistry = this.specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Kevin");
        owner1.setLastName("Tsang");
        owner1.setAddress("123 Meadowland");
        owner1.setCity("London");
        owner1.setTelephone("1234567890");

        Pet kevinPet = new Pet();
        kevinPet.setName("Teddy");
        kevinPet.setType(savedDogPetType);
        kevinPet.setOwner(owner1);
        kevinPet.setBirthDate(LocalDate.now());
        owner1.getPets().add(kevinPet);

        this.ownerService.save(owner1);

        Owner owner2 = new Owner();

        owner2.setFirstName("Joyce");
        owner2.setLastName("Lau");
        owner2.setAddress("123 Meadowland");
        owner2.setCity("London");
        owner2.setTelephone("1234567890");

        Pet joycePet = new Pet();
        joycePet.setName("Cony");
        joycePet.setType(savedRabbitPetType);
        joycePet.setOwner(owner2);
        joycePet.setBirthDate(LocalDate.now());
        owner2.getPets().add(joycePet);

        this.ownerService.save(owner2);

        log.debug("Loaded Owners....");

        Visit rabbitVisit = new Visit();
        rabbitVisit.setPet(joycePet);
        rabbitVisit.setDate(LocalDate.now());
        rabbitVisit.setDescription("Sneezy Rabbit");

        this.visitService.save(rabbitVisit);

        log.debug("Loaded Visits....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        this.vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);

        this.vetService.save(vet2);

        log.debug("Loaded Vets....");
    }

}
