package com.ssg.bidssgket.user.domain.product.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.admin.notification.NotificationService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.eventAuction.application.EventAuctionService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import com.ssg.bidssgket.user.domain.productwish.application.ProductWishService;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 여기는 view
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;
    private final MemberRepository memberRepository;
    private final EventAuctionService eventAuctionService;
    private final AuctionService auctionService;
    private final NotificationService notificationService;
    private final ProductWishService productWishService;


    @GetMapping(value = "/register", produces = "text/event-stream")
    public String registController(Model model, HttpSession httpSession) {
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        if (memberInfo.isPresent()) {
            Long memberNo = memberInfo.get().getMemberNo();
            model.addAttribute("registProductReqDto",RegistProductReqDto.builder()
                    .memberNo(memberNo)
                    .build());
            System.out.println("memberNo = " + memberNo);;
        } else {
            throw new IllegalArgumentException("Member not found for email: " + member);
        }
        return "user/product/register";
    }

    @PostMapping(value = "/register", produces = "text/event-stream")
    public String registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                @RequestParam("productImages")List<MultipartFile> productImages,
                                HttpSession httpSession) {
        System.out.println("registProductReqDto.getMemberNo() = " + registProductReqDto.getMemberNo());
        Product product = productService.registProduct(registProductReqDto, productImages);

        /** ===== 알림 시작, 상품 등록 구독 -> 판매자 구독 =====*/
        String email = null;
        Long memberNo = null;
        if (httpSession.getAttribute("member") != null) {
            email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            System.out.println("email = " + email);
            memberNo = memberRepository.findByEmail(email).get().getMemberNo();
        }

        notificationService.subscribeProduct(product.getProductNo(), memberNo, "product");

        /** ===== 알림 끝 =====*/

        return "redirect:/list";
    }

    @GetMapping(value = "/update/{productNo}", produces = "text/event-stream")
    public String updatePageController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        model.addAttribute("product", product);
        return "user/product/update";
    }

    @PostMapping("/update/{productNo}")
    public String updateProduct(@PathVariable("productNo") Long productNo,
                                @ModelAttribute ProductReqDto productReqDto) {
        productService.updateProduct(productReqDto);
        return "redirect:/detailSeller/" + productNo;
    }


    @GetMapping(value = "/bidFailed", produces = "text/event-stream")
    public String bidFailedController() {
        return "/user/product/bidFailed";
    }

    @GetMapping(value = "/bidSuccess", produces = "text/event-stream")
    public String bidSuccessController() {
        return "/user/product/bidSuccess";
    }

    @GetMapping("/detailAuction/{productNo}")
    public String detailAuctionController(Model model, @PathVariable("productNo") Long productNo,HttpSession httpSession) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        Long memberNo = memberInfo.get().getMemberNo();
        model.addAttribute("memberNo", memberNo);
        System.out.println("memberNo = " + memberNo);
        model.addAttribute("product", product);
        List<Auction> auctions = productService.findAuctionByProductNo(productNo);
        if(product.getSalesStatus().equals(SalesStatus.trading.toString())){
            return "redirect:/";
        }
        model.addAttribute("auctions", auctions);
        return "user/product/detailAuction";
    }

    @GetMapping("/detailBuyer/{productNo}")
    public String detailBuyerController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        List<Auction> auctions = productService.findAuctionByProductNo(productNo);
        model.addAttribute("auctions", auctions);
        model.addAttribute("product", product);
        return "user/product/detailBuyer";
    }

    @GetMapping(value = "/detailSeller/{productNo}", produces = "text/event-stream")
    public String detailSellerController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        List<Auction> auctions = productService.findAuctionByProductNo(productNo);

        model.addAttribute("auctions", auctions);
        model.addAttribute("product", product);
        log.info("상품 상태 확인 {}", product.getSalesStatus());
        if (product.getSalesStatus().equals(SalesStatus.sale_pause.toString())) {
            System.out.println("product.getSalesStatus() = " + product.getSalesStatus());
            System.out.println("유찰된 상품 처리");
            return "redirect:/auction/bidFailed/" + productNo;
        }
        return "user/product/detailSeller";
    }

    @GetMapping("/category/{category}")
    public String categoryController(Model model, @PathVariable("category") Category category, HttpSession httpSession) {
        log.info("category: {}", category);
        List<Product> products = productService.getProductsByCategory(category);
        model.addAttribute("category", category.name());
        model.addAttribute("products", products);
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        List<Long> wishedProductIds = new ArrayList<>();

        if (sessionMember != null) {
            MemberDTO member = auctionService.getMemberByEmail(sessionMember.getEmail());
            wishedProductIds = productWishService.findProductNoByMemberNo(member.getMemberNo());
            System.out.println("wishedProductIds = " + wishedProductIds);
        }
        model.addAttribute("wishedProductIds", wishedProductIds);
        return "user/product/category";
    }

    @GetMapping("/list")
    public String listController(Model model, HttpSession httpSession) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        List<Long> wishedProductIds = new ArrayList<>();

        if (sessionMember != null) {
            MemberDTO member = auctionService.getMemberByEmail(sessionMember.getEmail());
            wishedProductIds = productWishService.findProductNoByMemberNo(member.getMemberNo());
            System.out.println("wishedProductIds = " + wishedProductIds);
        }
        model.addAttribute("wishedProductIds", wishedProductIds);
        return "user/product/list";
    }

    @GetMapping("/detail/{productNo}")
    public String detailController(Model model, @PathVariable("productNo") Long productNo,
                                   HttpSession httpSession) {
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        Long memberNo = memberInfo.get().getMemberNo();
        List<Product> products = productService.getProductsByMember(memberNo);
        List<Auction> auctions = productService.findAllByProductNo(productNo);
        List<Auction> onAuctions = productService.findDeleteAuction(memberNo);
        List<Product> eventProducts = eventAuctionService.getEventAuctionProducts(productNo);
        model.addAttribute("memberNo", memberNo);
        System.out.println("memberNo = " + memberNo);
        model.addAttribute("member", member);
        boolean isSeller = products.stream()
                .anyMatch(product -> product.getProductNo().equals(productNo));
        /*boolean isAuction = auctions.stream()
                .anyMatch(auction -> auction.getMember().getMemberNo().equals(memberNo));*/
        boolean isAuction = auctionService.findAuctionMember(productNo, memberInfo.get().getMemberNo());
        /*boolean onAuction = onAuctions.stream()
                .anyMatch(auction -> auction.getTenderDeleted().equals(false));*/
        boolean eventAuction = eventProducts.stream()
                .anyMatch(product -> product.getProductNo().equals(productNo));

        System.out.println("isAuction = " + isAuction);
        if (eventAuction) {
                return "redirect:/eventAuction/detail/" + productNo;
        } else {
            if (isSeller) {
                log.info("판매자");
                return "redirect:/detailSeller/" + productNo;
            } else {
                if (isAuction) {
                    log.info("입찰 등록 및 회원");
                    return "redirect:/detailAuction/" + productNo;
                } else {
                    log.info("삭제된 경우 혹은 입찰자 아닌 사람");
                    return "redirect:/detailBuyer/" + productNo;
                }
            }
        }
    }

}
