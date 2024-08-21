package com.ssg.bidssgket.user.domain.eventAuction.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.eventAuction.application.EventAuctionService;
import com.ssg.bidssgket.user.domain.eventAuction.view.dto.BidMessage;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Acceptance;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
@Slf4j
public class LiveAuctionController {

    private final EventAuctionService eventAuctionService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public LiveAuctionController(EventAuctionService eventAuctionService, MemberRepository memberRepository, MemberService memberService) {
        this.eventAuctionService = eventAuctionService;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    /***
     * 실시간 경매 입찰 등록
     * @param bidMessage
     * @return
     */
    @MessageMapping("auction")
    @SendTo("/pro/auction")
    public BidMessage handleBidMessage(@RequestBody BidMessage bidMessage){
        Optional<Member> member = memberRepository.findById(bidMessage.getMemberNo());
        String email = member.get().getEmail();
        bidMessage.setEmail(email);
        log.info("handleBidMessage:"+bidMessage);
        AuctionReqDto auctionReqDto = new AuctionReqDto(
                bidMessage.getMinTenderPrice(),
                bidMessage.getMaxTenderPrice(),
                bidMessage.getProductNo()
        );
        Auction eventAuction = eventAuctionService.saveBid(auctionReqDto, bidMessage.getMemberNo());
        return bidMessage;
    }
}
