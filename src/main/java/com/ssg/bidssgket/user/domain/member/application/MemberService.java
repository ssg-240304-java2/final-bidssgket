package com.ssg.bidssgket.user.domain.member.application;

import com.ssg.bidssgket.user.domain.member.domain.Address;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Review;
import com.ssg.bidssgket.user.domain.member.domain.Role;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.domain.repository.ReviewRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.MemberDto;
import com.ssg.bidssgket.user.domain.member.view.DTO.ReviewDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    public boolean isEmailDuplicate(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean isNicknameDuplicate(String nickname) {
        return memberRepository.findByMemberNickname(nickname).isPresent();
    }

    public Member authenticate(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return member;
    }

    public void signup(MemberDto memberDto) {
        if (isEmailDuplicate(memberDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (isNicknameDuplicate(memberDto.getMemberNickname())) {
            throw new IllegalArgumentException("Nickname already exists");
        }

        String encodedPassword = passwordEncoder.encode(memberDto.getPwd());  // 비밀번호 암호화

        Address address = addressService.convertToEntity(memberDto.getAddress());

        Member newMember = Member.builder()
                .memberName(memberDto.getMemberName())
                .memberNickname(memberDto.getMemberNickname())
                .email(memberDto.getEmail())
                .pwd(encodedPassword)
                .phone(memberDto.getPhone())
                .address(address)
                .role(Role.MEMBER) // 기본 역할 설정
                .build();

        memberRepository.save(newMember);
    }

    public void submitBuyerReview(ReviewDto reviewDto, Member reviewee, Product product) {
        // 중복 리뷰 체크
        boolean exists = reviewRepository.existsByProduct_ProductNoAndReviewee_MemberNo(product.getProductNo(), reviewee.getMemberNo());
        if (exists) {
            throw new IllegalStateException("Review already exists for this product and member combination.");
        }

        int finalRating = calculateFinalRating(reviewDto.getProductRating(), reviewDto.getSellerRating());
        Review review = Review.createReview(reviewDto.getComment(), finalRating, reviewee, product);
        reviewRepository.save(review);
    }

    private int calculateFinalRating(int productRating, int sellerRating) {
        return (productRating >= 4) ? sellerRating + 5 : sellerRating;
    }

    public void submitSellerReview(ReviewDto reviewDto, Member reviewee, Product product) {
        // 중복 리뷰 체크
        boolean exists = reviewRepository.existsByProduct_ProductNoAndReviewee_MemberNo(product.getProductNo(), reviewee.getMemberNo());
        if (exists) {
            throw new IllegalStateException("Review already exists for this product and member combination.");
        }

        Review review = Review.createReview(reviewDto.getComment(), reviewDto.getSellerRating(), reviewee, product);
        reviewRepository.save(review);
    }

    @Transactional
    public void updateBiscuitForMember(Long memberNo) {
        long count = reviewRepository.countByReviewee_MemberNoAndBiscuitRatingGreaterThanEqual(memberNo, 90);

        if (count >= 3) {
            Member member = memberRepository.findById(memberNo)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            member.incrementBiscuit();
            memberRepository.save(member);
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }
}
