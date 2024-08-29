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
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, AuctionRepository auctionRepository, ProductRepository productRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
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
        log.info("회원 정보 확인 : {}", memberNo);

        List<Auction> auctionItems = auctionRepository.findByMemberNo(memberNo);

        /*상품 상태가 selling인 경우만 갖고 오기*/
        List<Auction> filteredAuctionItems = auctionItems.stream()
                .filter(auction -> {
                    Product product = auction.getProduct();
                    return product != null && product.getSalesStatus() == SalesStatus.selling;
                })
                .collect(Collectors.toList());

        filteredAuctionItems.forEach(auction -> log.info("Filtered Auction Item: {}", auction));

        return filteredAuctionItems;
    }

    // 해당 회원이 낙찰되었을 경우에만 (product테이블의 sales_status = 'trading'이며 auction 테이블에서 해당 회원이 bid_success = 'true'인 경우만
    public List<Auction> bidSuccessPurchaseTrading(Long memberNo) {
        List<Auction> auctionItems = auctionRepository.findByMember_memberNoAndBidSuccessIsTrueOrderByTenderDateDesc(memberNo);

        List<Auction> filteredAuctionTradingItems = auctionItems.stream()
                .filter(auction -> {
                    Product product = auction.getProduct();
                    return product != null && product.getSalesStatus() == SalesStatus.trading;
                })
                .collect(Collectors.toList());

        return filteredAuctionTradingItems;
    }

    public List<Product> getPurchaseTradingProducts(Long memberNo) {

        return purchaseOrderRepository.getPurchaseTradingProducts(memberNo);
    }

    public List<Product> getPurchaseCompletedProducts(Long memberNo) {

        return purchaseOrderRepository.getPurchaseCompletedProducts(memberNo);
    }

}
