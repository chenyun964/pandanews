package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.model.StatSummary;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.service.statistic.StatisticService;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin
public class StatisticController {

    private StatisticService statsService;

    public StatisticController(StatisticService ss) {
        this.statsService = ss;
    }

    /**
     * create new statistic of today
     * @param stats stats
     * @return saved statistics
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/statistic")
    public Statistic addStatistic(@Valid @RequestBody Statistic stats) {
        Statistic savedStatistic = statsService.addStatistic(stats);

        return savedStatistic;
    }


    /**
     * select all statistics
     * @return all statistics
     */
    @GetMapping("/statistic")
    public List<Statistic> getStatistics() {
        return statsService.displayStatistics();
    }

    /**
     * Get the summary of all stats
     * @return summarised stats
     */
    @GetMapping("/statistic/summary")
    public StatSummary getStatisticSummary() {
        return statsService.getSummary();
    }

    /**
     * select one particular stats
     * @param id
     * @return the stats with id
     */
    @GetMapping("/statistic/{id}")
    public Statistic getStatisticById(@PathVariable Long id) {
        Statistic stats = statsService.getStatistic(id);
        if (stats == null)
            return null;
        return statsService.getStatistic(id);
    }

    /**
     * update stats
     * @param id
     * @param newStatistic stats contains updated value
     * @return updated stats
     */
    @PutMapping("/statistic/{id}")
    public Statistic updateStatistic(@PathVariable Long id, @RequestBody Statistic newStatistic) {
        Statistic statistic = statsService.updateStatistic(id, newStatistic);
        if (statistic == null)
            return null;
        return statistic;
    }

    /**
     * delete stats
     * @param id
     */
    @DeleteMapping("/statistic/{id}")
    public void deleteStatistic(@PathVariable Long id) {
        statsService.deleteStatistic(id);
    }
}
