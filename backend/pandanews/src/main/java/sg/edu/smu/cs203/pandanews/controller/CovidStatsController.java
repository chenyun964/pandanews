package sg.edu.smu.cs203.pandanews.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.model.CovidStats.CovidCase;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.service.News.NewsServiceImpl;
import sg.edu.smu.cs203.pandanews.service.covidstats.CovidStatsServiceImpl;

@RestController
public class CovidStatsController {

    @Autowired
    private CovidStatsServiceImpl covidStatsService;

    @PostMapping(path = "covid/create")
    public ResponseEntity<?> createCase(@RequestBody CovidCase covidCase) {
        return ResponseEntity.ok(covidStatsService.createCase(covidCase));
    }

    @PostMapping(path = "covid/update/{id}")
    public ResponseEntity<?> updateCovidCase(@PathVariable int id, @RequestBody CovidCase covidCase) {
        CovidCase c = covidStatsService.updateCase(id, covidCase);
        return ResponseEntity.ok(c);
    }

    @GetMapping(path = "covid/list")
    public ResponseEntity<?> listCases() {
        return ResponseEntity.ok(covidStatsService.findAllCovidCase());
    }
}
