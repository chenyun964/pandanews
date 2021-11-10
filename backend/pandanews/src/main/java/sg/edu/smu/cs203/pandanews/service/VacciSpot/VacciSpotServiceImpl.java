package sg.edu.smu.cs203.pandanews.service.vaccispot;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.VacciSpot;
import sg.edu.smu.cs203.pandanews.repository.VacciSpotRepository;


@Service
public class VacciSpotServiceImpl implements VacciSpotService {

    private VacciSpotRepository vacciSpotRepo;

    public VacciSpotServiceImpl (VacciSpotRepository vacciSpotRepo) {
        this.vacciSpotRepo = vacciSpotRepo;
    }

    @Override
    public VacciSpot getById(Long id) {
        return vacciSpotRepo.findById(id).map(spot -> spot).orElse(null);
    }

    @Override
    public VacciSpot getByName(String name) {
        return vacciSpotRepo.findByName(name).map(spot -> spot).orElse(null);
    }

    @Override
    public List<VacciSpot> listAll() {
        return vacciSpotRepo.findAll();
    }

    @Override
    public List<VacciSpot> listByRegion(String region) {
        return vacciSpotRepo.findByRegion(region);
    }

    @Override
    public List<VacciSpot> listByType(String type) {
        return vacciSpotRepo.findByType(type);
    }

    @Override
    public List<VacciSpot> listByVacciType(String vacciType) {
        return vacciSpotRepo.findByVacciType(vacciType);
    }

    @Override
    public VacciSpot add(VacciSpot newSpot) {
        return vacciSpotRepo.save(newSpot);
    }

    @Override
    public VacciSpot update(Long id, VacciSpot newSpot) {
        return vacciSpotRepo.findById(id).map(spot -> {
            spot.setName(newSpot.getName());
            spot.setAddress(newSpot.getAddress());
            spot.setLatitude(newSpot.getLatitude());
            spot.setLongitude(newSpot.getLongitude());
            spot.setRegion(newSpot.getRegion());
            spot.setType(newSpot.getType());
            spot.setVacciType(newSpot.getVacciType());
            spot.setAdmin(newSpot.getAdmin());
            return vacciSpotRepo.save(spot);
        }).orElse(null);
    }

    @Override
    public VacciSpot deleteById(Long id) {
        return vacciSpotRepo.findById(id).map(spot -> {
            vacciSpotRepo.delete(spot);
            return spot;
        }).orElse(null);
    }
}
