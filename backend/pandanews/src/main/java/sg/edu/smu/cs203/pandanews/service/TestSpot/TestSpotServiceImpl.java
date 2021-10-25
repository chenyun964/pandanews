package sg.edu.smu.cs203.pandanews.service.testSpot;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.TestSpot;
import sg.edu.smu.cs203.pandanews.repository.TestSpotRepository;


@Service
public class TestSpotServiceImpl implements TestSpotService {

    private TestSpotRepository testSpots;

    public TestSpotServiceImpl (TestSpotRepository testSpots) {
        this.testSpots = testSpots;
    }

    @Override
    public TestSpot getById(Long id) {
        return testSpots.findById(id).map(spot -> spot).orElse(null);
    }

    @Override
    public TestSpot getByName(String name) {
        return testSpots.findByName(name).map(spot -> spot).orElse(null);
    }

    @Override
    public List<TestSpot> listAll() {
        return testSpots.findAll();
    }

    @Override
    public List<TestSpot> listByType(String type) {
        return testSpots.findByType(type);
    }

    @Override
    public TestSpot add(TestSpot newSpot) {
        return testSpots.save(newSpot);
    }

    @Override
    public TestSpot update(Long id, TestSpot newSpot) {
        return testSpots.findById(id).map(spot -> {
            spot.setName(newSpot.getName());
            spot.setAddress(newSpot.getAddress());
            spot.setLatitude(newSpot.getLatitude());
            spot.setLongitude(newSpot.getLongitude());
            spot.setType(newSpot.getType());
            spot.setOpHours(newSpot.getOpHours());
            spot.setContact(newSpot.getContact());
            return testSpots.save(spot);
        }).orElse(null);
    }

    @Override
    public TestSpot deleteById(Long id) {
        return testSpots.findById(id).map(spot -> {
            testSpots.delete(spot);
            return spot;
        }).orElse(null);
    }
}
