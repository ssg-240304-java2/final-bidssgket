package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.domain.Review;
import com.ssg.bidssgket.user.domain.member.domain.Wishlist;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import jakarta.persistence.*;
import lombok.*;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseTimeEntity 사용해야됨
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNo;
    private String productName;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String productDesc;
    @Enumerated(EnumType.STRING)
    private Sales_status salesStatus;
    private Integer buynowPrice;
    private Integer auctionStartPrice;
    private Integer bidSuccessPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<ProductImage>();

    public void addProductImage(ProductImage productImage){
        if(productImage.getProduct() != null){
           productImage.getProduct().getProductImages().remove(productImage);
        }

        productImage.setProduct(this);
        this.productImages.add(productImage);
    }

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductReport> productReports = new ArrayList<>();

    public void addProductReport(ProductReport productReport){
        if(productReport.getProduct() != null){
            productReport.getProduct().getProductImages().remove(productReport);
        }

        productReport.setProductNo(this);
        this.productReports.add(productReport);
    }


    @OneToMany(mappedBy = "bidNo",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Auction> aucitons = new ArrayList<>();

    /**
     * 양방향 연관관계, cascade 유의
     */
//    public void addAuction(Auction auction){
//        if(auction.getProduct() != null){
//            auction.getProduct().getAucitons().remove(auction);
//        }
//
//        auction.setProduct(this);
//        this.aucitons.add(auction);
//    }

    @OneToOne
    @JoinColumn(name = "review_no")
    private Review review;

//    public void addReview(Review review){
//        review.setReview_no(this);
//        this.review = review;
//    }

    @OneToOne
    @JoinColumn(name = "wishlist_no")
    private Wishlist wishlist;

//    public void addWishlist(Wishlist wishlist){
//        review.setReview_no(this);
//        this.wishlist = wishlist;
//    }

    @OneToOne
    @JoinColumn(name = "purchaseOrderNo")
    private PurchaseOrder purchaseOrder;

//    public void addPurchaseOrder(PurchaseOrder purchaseOrder){
//        purchaseOrder.setPurchaseOrderNo(this);
//        this.purchaseOrder = purchaseOrder;
//    }

    @OneToOne
    @JoinColumn(name = "saleOrderNo")
    private SaleOrder saleOrder;

//    public void addSaleOrder(SaleOrder saleOrder){
//        saleOrder.setSaleOrderNO(this);
//        this.saleOrder = saleOrder;
//    }

    @Builder
    private Product(String productName,ProductImage productImage, Category category, String productDesc, Integer buynowPrice, Integer auctionStartPrice, LocalDateTime auctionStartTime, LocalDateTime auctionEndTime, Member member) {
        this.productName = productName;
        this.productImages = (List<ProductImage>) productImage;
        this.category = category;
        this.productDesc = productDesc;
        this.buynowPrice = buynowPrice;
        this.auctionStartPrice = auctionStartPrice;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.member = member;
    }

    public static Product addProductBoth(String productName,ProductImage productImage, Category category, String productDesc, Integer buynowPrice, Integer auctionStartPrice, LocalDateTime auctionStartTime, LocalDateTime auctionEndTime, Member member){
        return Product.builder()
                .productName(productName)
                .productImage(productImage)
                .category(category)
                .productDesc(productDesc)
                .buynowPrice(buynowPrice)
                .auctionStartPrice(auctionStartPrice)
                .auctionStartTime(auctionStartTime)
                .auctionEndTime(auctionEndTime)
                .member(member)
                .build();
    }

}
