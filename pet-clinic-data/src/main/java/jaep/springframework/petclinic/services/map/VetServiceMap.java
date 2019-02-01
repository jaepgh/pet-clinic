package jaep.springframework.petclinic.services.map;

import jaep.springframework.petclinic.model.Specialty;
import jaep.springframework.petclinic.model.Vet;
import jaep.springframework.petclinic.services.SpecialtyService;
import jaep.springframework.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    public VetServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public Vet findByID(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet object) {
        if(object.getSpecialties().size() > 0){
            object.getSpecialties().forEach(especialty->{
                if (especialty.getId() == null){
                    Specialty saved = specialtyService.save(especialty);
                    especialty.setId(saved.getId());
                }
            });
        }
        return super.save(object);
    }

}
