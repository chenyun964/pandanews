package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.VacciSpot;

@Service
public interface VacciSpotService {
    VacciSpot getById(Long id);
    VacciSpot getByName(String name);
    List<VacciSpot> listAll();
    List<VacciSpot> listByRegion(String region);
    List<VacciSpot> listByType(String type);
    List<VacciSpot> listByVacciType(String vacciType);
    VacciSpot add(VacciSpot newSpot);
    VacciSpot update(Long id, VacciSpot newSpot);
    VacciSpot deleteById(Long id);
}
