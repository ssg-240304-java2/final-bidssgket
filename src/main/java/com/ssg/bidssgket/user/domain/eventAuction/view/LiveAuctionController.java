package com.ssg.bidssgket.user.domain.eventAuction.view;

import com.ssg.bidssgket.user.domain.eventAuction.view.dto.BidMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class LiveAuctionController {

    @MessageMapping("auction")
    @SendTo("/pro/auction")
    public BidMessage handleBidMessage(@RequestBody BidMessage bidMessage){
        log.info("handleBidMessage:"+bidMessage);
        BidMessage message = new BidMessage();
        message.setBidNo(43L);
        message.setMaxTenderPrice(99999999);
        return bidMessage;
    }
}
