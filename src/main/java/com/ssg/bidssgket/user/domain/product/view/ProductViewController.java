package com.ssg.bidssgket.user.domain.product.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
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


    @GetMapping("/register")
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

    @PostMapping("/register")
    public String registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                @RequestParam("productImages")List<MultipartFile> productImages,
                                HttpSession httpSession) {
        System.out.println("registProductReqDto.getMemberNo() = " + registProductReqDto.getMemberNo());
        productService.registProduct(registProductReqDto, productImages);
        return "redirect:/list";
    }

    @GetMapping("/update/{productNo}")
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
        return "redirect:/user/main/mainpage";
    }


    @GetMapping("/bidFailed")
    public String bidFailedController() {
        return "/user/product/bidFailed";
    }

    @GetMapping("/bidSuccess")
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

    @GetMapping("/detailSeller/{productNo}")
    public String detailSellerController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        List<Auction> auctions = productService.findAuctionByProductNo(productNo);
        model.addAttribute("auctions", auctions);
        model.addAttribute("product", product);
        return "user/product/detailSeller";
    }

    @GetMapping("/category/{category}")
    public String categoryController(Model model, @PathVariable("category") Category category) {
        log.info("category: {}", category);
        List<Product> products = productService.getProductsByCategory(category);
        model.addAttribute("products", products);
        return "user/product/category";
    }

    @GetMapping("/list")
    public String listController(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
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
        model.addAttribute("memberNo", memberNo);
        System.out.println("memberNo = " + memberNo);
        model.addAttribute("member", member);
        boolean isSeller = products.stream()
                                    .anyMatch(product -> product.getProductNo().equals(productNo));
        boolean isAuction = auctions.stream()
                                    .anyMatch(auction -> auction.getMember().getMemberNo().equals(memberNo));
        boolean onAuction = onAuctions.stream()
                .anyMatch(auction -> auction.getTenderDeleted().equals(false));

        if (isSeller) {
            return "redirect:/detailSeller/" + productNo;
        }else {
            if (isAuction && onAuction) {
                return "redirect:/detailAuction/" + productNo;
            } else {
                return "redirect:/detailBuyer/" + productNo;
            }
        }
    }

}
