package com.epam.userservice.dao;

import org.springframework.stereotype.Component;

/**
 * Class to return the response to a request
 */
@Component
public class Response {
    Object responseObject;
    String message;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(Object object, String message) {
        this.responseObject = object;
        this.message = message;
    }

    public void setObject(Object object) {
        this.responseObject = object;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
