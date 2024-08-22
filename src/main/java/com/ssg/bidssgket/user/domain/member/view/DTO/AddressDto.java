package com.ssg.bidssgket.user.domain.member.view.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressDto {

    private String addrUUID;
    private String postcode;
    private String address;
    private String detailAddress;

    @Builder
    public AddressDto(String addrUUID, String postcode, String address, String detailAddress) {
        this.addrUUID = addrUUID;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
