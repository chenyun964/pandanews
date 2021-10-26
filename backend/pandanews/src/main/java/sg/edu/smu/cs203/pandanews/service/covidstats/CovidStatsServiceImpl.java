package sg.edu.smu.cs203.pandanews.service.covidstats;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.covidStats.CovidCase;
import sg.edu.smu.cs203.pandanews.repository.CovidCaseRepository;

import java.util.List;

@Service
public class CovidStatsServiceImpl implements CovidStatsService {
    @Autowired
    private CovidCaseRepository covidCaseRepository;

    @Override
    public CovidCase createCase(CovidCase c) {
        return covidCaseRepository.save(c);
    }

    @Override
    public List<CovidCase> createCaseByAPI() {
        return null;
    }

    @Override
    public CovidCase updateNews(int id, CovidCase c) {
        return covidCaseRepository.findById(id).map(newCase -> {
            newCase.setLatitude(c.getLatitude());
            newCase.setLongitude(c.getLongitude());
            return covidCaseRepository.save(newCase);
        }).orElse(null);
    }

    @Override
    public void deleteCase(int id) {
        covidCaseRepository.deleteById(id);
    }

    @Override
    public List<CovidCase> findAllCovidCase() {
       return covidCaseRepository.findAll();
    }

    @Override
    public CovidCase findCaseById(int id) {
        return covidCaseRepository.findById(id).orElse(null);
    }
}
