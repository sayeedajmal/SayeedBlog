package com.strong.PostService.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class Actuator {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/info")
    public ResponseEntity<?> info() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Status", "UP");
        info.put("ServiceName", "PostService");
        info.put("Version", appVersion);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
}
