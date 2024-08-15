package com.ssg.bidssgket.user.domain.eventAuction.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.eventAuction.application.EventAuctionService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/eventAuction")
@RequiredArgsConstructor
public class EventAuctionViewController {

    private final EventAuctionService eventAuctionService;
    private final MemberRepository memberRepository;
    private final ProductService productService;


    @GetMapping("/regist")
    public String registEventProduct(Model model, HttpSession httpSession) {
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        if (memberInfo.isPresent()) {
            Long memberNo = memberInfo.get().getMemberNo();
            model.addAttribute("registProductReqDto", RegistProductReqDto.builder()
                    .memberNo(memberNo)
                    .build());
            System.out.println("memberNo = " + memberNo);;
        } else {
            throw new IllegalArgumentException("Member not found for email: " + member);
        }
        return "user/eventAuction/regist";
    }

    @PostMapping("/regist")
    public String registEventProduct(Model model, HttpSession httpSession, @ModelAttribute RegistProductReqDto registProductReqDto,
                         @RequestParam("auctionDuration")int auctionDuration, @RequestParam("productImages") List<MultipartFile> productImages) {
        eventAuctionService.registEventProduct(registProductReqDto,productImages,auctionDuration);
        return "redirect:/eventAuction/lists";
    }

    @GetMapping("/lists")
    public String eventAuctionList(Model model) {
        List<Product> eventProducts = eventAuctionService.getEventProducts();
        model.addAttribute("eventProducts", eventProducts);
        return "user/eventAuction/lists";
    }

    @GetMapping("/detail/{productNo}")
    public String detailController(Model model, @PathVariable("productNo") Long productNo,
                                   HttpSession httpSession) {
        log.info("detailController, productNo = " + productNo);
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        Long memberNo = memberInfo.get().getMemberNo();
        List<Product> products = eventAuctionService.getEventProductsByMember(memberNo);
        List<Auction> auctions = eventAuctionService.findAllByEventProductNo(productNo);
        model.addAttribute("memberNo", memberNo);
        System.out.println("memberNo = " + memberNo);
        model.addAttribute("member", member);
        boolean isSeller = products.stream()
                .anyMatch(product -> product.getProductNo().equals(productNo));
        boolean isAuction = auctions.stream()
                .anyMatch(auction -> auction.getMember().getMemberNo().equals(memberNo));
        if (isSeller) {
            return "redirect:/eventAuction/detailSeller/" + productNo;
        } else {
            if (isAuction) {
                return "redirect:/eventAuction/detailAuction/" + productNo;
            } else {
                return "redirect:/eventAuction/detailBuyer/" + productNo;
            }
        }
    }

    @GetMapping("/detailSeller/{productNo}")
    public String detailEventAuction(Model model, @PathVariable("productNo") Long productNo,HttpSession httpSession) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        Long memberNo = memberInfo.get().getMemberNo();
        model.addAttribute("memberNo", memberNo);
        System.out.println("memberNo = " + memberNo);
        model.addAttribute("product", product);
        List<Auction> auctions = productService.findAuctionByProductNo(productNo);
        model.addAttribute("auctions", auctions);
        return "user/eventAuction/detailAuction";
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
        model.addAttribute("auctions", auctions);
        return "user/eventAuction/detailAuction";
    }

    @GetMapping("/detailBuyer/{productNo}")
    public String detailBuyerController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        List<Auction> auctions = productService.findAuctionByProductNo(productNo);
        model.addAttribute("auctions", auctions);
        model.addAttribute("product", product);
        return "user/eventAuction/detailBuyer";
    }

}
