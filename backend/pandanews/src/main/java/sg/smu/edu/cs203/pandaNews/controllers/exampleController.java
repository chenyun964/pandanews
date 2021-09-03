package sg.smu.edu.cs203.pandaNews.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class exampleController {
    @GetMapping(path = "demo/test1")
    public String test1() {
        return ".Hack Advisor is Yeow Leong";
    }
}
