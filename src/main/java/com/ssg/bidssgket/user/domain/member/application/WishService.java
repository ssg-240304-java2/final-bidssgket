package com.ssg.bidssgket.user.domain.member.application;

import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.member.domain.repository.WishRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.WishDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;

    public Page<WishDto> getWishesByEmail(String email, Pageable pageable) {
        Page<Wish> wishPage = wishRepository.findWishesByEmail(email, pageable);

        List<WishDto> wishDtoList = wishPage.getContent().stream()
                .map(WishDto::toWishDto)  // Wish 엔티티를 WishDto로 변환
                .collect(Collectors.toList());

        return new PageImpl<>(wishDtoList, pageable, wishPage.getTotalElements());
    }

    public void removeWish(String email, Long productNo) {
        wishRepository.deleteByEmailAndProductNo(email, productNo);
    }
}
