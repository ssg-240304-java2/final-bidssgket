package com.ssg.bidssgket.user.domain.productwish.application;

import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.MemberDTO;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.WishDTO;
import com.ssg.bidssgket.user.domain.productwish.domain.repository.ProductWishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductWishService {
    private final ProductWishRepository productWishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public void toggleWish(Long productNo, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productNo));

        Optional<Wish> existingWish = productWishRepository.findByMemberAndProduct(member, product);

        if (existingWish.isPresent()) {
            productWishRepository.delete(existingWish.get());
        } else {
            Wish wish = Wish.createWish(member, product);
            productWishRepository.save(wish);
        }
    }


    public boolean isProductWished(Long productNo, String email) {
        if (productNo == null) {
            return false; // 또는 예외 발생
        }
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        return productWishRepository.findByMemberNoAndProductNo(member.getMemberNo(), productNo) != null;
    }

    public List<Long> findProductNoByMemberNo(Long memberNo) {
        return productWishRepository.findProductNoByMemberNo(memberNo);
    }
}
