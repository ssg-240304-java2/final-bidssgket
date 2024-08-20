package com.ssg.bidssgket.user.domain.productwish.application;

import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.MemberDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.productwish.domain.repository.ProductWishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductWishService {
    @Autowired
    private ProductWishRepository productWishRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    /*public void toggleWish(Long productNo, String email) {
        MemberDto member = memberRepository.findMemberByEmail(email);
        Product product = productService.findProductById(productNo);

        int count = productWishRepository.findByMemberNoAndProductNo(member.getMemberNo(), productNo);

        if (count > 0) {
            productWishRepository.deleteByMemberNoAndProductNo(member.getMemberNo(), productNo);
        } else {
            Wish wish = Wish.createWish(member, product);
            productWishRepository.save(wish);
        }
    }

    public boolean isProductWished(Long productNo, Long memberNo) {
        return productWishRepository.findByMemberNoAndProductNo(memberNo, productNo) > 0;
    }*/

}
