package com.ssg.bidssgket.admin.notification;

import com.ssg.bidssgket.admin.api.member.application.AdminMemberApiService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class NotificationController {
    private final NotificationService notificationService;
    private final AdminMemberApiService adminMemberApiService;

    /**
     * 상품 등록 구독 -> 판매자 구독
     */
//    @GetMapping(value = "/product/subscribe/seller", produces = "text/event-stream")
//    public SseEmitter subscribeProduct(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                       @RequestParam(value = "productNo") Long productNo,
//                                        @RequestParam(value = "memberNo") Long memberNo) {
//
//        log.info("===== 상품 등록 Subscribe =====");
//
//
//        return notificationService.subscribeProduct(productNo, memberNo, "product", lastEventId);
//    }

    /**
     * 경매 참가 구독 -> 구매자들 구독
     */
//    @GetMapping(value = "/auction/subscribe/buyer", produces = "text/event-stream")
//    public SseEmitter subscribeAuction(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                       @RequestParam(value = "productNo") Long productNo,
//                                       @RequestParam(value = "memberNo") Long memberNo) {
//
//        log.info("===== 경매 참가 Subscribe =====");
//
//
//        return notificationService.subscribeAuction(productNo, memberNo, "bid", lastEventId);
//    }



    /**
     * ============================================================================================================
     * ============================================================================================================
     * ============================================================================================================
     */


    /**
     * 경매 마감 알림(seller)
     */
    @GetMapping(value = "/detailSeller/admin/notify/close/seller/{productNo}", produces = "text/event-stream")
    public String pushAuctionClosedSeller(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                    @PathVariable(value = "productNo") Long productNo){

        log.info("===== 판매자 경매 마감 알림 ======");

        notificationService.sendAuctionClosedSeller(productNo, "closed", "경매가 마감되었습니다.");

        return "success";
    }

    /**
     * 경매 마감 알림(buyer)
     */
    @GetMapping(value = "/detailSeller/admin/notify/close/buyer/{productNo}", produces = "text/event-stream")
    public String pushAuctionClosedBuyer(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                    @PathVariable(value = "productNo") Long productNo){

        log.info("===== 구매자 경매 마감 알림 ======");

        notificationService.sendAuctionClosedBuyer(productNo, "closed", "경매가 마감되었습니다.");

        return "success";
    }


    /**
     * 경매 유찰 알림 d
     */
    @GetMapping(value = "/bidFailed/admin/notify/cancel/{productNo}", produces = "text/event-stream")
    public String pushAuctionCanceled(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                      @PathVariable(value = "productNo") Long productNo){

        log.info("===== 경매 유찰 알림 ======");

        notificationService.sendAuctionCanceled(productNo, "canceled", "상품의 경매가 유찰되었습니다.");

        return "success";
    }

    /**
     * 판매 상태 변경 알림(경매자) d
     */
    @GetMapping(value = "/detailSeller/admin/notify/change/{productNo}", produces = "text/event-stream")
    public String pushProductStatusChanged(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                           @PathVariable(value = "productNo") Long productNo){

        log.info("===== 판매 상태 변경 알림 ======");

        notificationService.sendProductStatusChanged(productNo, "changed", "참여한 상품의 상태가 변경되었습니다.");

        return "success";
    }


    /**
     * ============================================================================================================
     * ============================================================================================================
     * ============================================================================================================
     * 클랄랑 애픐 서버간 결제하고 db애 결제내역 쌓음
     */


    /**
     * 알림 후 연결 끊기
     */
    @DeleteMapping("/stop-notification")
    public String stopNotification(HttpSession httpSession){

        log.info("===== 알림 연결 끊기 =====");
        String email = null;
        Long memberNo = null;
        if (httpSession.getAttribute("member") != null) {
            email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            System.out.println("email = " + email);
            memberNo = adminMemberApiService.getMemberNoByEmail(email);
        }

        notificationService.deleteCache(memberNo);

        return "Stopped Notification for User";
    }


}