package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.common.domain.BaseTimeEntity;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import jakarta.persistence.*;
import lombok.*;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNo;
    private String productName;
    private String productDesc;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private Integer bidSuccessPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<ProductImage>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductReport> productReports = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Auction> auctions = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private DeliveryAddress deliveryAddress; // 배송지 정보 [FK]

    @OneToOne(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private SaleOrder saleOrder;

    @OneToOne(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private PurchaseOrder purchaseOrder;

    @Builder
    private Product(String productName, Category category, String productDesc, SalesStatus salesStatus, Boolean imdPurchase, Boolean auctionSelected, Boolean eventAuction, Integer buyNowPrice, Integer bidSuccessPrice , Integer auctionStartPrice, LocalDateTime auctionStartTime, LocalDateTime auctionEndTime, Member member) {
        this.productName = productName;
        this.category = category;
        this.salesStatus = salesStatus;
        this.productDesc = productDesc;
        this.imdPurchase = imdPurchase;
        this.auctionSelected = auctionSelected;
        this.eventAuction = eventAuction;
        this.buyNowPrice = buyNowPrice;
        this.auctionStartPrice = auctionStartPrice;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.bidSuccessPrice = bidSuccessPrice;
        this.member = member;
    }

    public static Product registProductBoth(RegistProductReqDto productReqDto, Member member){
        return Product.builder()
                .productName(productReqDto.getProductName())
                .category(Category.valueOf(productReqDto.getCategory()))
                .salesStatus(SalesStatus.valueOf(productReqDto.getSalesStatus()))
                .productDesc(productReqDto.getProductDesc())
                .imdPurchase(productReqDto.getImdPurchase())
                .auctionSelected(productReqDto.getAuctionSelected())
                .eventAuction(productReqDto.getEventAuction())
                .buyNowPrice(productReqDto.getBuyNowPrice())
                .auctionStartPrice(productReqDto.getAuctionStartPrice())
                .auctionStartTime(productReqDto.getAuctionStartTime())
                .auctionEndTime(productReqDto.getAuctionEndTime())
                .member(member)
                .build();
    }

    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addAuction(Auction auction){
        if(auction.getProduct() != null){
            auction.getProduct().getAuctions().remove(auction);
        }

        auction.setProduct(this);
        this.auctions.add(auction);
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void addProductImage(ProductImage productImage){
        if(productImage.getProduct() != null){
            productImage.getProduct().getProductImages().remove(productImage);
        }

        productImage.setProduct(this);
        this.productImages.add(productImage);
    }

    public void addProductReport(ProductReport productReport){
        if(productReport.getProduct() != null){
            productReport.getProduct().getProductImages().remove(productReport);
        }

        productReport.setProduct(this);
        this.productReports.add(productReport);
    }

    public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
        deliveryAddress.setProduct(this);
        this.deliveryAddress = deliveryAddress;
    }

    public void setProductNo(Long productNo) {
        this.productNo = productNo;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setImdPurchase(Boolean imdPurchase) {
        this.imdPurchase = imdPurchase;
    }

    public void setAuctionSelected(Boolean auctionSelected) {
        this.auctionSelected = auctionSelected;
    }

    public void setEventAuction(Boolean eventAuction) {
        this.eventAuction = eventAuction;
    }

    public void setBuyNowPrice(Integer buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public void setAuctionStartPrice(Integer auctionStartPrice) {
        this.auctionStartPrice = auctionStartPrice;
    }

    public void setBidSuccessPrice(Integer bidSuccessPrice) {
        this.bidSuccessPrice = bidSuccessPrice;
    }

    public void setAuctionStartTime(LocalDateTime auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSalesStatus(SalesStatus salesStatus) {
        this.salesStatus = salesStatus;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public void setProductReports(List<ProductReport> productReports) {
        this.productReports = productReports;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getSaleStatus(SalesStatus salesStatus) {
        return salesStatus.name();
    }
}
