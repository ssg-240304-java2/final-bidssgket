package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    private String addrUUID;
    private String zipcode;
    private String street;
    private String city;

    // 한회원이 여러개의 주소를 가질 수 있음. 주소를 entity로 써도되나? 이미 embeddable인데?

    private void setAddrUUID(){
        UUID uuid = UUID.randomUUID();
        this.addrUUID = uuid.toString();
    }
}
