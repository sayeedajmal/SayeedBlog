package com.strong.PostService.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() != null) {
                String responseBody = new String(response.body().asInputStream().readAllBytes(),
                        StandardCharsets.UTF_8);

                JsonNode errorResponse = objectMapper.readTree(responseBody);

                String message = errorResponse.path("message").asText("No message available");

                return new BlogException(
                        String.format("%s", message));
            }
        } catch (IOException e) {
            return new BlogException("Failed to read or parse response body", e);
        }

        return new BlogException("Unknown error occurred");
    }
}
