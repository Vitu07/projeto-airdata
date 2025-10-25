package br.com.cruzeirodosul.airquality.backend.controller;

import br.com.cruzeirodosul.airquality.backend.dto.AirQualityResponseDTO;
import br.com.cruzeirodosul.airquality.backend.dto.HistoricoDTO;
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
    public ResponseEntity<AirQualityResponseDTO> getAirQualityByCity(@RequestParam("city") String city){
        AirQualityResponseDTO response = service.getAirQualityDataForCity(city);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<HistoricoDTO> getHistory(@RequestParam String cityName) {
        HistoricoDTO historico = service.getDadosHistoricos(cityName);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/by-coords")
    public ResponseEntity<AirQualityResponseDTO> getAirQualityByCoords(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude) {

        AirQualityResponseDTO response = service.getAirQualityDataByCoords(latitude, longitude);
        return ResponseEntity.ok(response);
    }
}
