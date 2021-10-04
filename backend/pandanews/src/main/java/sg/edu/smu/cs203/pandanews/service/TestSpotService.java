package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.TestSpot;

@Service
public interface TestSpotService {
    TestSpot getById(Long id);
    TestSpot getByName(String name);
    List<TestSpot> listAll();
    List<TestSpot> listByType(String type);
    TestSpot add(TestSpot newSpot);
    TestSpot update(Long id, TestSpot newSpot);
    TestSpot deleteById(Long id);
}
