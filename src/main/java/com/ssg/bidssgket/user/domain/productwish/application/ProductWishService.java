package com.ssg.bidssgket.user.domain.productwish.application;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.MemberDto;
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
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void addWish(Long productNo, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (productWishRepository.findByMemberNoAndProductNo(member.getMemberNo(),productNo) == 0) {
            Wish wish = Wish.createWish(member, product);
            productWishRepository.save(wish);
        }
    }

    @Transactional
    public void removeWish(Long productNo, Long memberNo) {
        Optional<Wish> wish = productWishRepository.findByMemberNo(memberNo);
//        Member member = memberRepository.findByMemberNo(memberNo);
//        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        if (wish.isPresent()) {
            Wish removewish = productWishRepository.findByProduct(productNo);
            productWishRepository.delete(removewish);
        }
    }


    public List<Wish> getWishListByMemberNo(Long memberNo) {
        return productWishRepository.findWishByMember(memberNo);
    }
}
