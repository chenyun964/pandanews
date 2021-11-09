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

import sg.edu.smu.cs203.pandanews.model.StatSummary;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.service.statistic.StatisticService;


@RestController
public class StatisticController {

    private StatisticService statsService;

    public StatisticController(StatisticService ss) {
        this.statsService = ss;
    }

    // create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/statistic")
    public Statistic addStatistic(@Valid @RequestBody Statistic stats) {
        Statistic savedStatistic = statsService.addStatistic(stats);

        return savedStatistic;
    }

    // read all
    @GetMapping("/statistic")
    public List<Statistic> getStatistics() {
        return statsService.displayStatistics();
    }

    // get summary
    @GetMapping("/statistic/summary")
    public StatSummary getStatisticSummary() {
        return statsService.getSummary();
    }

    // read one
    @GetMapping("/statistic/{id}")
    public Statistic getStatisticById(@PathVariable Long id) {
        Statistic stats = statsService.getStatistic(id);
        if (stats == null)
            return null;
        return statsService.getStatistic(id);
    }

    // update
    @PutMapping("/statistic/{id}")
    public Statistic updateStatistic(@PathVariable Long id, @RequestBody Statistic newStatistic) {
        Statistic statistic = statsService.updateStatistic(id, newStatistic);
        if (statistic == null)
            return null;
        return statistic;
    }

    // delete
    @DeleteMapping("/statistic/{id}")
    public void deleteStatistic(@PathVariable Long id) {
        statsService.deleteStatistic(id);
    }
}
