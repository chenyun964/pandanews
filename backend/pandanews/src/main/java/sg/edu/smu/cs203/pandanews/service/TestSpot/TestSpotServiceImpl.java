package sg.edu.smu.cs203.pandanews.service.testspot;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.TestSpot;
import sg.edu.smu.cs203.pandanews.repository.TestSpotRepository;


@Service
public class TestSpotServiceImpl implements TestSpotService {

    private TestSpotRepository testSpotRepo;

    public TestSpotServiceImpl (TestSpotRepository testSpotRepo) {
        this.testSpotRepo = testSpotRepo;
    }

    @Override
    public TestSpot getById(Long id) {
        return testSpotRepo.findById(id).map(spot -> spot).orElse(null);
    }

    @Override
    public TestSpot getByName(String name) {
        return testSpotRepo.findByName(name).map(spot -> spot).orElse(null);
    }

    @Override
    public List<TestSpot> listAll() {
        return testSpotRepo.findAll();
    }

    @Override
    public List<TestSpot> listByType(String type) {
        return testSpotRepo.findByType(type);
    }

    @Override
    public TestSpot add(TestSpot newSpot) {
        return testSpotRepo.save(newSpot);
    }

    @Override
    public TestSpot update(Long id, TestSpot newSpot) {
        return testSpotRepo.findById(id).map(spot -> {
            spot.setAdmin(newSpot.getAdmin());
            spot.setName(newSpot.getName());
            spot.setAddress(newSpot.getAddress());
            spot.setLatitude(newSpot.getLatitude());
            spot.setLongitude(newSpot.getLongitude());
            spot.setType(newSpot.getType());
            spot.setOpHours(newSpot.getOpHours());
            spot.setContact(newSpot.getContact());
            return testSpotRepo.save(spot);
        }).orElse(null);
    }

    @Override
    public TestSpot deleteById(Long id) {
        return testSpotRepo.findById(id).map(spot -> {
            testSpotRepo.delete(spot);
            return spot;
        }).orElse(null);
    }
}
