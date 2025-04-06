package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config.ApiPathsConfig.HEALTH_PATH;

@RestController
@RequestMapping(HEALTH_PATH)
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "UP");
        healthStatus.put("timestamp", LocalDateTime.now().toString());
        healthStatus.put("service", "insurance-policy-service");

        return ResponseEntity.ok(healthStatus);
    }
}
