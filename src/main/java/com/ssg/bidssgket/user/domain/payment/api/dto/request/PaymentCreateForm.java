package com.ssg.bidssgket.user.domain.payment.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * 페이 결제 상품 정보
 */
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class PaymentCreateForm {

    private String name; // 페이 결제 상품 이름
    private int totalPrice; // 주문 금액

    public PaymentCreateForm(String name, int totalPrice) {
        this.name = name;
        this.totalPrice = totalPrice;
    }

    // setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
