package com.strong.PostService.Utils;

import java.io.IOException;
import java.io.InputStream;
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
                InputStream inputStream = response.body().asInputStream();
                String responseBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                try {
                    JsonNode errorResponse = objectMapper.readTree(responseBody);
                    String message = errorResponse.path("message").asText("Unknown error occurred");
                    return new BlogException(message);
                } catch (IOException jsonException) {
                    // If JSON parsing fails, handle it as plain text
                    return new BlogException(jsonException);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new BlogException("Failed to read response body", e);
        }

        return new BlogException(String.valueOf(response.status()));
    }
}
