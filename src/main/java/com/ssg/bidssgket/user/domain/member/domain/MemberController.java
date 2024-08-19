package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.ReviewDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final AuctionRepository auctionRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "user/member/login";
    }

    @GetMapping("/info")
    public String getMemberInfo(Model model, HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member"); // 세션에서 로그인된 사용자 정보 가져오기
        if (sessionMember == null) {   // 세션에 사용자가 없을 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();  // 이메일로 사용자 정보 조회
        model.addAttribute("memberName", member.getMemberName());
        model.addAttribute("phone", member.getPhone());
        model.addAttribute("nickname", member.getMemberNickname());
        model.addAttribute("biscuit", member.getBiscuit());
        model.addAttribute("address", member.getAddress());
        
        return "user/member/info";
    }

    @PostMapping("/user/info/update")
    public String updateMemberInfo(@RequestParam("memberName") String memberName,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("postcode") String postcode,
                                   @RequestParam("address") String address,
                                   @RequestParam("detailAddress") String detailAddress,
                                   HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");

        if (sessionMember != null) {
            Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();
            member.setMemberName(memberName);
            member.setPhone(phone);

            Address newAddress = new Address(postcode, address, detailAddress);
            member.setAddress(newAddress);

            memberRepository.save(member);
        }

        return "redirect:/user/mypage";
    }

    @GetMapping("/reviewSeller")
    public String sellerReviewPage(Model model, HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        if (sessionMember == null) {
            return "redirect:/login";
        }

        Long exampleProductNo = 18L;  // 예제 상품 번호

        Product product = productRepository.findById(exampleProductNo).orElseThrow();

        model.addAttribute("productNo", exampleProductNo);
        model.addAttribute("sellerRatingValue","");
        model.addAttribute("comments", "");

        return "user/member/review_seller";
    }

    @GetMapping("/reviewBuyer")
    public String BuyerReviewPage(Model model, HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        if (sessionMember == null) {
            return "redirect:/login";
        }

        // 예제 상품 번호, 실제로는 동적으로 가져와야 함
        Long exampleProductNo = 18L;  // 예제 상품 번호

        // 상품 정보 조회
        Product product = productRepository.findById(exampleProductNo).orElseThrow();

        model.addAttribute("productNo", exampleProductNo);
        model.addAttribute("productRatingValue", 0);
        model.addAttribute("sellerRatingValue","");
        model.addAttribute("comments", "");

        return "user/member/review_buyer";
    }

    @PostMapping("/submit-buyer-review")
    @Transactional
    public String submitBuyerReview(@RequestParam(name = "productNo") Long productNo,
                                    @RequestParam(name = "product-rating") int productRating,
                                    @RequestParam(name = "seller-rating") int sellerRating,
                                    @RequestParam(name = "comments") String comments,
                               HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");

        if (sessionMember != null) {
            Product product = productRepository.findById(productNo).orElseThrow();
            Member reviewee = product.getMember();

            ReviewDto reviewDto = ReviewDto.builder()
                    .productRating(productRating)
                    .sellerRating(sellerRating)
                    .comment(comments)
                    .reviewee(reviewee.getEmail())
                    .productNo(productNo.toString())
                    .build();
            memberService.submitBuyerReview(reviewDto, reviewee, product);

            // 리뷰를 제출한 후, memberNo의 biscuit을 업데이트
            memberService.updateBiscuitForMember(reviewee.getMemberNo());
        }

        return "redirect:/user/mypage";
    }

    @PostMapping("/submit-seller-review")
    @Transactional
    public String submitSellerReview(@RequestParam(name = "productNo") Long productNo,
                                     @RequestParam(name = "buyer-rating") int buyerRating,
                                     @RequestParam(name = "comments") String comments,
                                     HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");

        if (sessionMember != null) {
            Product product = productRepository.findById(productNo).orElseThrow();

            // productNo와 bidSuccess가 true인 Auction 엔티티를 찾는다
            Auction successfulAuction = auctionRepository.findByProductAndBidSuccessTrue(product)
                    .orElseThrow(() -> new RuntimeException("Successful auction not found for productNo: " + productNo));

            Member reviewee = successfulAuction.getMember();

            ReviewDto reviewDto = ReviewDto.builder()
                    .sellerRating(buyerRating)
                    .comment(comments)
                    .reviewee(reviewee.getEmail())
                    .productNo(productNo.toString())
                    .build();

            memberService.submitSellerReview(reviewDto, reviewee, product);

            // 리뷰를 제출한 후, memberNo의 biscuit을 업데이트
            memberService.updateBiscuitForMember(reviewee.getMemberNo());
        }

        return "redirect:/user/mypage";
    }


//    @GetMapping("/private")
//    public String privatePage() {
//        return "user/main/mainpage";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage() {
//        return "user/member/review_seller.admin";
//    }

//      @GetMapping()
//      public String showReviewPage(Principal principal, HttpSession httpSession) {
//          System.out.println("principal = " + principal);
//          SessionMember member = (SessionMember) httpSession.getAttribute("member");
//          System.out.println("---->"+member); //--> 세션확인용
//          return "";
//      }
}