package com.strong.AuthorService.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Actuator class handles HTTP requests related to service health checks and
 * token retrieval.
 * This controller uses the @RestController annotation to indicate that it's a
 * controller and automatically serializes the returned objects into JSON
 * format.
 */
@RestController
public class Actuator {
    @Value("${app.version}")
    private String appVersion;

    /**
     * GET endpoint to check the health status of the service.
     *
     * @return A response indicating the health status of the service with a message
     *         "UP" and HTTP status code 200 (OK).
     */
    @GetMapping("actuator/info")
    public ResponseEntity<?> info() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Status", "UP");
        info.put("ServiceName", "AuthorService");
        info.put("Version", appVersion);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
}
