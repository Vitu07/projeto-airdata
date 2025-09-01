package br.com.cruzeirodosul.airquality.backend.controller;

import br.com.cruzeirodosul.airquality.backend.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/air-quality")
public class AirQualityController {

    @Autowired
    private AirQualityService service;

    @GetMapping
    public ResponseEntity<String> getAirQualityByCity(@RequestParam("city") String city){
        String response = service.getAirQualityDataForCity(city);

        return ResponseEntity.ok(response);
    }
}
