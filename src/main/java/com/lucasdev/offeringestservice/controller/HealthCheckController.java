package com.lucasdev.offeringestservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingest")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Servicio activo!");
    }

    @GetMapping("/healthcheck")
    public String health() {
        return "Offer Ingest Service está funcionando ✅";
    }
}
