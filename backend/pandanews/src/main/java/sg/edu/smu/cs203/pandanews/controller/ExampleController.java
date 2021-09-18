package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs203.pandanews.service.NewsNewsAPIServiceImpl;

@RestController
public class ExampleController {

    @Autowired
    private NewsNewsAPIServiceImpl newsAPIServiceImpl;

}
