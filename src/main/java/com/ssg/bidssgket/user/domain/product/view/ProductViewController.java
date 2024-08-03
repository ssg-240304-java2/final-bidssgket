package com.ssg.bidssgket.user.domain.product.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 여기는 view
 */

@Slf4j
@Controller
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductViewController {
    @GetMapping("/regist")
    public String registController() {
        return "/user/product/regist";
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
