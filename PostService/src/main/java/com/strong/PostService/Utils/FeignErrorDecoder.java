package com.strong.PostService.Utils;

import org.springframework.stereotype.Component;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 409) {
            return new BlogException("Author Not Found by this Id: ");
        }
        return new Default().decode(methodKey, response);
    }
}
