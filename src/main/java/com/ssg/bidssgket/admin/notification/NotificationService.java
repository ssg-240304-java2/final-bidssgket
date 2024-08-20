//package com.ssg.bidssgket.admin.notification;
//
//import com.ssg.bidssgket.admin.api.product.auction.service.AdminAuctionService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class NotificationService {
//    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
//    private final EmitterLocalRepository emitterLocalRepository;
//    private final AdminAuctionService adminAuctionService;
//
//
//
//    /**
//     * 상품 등록 구독 -> 판매자 구독
//     */
//    public SseEmitter subscribeProduct(Long productNo, Long memberNo, String eventName, String lastEventId) {
//        String emitterId = makeTimeIncludeId(productNo, memberNo);
//        SseEmitter emitter = emitterLocalRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
//
//        emitter.onCompletion(() -> emitterLocalRepository.deleteById(emitterId));
//        emitter.onTimeout(() -> emitterLocalRepository.deleteById(emitterId));
//
//        // 503 에러를 방지하기 위한 더미 이벤트 전송
//        String eventId = makeTimeIncludeId(productNo, memberNo);
//        sendNotification(emitter, eventId, emitterId, eventName,"Product EventStream Created. [memberNo=" + memberNo + "]");
//
//        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
//        if (hasLostData(lastEventId)) {
//            sendLostData(lastEventId, memberNo, emitterId, eventName, emitter);
//        }
//
//        return emitter;
//    }
//
//
//    /**
//     * 경매 참가 구독 -> 구매자들 구독
//     */
//    public SseEmitter subscribeAuction(Long bidNo, Long memberNo, String eventName, String lastEventId) {
//        String emitterId = makeTimeIncludeId(bidNo, memberNo);
//        SseEmitter emitter = emitterLocalRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
//
//        emitter.onCompletion(() -> emitterLocalRepository.deleteById(emitterId));
//        emitter.onTimeout(() -> emitterLocalRepository.deleteById(emitterId));
//
//        // 503 에러를 방지하기 위한 더미 이벤트 전송
//        String eventId = makeTimeIncludeId(bidNo, memberNo);
//        sendNotification(emitter, eventId, emitterId, eventName, "Auction EventStream Created. [userEmail=" + memberNo + "]");
//
//        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
//        if (hasLostData(lastEventId)) {
//            sendLostData(lastEventId, memberNo, emitterId, eventName, emitter);
//        }
//
//        return emitter;
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
//     * 경매 마감 알림 보내기
//     */
//    public void sendAuctionClosed(Long productNo, String eventName, String content) {
//
//        // Auction에서 bidNo & 낙찰여부 -> 판매자까지
////        String eventId = bidNo + "_" + memberNo + "_" + System.currentTimeMillis();
//
//        adminAuctionService.sendAuctionClosed(productNo);
//
//        String eventId = makeTimeIncludeId(productNo, memberNo);
//
//        Map<String, SseEmitter> emitters = emitterLocalRepository.findAllEmitterStartWithByMemberId( memberNo + "_" + productNo);
//
//
//        log.info("===== sendAuctionClosed service =====");
//
//        emitters.forEach(
//                (key, emitter) -> {
//                    log.info(key + " == : " + emitters.get(key).toString());
//                    emitterLocalRepository.saveEventCache(key, content);
//                    sendNotification(emitter, eventId, key, eventName, content);
//
//                }
//        );
//
//    }
//
//
//    /**
//     * 경매 유찰 알림 보내기
//     */
//    public void sendAuctionCanceled(Long productNo, String eventName, String content) {
//        // Product 판매자한테만 보내기
////        String eventId = productNo + "_" + memberNo + "_" + System.currentTimeMillis();
//        String eventId = makeTimeIncludeId(productNo, memberNo);
//
//        Map<String, SseEmitter> emitters = emitterLocalRepository.findAllEmitterStartWithByMemberId( productNo + "_" + memberNo);
//
//
//        log.info("===== sendAuctionCanceled service =====");
//
//        emitters.forEach(
//                (key, emitter) -> {
//                    log.info(key + " == : " + emitters.get(key).toString());
//                    emitterLocalRepository.saveEventCache(key, content);
//                    sendNotification(emitter, eventId, key, eventName, content);
//
//                }
//        );
//
//    }
//
//
//    /**
//     * 판매 상태 변경 알림
//     */
//    public void sendProductStatusChanged(Long productNo, String eventName, String content) {
//        // Auction에서 productNo에 해당하는 애들 다 가져오기
//        // for문 돌리면서
////        String eventId = productNo + "_" + memberNo + "_" + System.currentTimeMillis();
//        String eventId = makeTimeIncludeId(productNo, memberNo);
//
//        Map<String, SseEmitter> emitters = emitterLocalRepository.findAllEmitterStartWithByMemberId( bidNo + "_" + memberNo);
//
//
//        log.info("===== sendProductStatusChanged service =====");
//
//        emitters.forEach(
//                (key, emitter) -> {
//                    log.info(key + " == : " + emitters.get(key).toString());
//                    emitterLocalRepository.saveEventCache(key, content);
//                    sendNotification(emitter, eventId, key, eventName, content);
//
//                }
//        );
//
//    }
//
//
//
//    /**
//     * push 보내기 보내기
//     */
//    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, String eventName, Object data) {
//        try {
//            emitter.send(SseEmitter.event()
//                    .id(eventId)
//                    .name(eventName)
//                    .data(data)
//            );
//        } catch (IOException exception) {
//            // 실패할 시 local cache에 저장
//            log.info("=== 알림 보내기 실패 === ");
//
//            emitterLocalRepository.saveEventCache(emitterId, data);
//            emitterLocalRepository.deleteById(emitterId);
//        }
//
//    }
//
//    /**
//     * local repo에서 삭제
//     */
//    public void deleteCache(Long memberId) {
//        emitterLocalRepository.deleteAllEmitterStartWithId(memberId+"");
//    }
//
//
//    /**
//     * local repository key 생성
//     * memberNo_ProductNoOrBidNo_131313213
//     */
//    private String makeTimeIncludeId(Long productNoOrBidNo, Long memberNo) {
//        return memberNo + "_" + productNoOrBidNo + "_" + System.currentTimeMillis();
//    }
//
//
//    /**
//     * local cache 확인
//     */
//    private boolean hasLostData(String lastEventId) {
//        return !lastEventId.isEmpty();
//    }
//
//
//    /**
//     * local cache 뺑이치기
//     */
//    private void sendLostData(String lastEventId, Long memberId, String emitterId, String eventName, SseEmitter emitter) {
//        Map<String, Object> eventCaches = emitterLocalRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
//
//        eventCaches.entrySet().stream()
//                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
//                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, eventName, entry.getValue()));
//    }
//
//
//}
