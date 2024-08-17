package com.ssg.bidssgket.user.domain.eventAuction.api;

import com.ssg.bidssgket.global.util.ncps3.FileService;
import com.ssg.bidssgket.user.domain.eventAuction.application.EventAuctionService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.api.dto.response.ProductApiResDto;
import com.ssg.bidssgket.user.domain.product.application.ProductReportService;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/eventAuction")
@RequiredArgsConstructor
public class EventAuctionApiController {

    private final EventAuctionService eventAuctionService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProductReportService productReportService;

    @PostMapping("/regist")
    public ResponseEntity<ProductApiResDto> registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                                          @RequestParam("productImages") List<MultipartFile> productImages,
                                                          @RequestParam("auctionDuration")int auctionDuration,
                                                          HttpSession httpSession) {
        String member = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Optional<Member> memberInfo = memberRepository.findByEmail(member);
        Long registMember = memberInfo.get().getMemberNo();
        registProductReqDto.setMemberNo(registMember);
        registProductReqDto.setAuctionStartTime(LocalDateTime.now());
        registProductReqDto.setAuctionEndTime(LocalDateTime.now().plusHours(auctionDuration));
        Product registProduct = eventAuctionService.registEventProduct(registProductReqDto, productImages,auctionDuration);
        ProductApiResDto productApiResDto = new ProductApiResDto(registProduct);
        return ResponseEntity.ok(productApiResDto);
    }

}
