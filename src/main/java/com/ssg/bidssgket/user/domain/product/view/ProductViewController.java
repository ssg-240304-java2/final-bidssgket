package com.ssg.bidssgket.user.domain.product.view;

import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 여기는 view
 */

@Slf4j
@Controller
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/register")
    public String registController(Model model) {
        model.addAttribute("registProductReqDto",RegistProductReqDto.builder().build());
        return "/user/product/register";
    }

    @PostMapping("/register")
    public String registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                @RequestParam("prodcutImages")List<MultipartFile> prodcutImages) {
        productService.registProduct(registProductReqDto, prodcutImages);
        return "redirect:/user/product/list";
    }

    @GetMapping("/update")
    public String updateController() {
        return "/user/product/update";
    }

    @GetMapping("/bidFailed")
    public String bidFailedController() {
        return "/user/product/bidFailed";
    }

    @GetMapping("/bidSuccess")
    public String bidSuccessController() {
        return "/user/product/bidSuccess";
    }

    @GetMapping("/detailAuction")
    public String detailAuctionController() {
        return "/user/product/detailAuction";
    }

    @GetMapping("/detailBuyer")
    public String detailBuyerController() {
        return "/user/product/detailBuyer";
    }

    @GetMapping("/detailSeller")
    public String detailSellerController() {
        return "/user/product/detailSeller";
    }

}
