package com.ssg.bidssgket.user.domain.payment.application.dto.response;

public class ErrorResponse {

    private final int status;
    private final String message;

    public ErrorResponse(int status, String message) {

        this.status = status;
        this.message = message;
    }

    public int getStatus() {

        return status;
    }

    public String getMessage() {

        return message;
    }
}
