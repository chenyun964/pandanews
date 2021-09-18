package sg.edu.smu.cs203.pandanews.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import sg.edu.smu.cs203.pandanews.entities.*;

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
    public ResponseEntity<?> apiCall() {
        System.out.println(String.format(endpoint + "?cc=%s&q=%s", countryCode, phase));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", ApiKey);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity(headers);
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, false);
            newsListDAO deserializedObj = mapper.
                    readValue(restTemplate.exchange(String.format(endpoint + "?cc=%s&q=%s", countryCode, phase), HttpMethod.GET,
                            entity, String.class, 1).getBody(), newsListDAO.class);
            System.out.println(deserializedObj.get_type());
            return ResponseEntity.ok(deserializedObj);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
