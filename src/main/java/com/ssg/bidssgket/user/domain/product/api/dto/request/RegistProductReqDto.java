package com.ssg.bidssgket.user.domain.product.api.dto.request;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistProductReqDto extends FileDto{
    private String productName;
    private String category;
    private String productDesc;
    private String salesStatus;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private List<String> productImg;
    private Long memberNo;

    @Builder // Builder 패턴 추가
    public RegistProductReqDto(String productName, String category, String productDesc, String salesStatus,
                               Boolean imdPurchase, Boolean auctionSelected, Boolean eventAuction,
                               Integer buyNowPrice, Integer auctionStartPrice, LocalDateTime auctionStartTime,
                               LocalDateTime auctionEndTime, List<String> productImg, Long memberNo) {
        this.productName = productName;
        this.category = category;
        this.productDesc = productDesc;
        this.salesStatus = salesStatus;
        this.imdPurchase = imdPurchase;
        this.auctionSelected = auctionSelected;
        this.eventAuction = eventAuction;
        this.buyNowPrice = buyNowPrice;
        this.auctionStartPrice = auctionStartPrice;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.productImg = productImg;
        this.memberNo = memberNo;
    }

    public void setProductImg(List<String> productImg){
        this.productImg = productImg;
    }

    public void setMemberNo(Long memberNo) {
        this.memberNo = memberNo;
    }

    public void setAuctionStartTime(LocalDateTime auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public Integer getBuyNowPrice() {
        return buyNowPrice;
    }

    public void setBuyNowPrice(String buyNowPrice) {
        // 콤마를 제거하고 Integer로 변환
        this.buyNowPrice = Integer.parseInt(buyNowPrice.replace(",", ""));
    }

    public Integer getAuctionStartPrice() {
        return auctionStartPrice;
    }

    public void setAuctionStartPrice(String auctionStartPrice) {
        // 콤마를 제거하고 Integer로 변환
        this.auctionStartPrice = Integer.parseInt(auctionStartPrice.replace(",", ""));
    }

}
