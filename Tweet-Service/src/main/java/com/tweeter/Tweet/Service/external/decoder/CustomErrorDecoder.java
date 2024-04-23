package com.tweeter.Tweet.Service.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweeter.Tweet.Service.exception.CustomException;
import com.tweeter.Tweet.Service.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.body() == null) {
            return new CustomException("Response body is null", response.reason(), response.status());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body()
                    .asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getMessage(), errorResponse.getDetails(), response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error", "INTERNAL_SERVER_ERROR", 500);
        }
    }
}
