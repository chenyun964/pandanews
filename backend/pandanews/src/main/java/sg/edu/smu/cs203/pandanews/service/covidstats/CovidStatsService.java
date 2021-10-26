package sg.edu.smu.cs203.pandanews.service.covidstats;

import sg.edu.smu.cs203.pandanews.model.covidStats.CovidCase;

import java.util.List;

public interface CovidStatsService {

    CovidCase createCase(CovidCase c);

    List<CovidCase> createCaseByAPI(); //TODO look for a API first

    CovidCase updateNews(int id, CovidCase c);

    void deleteCase(int id);

    List<CovidCase> findAllCovidCase();

    CovidCase findCaseById(int id);
}
