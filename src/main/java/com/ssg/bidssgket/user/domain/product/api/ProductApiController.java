package com.ssg.bidssgket.user.domain.product.api;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import com.ssg.bidssgket.global.util.ncps3.FileService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.api.dto.response.ProductApiResDto;
//import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final FileService fileService;
    private final MemberRepository memberRepository;

    @PostMapping("/register")
    public ResponseEntity<ProductApiResDto> registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                                          @RequestParam("productImages") List<MultipartFile> productImages,
                                                          HttpSession httpSession) {
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        Long registMember = memberInfo.get().getMemberNo();
        registProductReqDto.setMemberNo(registMember);
        Product registProduct = productService.registProduct(registProductReqDto, productImages);
        ProductApiResDto productApiResDto = new ProductApiResDto(registProduct);
        return ResponseEntity.ok(productApiResDto);
    }

    @DeleteMapping("/image")
    @ResponseBody
    public void deleteProductImage(@RequestParam Long deleteImageId) {
        System.out.println("productId = " + deleteImageId);
        try {
            productService.deleteImage(deleteImageId);
        }catch (Exception e){
            System.out.println("error!!!!");
        }
    }

    @PostMapping("/addImage")
    public void addProductImage(@RequestParam("productImages") List<MultipartFile> productImages,
                                @RequestParam Long productNo) {
        productService.addImage(productImages,productNo);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Long productNo) {

        productService.deleteProductByNo(productNo);
        return ResponseEntity.ok("Product deleted");
    }
  
}