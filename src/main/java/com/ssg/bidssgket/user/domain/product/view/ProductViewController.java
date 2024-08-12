package com.ssg.bidssgket.user.domain.product.view;

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
import lombok.extern.java.Log;
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
//@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;
    private final MemberRepository memberRepository;

    @GetMapping("/register")
    public String registController(Model model, HttpSession httpSession) {
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        if (memberInfo.isPresent()) {
            model.addAttribute("member", memberInfo.get().getMemberNo());
        }
        System.out.println("memberInfo.get().getMemberNo() = " + memberInfo.get().getMemberNo());
        model.addAttribute("member", member);
        model.addAttribute("registProductReqDto",RegistProductReqDto.builder().build());
        return "user/product/register";
    }

    @PostMapping("/register")
    public String registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                @RequestParam("productImages")List<MultipartFile> productImages,
                                HttpSession httpSession) {
        productService.registProduct(registProductReqDto, productImages);
        // fileDto -> entity로 바꿔서 DB에 파일 저장
        return "redirect:/user/main/mainpage";
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
    public String detailAuctionController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime auctionEndDate = product.getAuctionEndTime();
        Duration duration = Duration.between(auctionEndDate, now);
        long durationMillis = duration.toMillis();
        model.addAttribute("durationMillis", durationMillis);
        model.addAttribute("product", product);
        return "user/product/detailAuction";
    }

    @GetMapping("/detailBuyer/{productNo}")
    public String detailBuyerController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime auctionEndDate = product.getAuctionEndTime();
        Duration duration = Duration.between(auctionEndDate, now);
        long durationMillis = duration.toMillis();
        model.addAttribute("durationMillis", durationMillis);
        model.addAttribute("product", product);
        return "user/product/detailBuyer";
    }

    @GetMapping("/detailSeller/{productNo}")
    public String detailSellerController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime auctionEndDate = product.getAuctionEndTime();
        Duration duration = Duration.between(auctionEndDate, now);
        long durationMillis = duration.toMillis();
        model.addAttribute("durationMillis", durationMillis);
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

//    @GetMapping("/detail")
//    public String detailController(Model model, @PathVariable("productNo") Long productNo,
//                                   HttpSession httpSession) {
//        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
//        Optional<Member> memberInfo = memberRepository.findByEmail(member);
//        Long memberNo = memberInfo.get().getMemberNo();
//        List<Product> products = productService.getProductsByMember(memberNo);
//        return null;
//    }

}
