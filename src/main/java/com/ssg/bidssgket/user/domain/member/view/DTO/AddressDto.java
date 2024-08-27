package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.Address;
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

    // Address 엔티티로 변환하는 메서드
    public Address toEntity() {
        return Address.builder()
                .postcode(this.postcode)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .build();
    }
}
