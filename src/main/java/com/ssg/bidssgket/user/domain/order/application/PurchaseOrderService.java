package com.ssg.bidssgket.user.domain.order.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.dto.response.ProductWithOrderDto;
import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import com.ssg.bidssgket.user.domain.order.domain.Parcel;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.PurchaseOrderRepository;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final AuctionRepository auctionRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, AuctionRepository auctionRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.auctionRepository = auctionRepository;
    }

    @Transactional
    public PurchaseOrder createAndSavePurchaseOrder(OrderTransactionType orderTransactionType, DeliveryType deliveryType,
                                                    OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel, DeliveryAddress deliveryAddress) {

        // PurchaseOrder 객체 생성
        PurchaseOrder purchaseOrder = PurchaseOrder.addPurchaseOrder(
                orderTransactionType,
                deliveryType,
                orderStatus,
                member,
                product,
                payment,
                parcel,
                deliveryAddress
        );

        // PurchaseOrder 객체 저장
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<Auction> getPurchaseAuctionProducts(Long memberNo) {
        // memberNo 값을 로그로 출력
        log.info("회원 정보 확인 : {}", memberNo);

        // 데이터베이스에서 경매중인 상품 목록 조회
        List<Auction> auctionItems = auctionRepository.findAuctionItemsByMember(memberNo);

        // 조회된 경매중인 상품 목록을 로그로 출력
        if (auctionItems == null || auctionItems.isEmpty()) {
            log.info("회원의 경매중인 상품 정보가 없습니다.: {}", memberNo);
        } else {
            log.info("회원의 경매중인 상품 개수 : {}, 회원 : {}", auctionItems.size(), memberNo);
            auctionItems.forEach(auction -> log.info("Auction Item: {}", auction));
        }

        return auctionItems;
    }

    public List<Product> getPurchaseTradingProducts(Long memberNo) {

        return purchaseOrderRepository.getPurchaseTradingProducts(memberNo);
    }

    public List<Product> getPurchaseCompletedProducts(Long memberNo) {

        return purchaseOrderRepository.getPurchaseCompletedProducts(memberNo);
    }

    // 멤버의 구매 거래 상품과 그 주문 정보를 조회
    public List<ProductWithOrderDto> getPurchaseTradingItemsWithOrder(Long memberNo) {
        List<Product> products = getPurchaseTradingProducts(memberNo); // 제품 조회
        List<ProductWithOrderDto> productWithOrders = new ArrayList<>();

        for (Product product : products) {
            PurchaseOrder purchaseOrder = product.getPurchaseOrder(); // 구매 주문 조회
            productWithOrders.add(new ProductWithOrderDto(product, purchaseOrder));
        }

        return productWithOrders;
    }
}
