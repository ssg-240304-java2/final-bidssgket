package com.ssg.bidssgket.user.domain.order.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import com.ssg.bidssgket.user.domain.order.domain.Parcel;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.SaleOrderRepository;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductReportRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;
    private final AuctionRepository auctionRepository;
    private final ProductReportRepository productReportRepository;
    private final ProductRepository productRepository;


    public SaleOrderService(SaleOrderRepository saleOrderRepository, AuctionRepository auctionRepository, ProductReportRepository productReportRepository, ProductRepository productRepository) {
        this.saleOrderRepository = saleOrderRepository;
        this.auctionRepository = auctionRepository;
        this.productReportRepository = productReportRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public SaleOrder createAndSaveSaleOrder(OrderTransactionType orderTransactionType, DeliveryType deliveryType,
                                            OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel, DeliveryAddress deliveryAddress) {

        // SaleOrder 객체 생성
        SaleOrder saleOrder = SaleOrder.addSaleOrder(
                orderTransactionType,
                deliveryType,
                orderStatus,
                member,
                product,
                payment,
                parcel,
                deliveryAddress
        );

        // SaleOrder 객체 저장
        return saleOrderRepository.save(saleOrder);
    }

    public List<Product> getSaleAuctionProducts(Long memberNo) {

        // memberNo 값을 로그로 출력
        log.info("회원 정보 확인 : {}", memberNo);

        // 데이터베이스에서 경매중인 상품 목록 조회
//        List<Auction> auctionItems = auctionRepository.findAuctionSalesItemsByMember(memberNo);
        List<Product> auctionItems = productRepository.findAuctionSalesItemsByMember(memberNo);

        // 조회된 경매중인 상품 목록을 로그로 출력
        if (auctionItems == null || auctionItems.isEmpty()) {
            log.info("회원의 경매중인 상품 정보가 없습니다.: {}", memberNo);
        } else {
            log.info("회원의 경매중인 상품 개수 : {}, 회원 : {}", auctionItems.size(), memberNo);
            auctionItems.forEach(auction -> log.info("Auction Item: {}", auction));
        }

        return auctionItems;

    }

    public List<Product> getSaleTradingProducts(Long memberNo) {

        return  saleOrderRepository.getSaleTradingProducts(memberNo);
    }

    public List<Product> getSaleCompletedProducts(Long memberNo) {

        return  productRepository.getSaleCompletedProducts(memberNo);
    }
}
