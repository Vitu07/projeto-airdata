package br.com.cruzeirodosul.airquality.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirQualityService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_URL}")
    private String apiURL;

    @Value("${EXTERNAL_API_KEY}")
    private String apiKey;

    public String getAirQualityDataForCity(String city){
        String url = String.format("%s/feed/%s/?token=%s", apiURL, city, apiKey);

        try{
            return restTemplate.getForObject(url, String.class);
        }catch(Exception e){
            return "{\"status\":\"error\", \"message\":\"Não foi possível buscar os dados para a cidade: " + city + "\"}";
        }
    }
}
