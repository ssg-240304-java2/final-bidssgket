package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class Address {
    private String addrUUID;
    private String zipcode;
    private String street;
    private String city;

    private void setAddrUUID(){
        UUID uuid = UUID.randomUUID();
        this.addrUUID = uuid.toString();
    }

    @Override
    public String toString() {
        return "Address{" +
                "addrUUID='" + addrUUID + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
