package br.com.cruzeirodosul.airquality.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("api/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("AIRDATA backend is healthy and running.");
    }
}
