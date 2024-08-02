package com.ssg.bidssgket.user.domain.product.domain;

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
@NoArgsConstructor
@Getter
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
    private int buynowPrice;
    private int auctionStartPrice;
    private int bidSuccessPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;

    @ManyToOne
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

    @OneToMany(mappedBy = "bidNo")
    private List<Auction> aucitons = new ArrayList<>();

    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addAuction(Auction auction){
        if(auction.getProduct() != null){
            auction.getProduct().getAuctions().remove(auction);
        }

        auction.setProduct(this);
        this.aucitons.add(auction);
    }

    @OneToOne
    @JoinColumn(name = "reviewNo")
    private Review review;

    @OneToOne
    @JoinColumn(name = "wishListNo")
    private WishList wishList;

    @OneToOne
    @JoinColumn(name = "purchaseOrderNo")
    private PurchaseOrder purchaseOrder;

    @OneToOne
    @JoinColumn(name = "saleOrderNo")
    private SaleOrder saleOrder;

}
