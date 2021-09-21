package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs203.pandanews.model.CovidStats.CovidCase;
import sg.edu.smu.cs203.pandanews.service.News.NewsNewsAPIServiceImpl;
import sg.edu.smu.cs203.pandanews.service.TestService;

@RestController
public class ExampleController {

    @Autowired
    private TestService testService;
    @PostMapping(path = "test/test")
    public ResponseEntity<?> createCase() {
        return ResponseEntity.ok(testService.testMethod());
    }
}
