package com.ssg.bidssgket.user.domain.eventAuction.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.eventAuction.application.EventAuctionService;
import com.ssg.bidssgket.user.domain.eventAuction.view.dto.BidMessage;
import com.ssg.bidssgket.user.domain.product.domain.Acceptance;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class LiveAuctionController {

    private final EventAuctionService eventAuctionService;

    public LiveAuctionController(EventAuctionService eventAuctionService) {
        this.eventAuctionService = eventAuctionService;
    }

    /***
     * 실시간 경매 입찰 등록
     * @param bidMessage
     * @return
     */
    @MessageMapping("auction")
    @SendTo("/pro/auction")
    public BidMessage handleBidMessage(@RequestBody BidMessage bidMessage){
        log.info("handleBidMessage:"+bidMessage);
//        BidMessage message = new BidMessage();
//        message.setMaxTenderPrice(message.getMaxTenderPrice());
        AuctionReqDto auctionReqDto = new AuctionReqDto(
                bidMessage.getMinTenderPrice(),
                bidMessage.getMaxTenderPrice(),
                bidMessage.getProductNo()
        );
        System.out.println("bidMessage = " + bidMessage.getMaxTenderPrice());
        Auction eventAuction = eventAuctionService.saveBid(auctionReqDto, bidMessage.getMemberNo());
        return bidMessage;
    }
}
