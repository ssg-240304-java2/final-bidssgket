package com.ssg.bidssgket.user.domain.member.application;

import com.ssg.bidssgket.user.domain.member.domain.Address;
import com.ssg.bidssgket.user.domain.member.view.DTO.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    public Address convertToEntity(AddressDto addressDto) {
        return Address.builder()
                .postcode(addressDto.getPostcode())
                .address(addressDto.getAddress())
                .detailAddress(addressDto.getDetailAddress())
                .build();
    }
}
