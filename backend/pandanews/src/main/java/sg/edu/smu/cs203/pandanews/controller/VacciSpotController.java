package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import sg.edu.smu.cs203.pandanews.dto.VacciSpotDTO;
import sg.edu.smu.cs203.pandanews.exception.SpotNotFoundException;
import sg.edu.smu.cs203.pandanews.model.VacciSpot;
import sg.edu.smu.cs203.pandanews.service.VacciSpot.VacciSpotService;
import sg.edu.smu.cs203.pandanews.util.GeoCodeUtil;

@RestController
@RequestMapping(path = "/vaccispots")
public class VacciSpotController {
    private VacciSpotService vacciSpotService;

    private GeoCodeUtil geoCodeUtil;

    @Autowired
    public VacciSpotController(VacciSpotService vss, GeoCodeUtil gcu) {
        this.vacciSpotService = vss;
        this.geoCodeUtil = gcu;
    }

    @GetMapping
    @ResponseBody
    public Iterable<VacciSpot> getVacciSpots() {
        return vacciSpotService.listAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public VacciSpot getById(@PathVariable Long id) {
        VacciSpot spot = vacciSpotService.getById(id);
        if (spot == null) {
            throw new SpotNotFoundException();
        }
        return spot;
    }

    @GetMapping(path = "/name/{name}")
    @ResponseBody
    public VacciSpot getByName(@PathVariable String name) {
        VacciSpot spot = vacciSpotService.getByName(name);
        if (spot == null) {
            throw new SpotNotFoundException();
        }
        return spot;
    }

    @GetMapping(path = "/region/{region}")
    @ResponseBody
    public Iterable<VacciSpot> getAllByRegion(@PathVariable String region) {
        return vacciSpotService.listByRegion(region);
    }

    @GetMapping(path = "/type/{type}")
    @ResponseBody
    public Iterable<VacciSpot> getAllByType(@PathVariable String type) {
        return vacciSpotService.listByType(type);
    }

    @GetMapping(path = "vaccitype/{vacciType}")
    @ResponseBody
    public Iterable<VacciSpot> getAllByVacciType(@PathVariable String vacciType) {
        return vacciSpotService.listByVacciType(vacciType);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public VacciSpot postMethodName(@RequestBody VacciSpotDTO newSpotDTO) {
        VacciSpot newSpot = new VacciSpot();
        newSpot.setName(newSpotDTO.getName());
        newSpot.setType(newSpotDTO.getType());
        newSpot.setAddress(newSpotDTO.getAddress());
        newSpot.setRegion(newSpotDTO.getRegion());
        newSpot.setVacciType(newSpotDTO.getVacciType());
        Double[] latLng = geoCodeUtil.getLatLng(newSpotDTO.getAddress());
        newSpot.setLatitude(latLng[0]);
        newSpot.setLongitude(latLng[1]);
        return vacciSpotService.add(newSpot);
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public VacciSpot updateVacciSpot(@PathVariable Long id, @RequestBody VacciSpotDTO newSpotDTO) {
        VacciSpot newSpot = new VacciSpot();
        newSpot.setName(newSpotDTO.getName());
        newSpot.setType(newSpotDTO.getType());
        newSpot.setAddress(newSpotDTO.getAddress());
        newSpot.setRegion(newSpotDTO.getRegion());
        newSpot.setVacciType(newSpotDTO.getVacciType());
        newSpot.setLatitude(newSpotDTO.getLatitude());
        newSpot.setLongitude(newSpotDTO.getLongitude());
        newSpot = vacciSpotService.update(id, newSpot);
        if (newSpot == null) {
            throw new SpotNotFoundException();
        }
        return newSpot;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public VacciSpot deleteById(@PathVariable Long id) {
        VacciSpot spot = vacciSpotService.deleteById(id);
        if (spot == null) {
            throw new SpotNotFoundException();
        }
        return spot;
    }
}
