package jaep.springframework.petclinic.bootstrap;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Pet;
import jaep.springframework.petclinic.model.PetType;
import jaep.springframework.petclinic.model.Vet;
import jaep.springframework.petclinic.services.OwnerService;
import jaep.springframework.petclinic.services.PetService;
import jaep.springframework.petclinic.services.PetTypeService;
import jaep.springframework.petclinic.services.VetService;
import jaep.springframework.petclinic.services.map.OwnerServiceMap;
import jaep.springframework.petclinic.services.map.PetServiceMap;
import jaep.springframework.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private  final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType petType1 = new PetType();
        petType1.setName("Dog");

        PetType dog = petTypeService.save(petType1);

        PetType petType2 = new PetType();
        petType2.setName("Cat");

        PetType cat = petTypeService.save(petType2);

        //---------------------------------------------------


        //---------------------------------------------------

        Owner owner1 = new Owner();
        owner1.setFirstName("User1");
        owner1.setLastName("Doe1");
        owner1.setAddress("123 SW 25th Ct");
        owner1.setCity("Miami");
        owner1.setTelephone("3054562222");

        Pet pet1 = new Pet();
        pet1.setPetType(dog);
        pet1.setBirthDay(LocalDate.now());
        pet1.setName("Lucky");
        pet1.setOwner(owner1);
        owner1.getPets().add(pet1);

        ownerService.save(owner1);


        Owner owner2 = new Owner();
        owner2.setFirstName("User2");
        owner2.setLastName("Doe2");
        owner2.setAddress("456 NW 14t St");
        owner2.setCity("North Miami");
        owner2.setTelephone("7864457898");

        Pet pet2 = new Pet();
        pet2.setPetType(cat);
        pet2.setBirthDay(LocalDate.now().minusYears(1));
        pet2.setName("Kitty");
        pet2.setOwner(owner2);
        owner2.getPets().add(pet2);

        ownerService.save(owner2);

        Owner owner3 = new Owner();
        owner3.setFirstName("User3");
        owner3.setLastName("Doe3");
        owner3.setAddress("789 Pines Blvd");
        owner3.setCity("Pembroke Pines");
        owner3.setTelephone("9544568787");

        Pet pet3 = new Pet();
        pet3.setPetType(dog);
        pet3.setBirthDay(LocalDate.now().minusMonths(3));
        pet3.setName("Boby");
        pet3.setOwner(owner3);
        owner3.getPets().add(pet3);

        ownerService.save(owner3);

        //---------------------------------------------------

        Vet vet1 = new Vet();
        vet1.setFirstName("Juan");
        vet1.setLastName("Perez");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("John");
        vet2.setLastName("Doe");

        vetService.save(vet2);

        Vet vet3 = new Vet();
        vet3.setFirstName("Jim");
        vet3.setLastName("Cox");

        vetService.save(vet3);

        //---------------------------------------------------

    }
}
