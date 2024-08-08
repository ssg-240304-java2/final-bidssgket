package com.ssg.bidssgket.user.domain.payment.exception;

public enum ExceptionCode {

    PAYMENT_CANCEL("결제가 취소되었습니다."),
    PAYMENT_FAILED("결제가 실패했습니다."),
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    INVALID_PAYMENT("잘못된 결제 정보입니다."),
    PAYMENT_DECLINED("결제가 거부되었습니다."),
    INSUFFICIENT_BALANCE("잔액이 부족합니다.");

    private final String message;

    ExceptionCode(String message) {

        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}
