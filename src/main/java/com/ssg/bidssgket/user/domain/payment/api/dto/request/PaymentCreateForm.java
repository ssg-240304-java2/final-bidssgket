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
    private String email; // 이메일

    public PaymentCreateForm(String name, int totalPrice, String email) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.email = email;
    }

    // setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
