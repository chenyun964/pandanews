package sg.edu.smu.cs203.pandanews.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import sg.edu.smu.cs203.pandanews.model.geoCode.GeoCodeListDAO;

@Component
public class GeoCodeUtil {
    @Value("${positionstack.apikey}")
    private String apikey;
    @Value("${positionstack.endpoint}")
    private String endpoint;

    public Double[] getLatLng(String address) {
        RestTemplate restTemplate = new RestTemplate();
        GeoCodeListDAO geoCodeListDAO = new GeoCodeListDAO();
        String url = String.format(
                "%s?access_key=%s&query={address}", endpoint, apikey);
        ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, false);

        try {
            geoCodeListDAO = mapper.readValue(
                restTemplate.exchange(url, HttpMethod.GET, null, String.class, address).getBody(), 
                GeoCodeListDAO.class
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return new Double[]{
            geoCodeListDAO.getData().get(0).getLatitude(), 
            geoCodeListDAO.getData().get(0).getLongitude()
        };
    }
}
