package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.application.WishService;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.domain.repository.WishRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.*;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final AuctionRepository auctionRepository;
    private final ProductService productService;
    private final WishService wishService;

    @GetMapping("/login")  // 로그인 페이지
    public String loginPage() {
        return "user/member/login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "user/member/login";
    }

    @PostMapping("/user/signup")
    public String signup(@RequestParam("name") String memberName,
                         @RequestParam("nickname") String memberNickname,
                         @RequestParam("id") String email,
                         @RequestParam("password") String pwd,
                         @RequestParam("phone") String phone,
                         @RequestParam("postcode") String postcode,
                         @RequestParam("address") String address,
                         @RequestParam("detailAddress") String detailAddress,
                         HttpServletRequest request) {

        // 이메일과 닉네임 중복 체크
        if (memberService.isEmailDuplicate(email)) {
            return "redirect:/signup?error=email";
        }

        if (memberService.isNicknameDuplicate(memberNickname)) {
            return "redirect:/signup?error=nickname";
        }

        // 주소 및 멤버 DTO 생성
        AddressDto addressDto = AddressDto.builder()
                .postcode(postcode)
                .address(address)
                .detailAddress(detailAddress)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .memberName(memberName)
                .memberNickname(memberNickname)
                .email(email)
                .pwd(pwd)
                .phone(phone)
                .address(addressDto)
                .build();

        // 회원가입 처리
        memberService.signup(memberDto);

        // 로그인 후 세션 처리
        Member member = memberService.findByEmail(email);
        SessionMember sessionMember = new SessionMember(member);

        HttpSession session = request.getSession();
        session.setAttribute("member", sessionMember);

        return "redirect:/login";
    }

    @PostMapping("/check-duplicate")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkDuplicate(@RequestParam(required = false) String email,
                                                               @RequestParam(required = false) String nickname) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("emailError", email != null && memberService.isEmailDuplicate(email));
        response.put("nicknameError", nickname != null && memberService.isNicknameDuplicate(nickname));

        return ResponseEntity.ok(response);
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

    @GetMapping("/wish")
    public String getWish(Model model, HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "0") int page) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        if (sessionMember == null) {
            return "redirect:/login";
        }

        int pageSize = 4;   // 한 페이지에 4개의 상품 표시
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<WishDto> wishPage = wishService.getWishesByEmail(sessionMember.getEmail(), pageable);
        model.addAttribute("wishes", wishPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", wishPage.getTotalPages());

        return "user/member/wishlist";
    }

    @PostMapping("/wishlist/delete")
    public String deleteWish(@RequestParam Long productNo, HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        if (sessionMember == null) {
            return "redirect:/login";
        }
        wishService.removeWish(sessionMember.getEmail(), productNo);
        return "redirect:/wish";
    }

    @GetMapping("/detailBuyer")
    public String getProductDetail(@RequestParam("productNo") Long productNo, Model model) {
        ProductResDto productResDto = productService.findProductByNo(productNo);

        model.addAttribute("product", productResDto);

        return "user/product/detailBuyer";
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