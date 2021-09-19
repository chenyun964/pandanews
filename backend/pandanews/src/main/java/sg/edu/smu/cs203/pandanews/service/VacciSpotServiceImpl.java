package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.VacciSpot;
import sg.edu.smu.cs203.pandanews.repository.VacciSpotRepository;


@Service
public class VacciSpotServiceImpl implements VacciSpotService {

    private VacciSpotRepository vacciSpots;

    public VacciSpotServiceImpl (VacciSpotRepository vacciSpots) {
        this.vacciSpots = vacciSpots;
    }

    @Override
    public VacciSpot getById(Long id) {
        return vacciSpots.findById(id).map(spot -> spot).orElse(null);
    }

    @Override
    public VacciSpot getByName(String name) {
        return vacciSpots.findByName(name).map(spot -> spot).orElse(null);
    }

    @Override
    public List<VacciSpot> listAll() {
        return vacciSpots.findAll();
    }

    @Override
    public List<VacciSpot> listByRegion(String region) {
        return vacciSpots.findByRegion(region);
    }

    @Override
    public List<VacciSpot> listByType(String type) {
        return vacciSpots.findByType(type);
    }

    @Override
    public List<VacciSpot> listByVacciType(String vacciType) {
        return vacciSpots.findByVacciType(vacciType);
    }

    @Override
    public VacciSpot add(VacciSpot newSpot) {
        return vacciSpots.save(newSpot);
    }

    @Override
    public VacciSpot update(Long id, VacciSpot newSpot) {
        return vacciSpots.findById(id).map(spot -> {
            spot.setName(newSpot.getName());
            spot.setAddress(newSpot.getAddress());
            spot.setLatitude(newSpot.getLatitude());
            spot.setLongitude(newSpot.getLongitude());
            spot.setRegion(newSpot.getRegion());
            spot.setType(newSpot.getType());
            spot.setVacciType(newSpot.getVacciType());
            return vacciSpots.save(spot);
        }).orElse(null);
    }

    @Override
    public VacciSpot deleteById(Long id) {
        return vacciSpots.findById(id).map(spot -> {
            vacciSpots.delete(spot);
            return spot;
        }).orElse(null);
    }
}
