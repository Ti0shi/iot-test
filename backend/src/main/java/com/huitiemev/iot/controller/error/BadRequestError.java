package com.huitiemev.iot.controller.error;

public class BadRequestError {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BadRequestError(String message) {
        this.message = message;
    }
}
