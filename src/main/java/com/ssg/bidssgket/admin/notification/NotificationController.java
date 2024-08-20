//package com.ssg.bidssgket.admin.notification;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/admin/notify")
//public class NotificationController {
//    private final NotificationService notificationService;
//
//    /**
//     * 상품 등록 구독 -> 판매자 구독
//     */
//    @GetMapping(value = "/product/subscribe", produces = "text/event-stream")
//    public SseEmitter subscribeProduct(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                       @RequestParam(value = "productNo") Long productNo,
//                                        @RequestParam(value = "memberNo") Long memberNo) {
//
//        log.info("===== 상품 등록 Subscribe =====");
//
//
//        return notificationService.subscribeProduct(productNo, memberNo, "product", lastEventId);
//    }
//
//    /**
//     * 경매 참가 구독 -> 구매자들 구독
//     */
//    @GetMapping(value = "/auction/subscribe/{memberNo}", produces = "text/event-stream")
//    public SseEmitter subscribeAuction(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                       @PathVariable(value = "bidNo") Long bidNo,
//                                       @RequestParam(value = "memberNo") Long memberNo) {
//
//        log.info("===== 경매 참가 Subscribe =====");
//
//
//        return notificationService.subscribeAuction(bidNo, memberNo, "bid", lastEventId);
//    }
//
//
//
//    /**
//     * ============================================================================================================
//     * ============================================================================================================
//     * ============================================================================================================
//     */
//
//
//    /**
//     * 경매 마감 알림
//     */
//    @GetMapping(value = "/complete/{productNo}", produces = "text/event-stream")
//    public String pushAuctionClosed(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                    @PathVariable(value = "productNo") Long productNo){
//
//        log.info("===== 경매 마감 알림 ======");
//
//        notificationService.sendAuctionClosed(productNo, "closed", "경매가 마감되었습니다.");
//
//        return "success";
//    }
//
//
//    /**
//     * 경매 유찰 알림
//     */
//    @GetMapping(value = "/complete/{productNo}", produces = "text/event-stream")
//    public String pushAuctionCanceled(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                      @PathVariable(value = "productNo") Long productNo){
//
//        log.info("===== 경매 유찰 알림 ======");
//
//        notificationService.sendAuctionCanceled(productNo, "canceled", "test content");
//
//        return "success";
//    }
//
//    /**
//     * 판매 상태 변경 알림
//     */
//    @GetMapping(value = "/complete/{productNo}", produces = "text/event-stream")
//    public String pushProductStatusChanged(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
//                                           @PathVariable(value = "productNo") Long productNo){
//
//        log.info("===== 판매 상태 변경  알림 ======");
//
//        notificationService.sendProductStatusChanged(productNo, "changed", "test content");
//
//        return "success";
//    }
//
//
//    /**
//     * ============================================================================================================
//     * ============================================================================================================
//     * ============================================================================================================
//     */
//
//
//    /**
//     * 알림 후 연결 끊기
//     */
//    @DeleteMapping("/stop-notification")
//    public String stopNotification(@RequestParam(value = "memberNo") Long memberNo){
//
//        log.info("===== 알림 연결 끊기 =====");
//
//        notificationService.deleteCache(memberNo);
//
//        return "Stopped Notification for User";
//    }
//
//
//}