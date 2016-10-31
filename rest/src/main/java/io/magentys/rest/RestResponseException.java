package io.magentys.rest;

import io.magentys.rest.model.RestResponse;

public class RestResponseException extends RuntimeException {

    public RestResponseException(String message) {
        super(message);
    }

    public static <T> void throwOnResponseFailure(final RestResponse<T> response) {
        if (response.failed()) {
            throw new RestResponseException(String.format("Unexpected response status: %s, body: %s", response.statusCode(), response.body()));
        }
    }
}