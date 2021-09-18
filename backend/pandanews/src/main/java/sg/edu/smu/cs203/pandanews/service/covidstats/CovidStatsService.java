package sg.edu.smu.cs203.pandanews.service.covidstats;

import org.springframework.beans.factory.annotation.Autowired;
import sg.edu.smu.cs203.pandanews.model.CovidStats.CovidCase;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CovidCaseRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.service.News.NewsNewsAPIServiceImpl;

import java.util.List;

public interface CovidStatsService {



    CovidCase createCase(CovidCase c);

    List<CovidCase> createCaseByAPI(); //TODO look for a API first

    CovidCase updateNews(int id, CovidCase c);

    void deleteCase(int id);

    List<CovidCase> findAllCovidCase();

    CovidCase findCaseById(int id);
}
