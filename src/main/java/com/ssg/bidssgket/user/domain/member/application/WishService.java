package com.ssg.bidssgket.user.domain.member.application;

import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.member.domain.repository.WishRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.WishDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    // 이메일로 Wish 리스트 조회
    public List<WishDto> getWishesByEmail(String email) {
        List<Wish> wishList = wishRepository.findByMemberEmail(email);
        /*List<WishDto> wishDtoList = new ArrayList<>();
        for (Wish wish : wishList) {
            WishDto wishDto = WishDto.toWishDto(wish);
            wishDtoList.add(wishDto);
        }
        return wishDtoList;*/
        return wishList.stream().map(WishDto::toWishDto).toList();
    }

    // 제품 번호로 Product 조회
    public Product getProductByProductNo(Long productNo) {
        return productRepository.findById(productNo).orElse(null);
    }
    public void removeWish(String email, Long productNo) {
        wishRepository.deleteByEmailAndProductNo(email, productNo);
    }

}
