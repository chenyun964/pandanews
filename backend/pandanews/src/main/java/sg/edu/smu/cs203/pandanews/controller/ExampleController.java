package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    @GetMapping(path = "demo/test1")
    public String test1() {
        return ".Hack Advisor is Yeow Leong";
    }
}
