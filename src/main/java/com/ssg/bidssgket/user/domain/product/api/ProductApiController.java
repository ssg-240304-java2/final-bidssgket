package com.ssg.bidssgket.user.domain.product.api;

import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.api.dto.response.ProductApiResDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 여기는 RestController
 *
 */

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<ProductApiResDto> registProduct(@ModelAttribute RegistProductReqDto registProductReqDto) {
        Product registProduct = productService.registProduct(registProductReqDto);
        ProductApiResDto productApiResDto = new ProductApiResDto(registProduct);
        return ResponseEntity.ok(productApiResDto);
    }
}
