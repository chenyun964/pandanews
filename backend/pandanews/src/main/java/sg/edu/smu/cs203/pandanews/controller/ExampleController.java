package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sg.edu.smu.cs203.pandanews.service.NewsAPIService;

@RestController
public class ExampleController {

    @Autowired
    private NewsAPIService newsAPIService;

    @GetMapping(path = "demo/test1")
    public ResponseEntity<?> test1() {
        try {
            return newsAPIService.apiCall();
        }catch (ResponseStatusException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "demo/test2")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok("Fuck u");
    }
}
