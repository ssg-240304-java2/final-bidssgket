package com.ssg.bidssgket.user.domain.payment.exception;

import java.util.function.Supplier;

public class BusinessLogicException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public BusinessLogicException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public BusinessLogicException(ExceptionCode exceptionCode, Supplier<String> messageSupplier) {
        super(messageSupplier.get());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {

        return exceptionCode;
    }
}
