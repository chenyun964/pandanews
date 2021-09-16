package sg.edu.smu.cs203.pandanews.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;


@Service
public class NewsAPIService implements APICallingService {

    @Value("${azure.bing.ApiKey}")
    private String ApiKey;
    @Value("${azure.bing.endpoint}")
    private String endpoint;
    @Value("${azure.bing.countryCode}")
    private String countryCode;
    @Value("${azure.bing.phase}")
    private String phase;

    @Override
//    public ResponseEntity<?> apiCall() {
    public ResponseEntity<String> apiCall() {
        System.out.println(String.format(endpoint + "?cc=%s&q=%s", countryCode, phase));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", ApiKey);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(new Object(),
                headers);
        try {
            ResponseEntity r = restTemplate.exchange(
                    String.format(endpoint + "?cc=%s&q=%s", countryCode, phase), HttpMethod.GET, entity,
                    String.class);
            System.out.println(r.getBody());
            return r;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
