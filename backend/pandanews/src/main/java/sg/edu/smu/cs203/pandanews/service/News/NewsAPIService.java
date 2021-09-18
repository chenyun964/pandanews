package sg.edu.smu.cs203.pandanews.service.News;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.news.News;

import java.util.List;

@Service
//This method is for calling of external API
public interface NewsAPIService {
    public List<News> apiCall();
}
