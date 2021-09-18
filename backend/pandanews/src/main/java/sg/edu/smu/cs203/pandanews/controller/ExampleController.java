package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs203.pandanews.service.News.NewsNewsAPIServiceImpl;

@RestController
public class ExampleController {

    @Autowired
    private NewsNewsAPIServiceImpl newsAPIServiceImpl;

}
