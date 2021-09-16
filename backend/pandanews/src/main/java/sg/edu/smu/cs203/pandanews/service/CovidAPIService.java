package sg.edu.smu.cs203.pandanews.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

public class CovidAPIService implements APICallingService{

//    @Value("${azure.bing.ApiKey}")
//    public String ApiKey;
//    @Value("${azure.bing.endpoint}")
//    public  String endpoint;


    @Override
    public ResponseEntity<?> apiCall() {
        return null;
    }

}
