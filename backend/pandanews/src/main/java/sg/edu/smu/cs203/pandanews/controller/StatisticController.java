package sg.edu.smu.cs203.pandanews.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.service.Statistic.StatisticService;

@RestController
public class StatisticController {

    private StatisticService statsService;

    public StatisticController(StatisticService ss){
        this.statsService = ss;
    }

    //create 
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/covidStatistic")
    public Statistic addStatistic(@Valid @RequestBody Statistic stats){
        Statistic savedStatistic = statsService.addStatistic(stats);

        return savedStatistic;
    }

    // read all
    @GetMapping("/covidStatistic")
    public List<Statistic> getStatistics(){
        return statsService.displayStatistics();
    }

    // read one 
    @GetMapping("/covidStatistic/{id}")
    public Statistic getStatistic(@PathVariable Long id){
        Statistic stats = statsService.getStatistic(id);

        if(stats == null) 
            return null;
        return statsService.getStatistic(id);
    }

    //update 
    @PutMapping("/covidStatistic/{id}")
    public Statistic updateStatistic(@PathVariable Long id, @RequestBody Statistic newStatistic){
        Statistic statistic = statsService.updateStatistic(id, newStatistic);
        if(statistic == null) return null;
        return statistic;
    }

    // delete
    @DeleteMapping("/covidStatistic/{id}")
    public void deleteStatistic(@PathVariable Long id){
        statsService.deleteStatistic(id);
    }

    // @PostMapping(path = "covid/create")
    // public ResponseEntity<?> createCase(@RequestBody CovidCase covidCase) {
    //     return ResponseEntity.ok(statsService.createCase(covidCase));
    // }

    // @PostMapping(path = "covid/update/{id}")
    // public ResponseEntity<?> updateCovidCase(@PathVariable int id, @RequestBody CovidCase covidCase) {
    //     CovidCase c = statsService.updateCase(id, covidCase);
    //     return ResponseEntity.ok(c);
    // }

    // @GetMapping(path = "covid/list")
    // public ResponseEntity<?> listCases() {
    //     return ResponseEntity.ok(statsService.findAllCovidCase());
    // }
}
