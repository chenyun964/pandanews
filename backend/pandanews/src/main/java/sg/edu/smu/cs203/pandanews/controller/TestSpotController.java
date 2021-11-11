package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import sg.edu.smu.cs203.pandanews.dto.TestSpotDTO;
import sg.edu.smu.cs203.pandanews.exception.SpotNotFoundException;
import sg.edu.smu.cs203.pandanews.model.TestSpot;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.service.testspot.TestSpotService;
import sg.edu.smu.cs203.pandanews.service.user.UserService;
import sg.edu.smu.cs203.pandanews.util.GeoCodeUtil;

@RestController
@CrossOrigin
@RequestMapping(path = "/testspots")
public class TestSpotController {
    private TestSpotService testSpotService;

    private GeoCodeUtil geoCodeUtil;

    private UserService userService;

    @Autowired
    public TestSpotController(TestSpotService tss, GeoCodeUtil gcu, UserService us) {
        this.testSpotService = tss;
        this.geoCodeUtil = gcu;
        this.userService = us;
    }

    /**
     * List all swab test spots.
     * 
     * @return list of all swab test spots
     */
    @GetMapping
    @ResponseBody
    public Iterable<TestSpot> getTestSpots() {
        return testSpotService.listAll();
    }

    /**
     * Search for swab test spot with the given id. If there is no swab test spot
     * with the given id, throw a SpotNotFoundException.
     * 
     * @param id
     * @return swab test spot with the given id
     */
    @GetMapping(path = "/{id}")
    @ResponseBody
    public TestSpot getById(@PathVariable Long id) {
        TestSpot spot = testSpotService.getById(id);
        if (spot == null) {
            throw new SpotNotFoundException();
        }
        return spot;
    }

    /**
     * Search for swab test spot with the given name. If there is no swab test spot
     * with the given name, throw a SpotNotFoundException.
     * 
     * @param name
     * @return swab test spot with the given name
     */
    @GetMapping(path = "/name/{name}")
    @ResponseBody
    public TestSpot getByName(@PathVariable String name) {
        TestSpot spot = testSpotService.getByName(name);
        if (spot == null) {
            throw new SpotNotFoundException();
        }
        return spot;
    }

    /**
     * List all swab test spots with the given type of test.
     * 
     * @param type
     * @return list of swab test spots with the given type of test
     */
    @GetMapping(path = "/type/{type}")
    @ResponseBody
    public Iterable<TestSpot> getAllByType(@PathVariable String type) {
        return testSpotService.listByType(type);
    }

    /**
     * Add a new swab test spot with POST request to "/testspots".
     * 
     * @param newSpotDTO
     * @return saved swab test spot
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TestSpot postMethodName(@RequestBody @Valid TestSpotDTO newSpotDTO) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        TestSpot newSpot = new TestSpot();
        newSpot.setAdmin(user);
        newSpot.setName(newSpotDTO.getName());
        newSpot.setType(newSpotDTO.getType());
        newSpot.setAddress(newSpotDTO.getAddress());
        newSpot.setOpHours(newSpotDTO.getOpHours());
        newSpot.setContact(newSpotDTO.getContact());
        Double[] latLng = geoCodeUtil.getLatLng(newSpotDTO.getAddress());
        newSpot.setLatitude(latLng[0]);
        newSpot.setLongitude(latLng[1]);
        return testSpotService.add(newSpot);
    }

    /**
     * Update a swab test spot with the given id with PUT request to "/testspots".
     * If there is no swab test spot with the given id, throw a SpotNotFoundException.
     * 
     * @param id
     * @param newSpotDTO
     * @return updated swab test spot
     */
    @PutMapping(path = "/{id}")
    @ResponseBody
    public TestSpot updateTestSpot(@PathVariable Long id, @RequestBody @Valid TestSpotDTO newSpotDTO) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        TestSpot newSpot = new TestSpot();
        newSpot.setAdmin(user);
        newSpot.setName(newSpotDTO.getName());
        newSpot.setType(newSpotDTO.getType());
        newSpot.setAddress(newSpotDTO.getAddress());
        newSpot.setOpHours(newSpotDTO.getOpHours());
        newSpot.setContact(newSpotDTO.getContact());
        Double[] latLng = geoCodeUtil.getLatLng(newSpotDTO.getAddress());
        newSpot.setLatitude(latLng[0]);
        newSpot.setLongitude(latLng[1]);
        newSpot = testSpotService.update(id, newSpot);
        if (newSpot == null) {
            throw new SpotNotFoundException();
        }
        return newSpot;
    }

    /**
     * Delete the swab test spot with the given id with DELETE request to "/testspots".
     * If there is no swab test spot with the given id, throw a SpotNotFoundException.
     * 
     * @param id
     * @return deleted swab test spot
     */
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public TestSpot deleteById(@PathVariable Long id) {
        TestSpot spot = testSpotService.deleteById(id);
        if (spot == null) {
            throw new SpotNotFoundException();
        }
        return spot;
    }
}
