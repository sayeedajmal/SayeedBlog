package com.strong.AuthorService.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Actuator class handles HTTP requests related to service health checks and
 * token retrieval.
 * This controller uses the @RestController annotation to indicate that it's a
 * controller and automatically serializes the returned objects into JSON
 * format.
 */
@RestController
@RequestMapping("/api")
public class Actuator {
    /**
     * GET endpoint to check the health status of the service.
     *
     * @return A response indicating the health status of the service with a message
     *         "UP" and HTTP status code 200 (OK).
     */
    @GetMapping("actuator/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("UP", HttpStatus.OK);
    }
}
