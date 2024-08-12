package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoomMember;
import com.ssg.bidssgket.user.domain.member.view.DTO.MemberDto;
import com.ssg.bidssgket.user.domain.order.domain.Parcel;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo; // 사용자 고유번호
    private String memberName; // 사용자 이름
    private String memberId; // 아이디
    private String pwd; // 비밀번호

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role; // 관리자인지 사용자인지

    @Column(name = "memberNickname", unique = true)
    private String memberNickname;

    @ColumnDefault("50")
    @Column(name = "biscuit")
    private Integer biscuit; // 비스킷 온도

    @ColumnDefault("false")
    @Column(name = "is_deleted")
    private boolean isDeleted; // 탈퇴 여부

    @ColumnDefault("false")
    @Column(name = "is_penalty")
    private boolean isPenalty; // 패널티 여부

    @Embedded
    private Address address; // 주소

//    private String provider;
//    private String providerId;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Auction> auctions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SaleOrder> saleOrders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Parcel> parcels = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Pay pay;

    @OneToOne(mappedBy = "reviewer", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Review reviewer;

    @OneToOne(mappedBy = "reviewee", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Review reviewee;



    @Builder
    private Member(String memberName, String memberId, String pwd, String memberNickname, String email, Role role, Integer biscuit, Address address, Boolean isDeleted, Boolean isPenalty) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.pwd = pwd;
        this.memberNickname = memberNickname;
        this.email = email;
        this.role = role;
//        this.provider = provider;
//        this.providerId = providerId;
        this.biscuit = biscuit == null? 50 :biscuit;
        this.address = address;
        this.isDeleted = isDeleted == null? false:isDeleted;
        this.isPenalty = isPenalty == null? false:isPenalty;
    }

//    public static Member createMember(MemberDto memberDto) {
//        Address address = Address.createAddress(memberDto.getAddress());
//        return Member.builder()
//                .memberName(memberDto.getMemberName())
//                .memberId(memberDto.getMemberId())
//                .pwd(memberDto.getPwd())
//                .memberNickname(memberDto.getMemberNickname())
//                .biscuit(memberDto.getBiscuit())
//                .address(address)
//                .isDeleted(memberDto.getIsDeleted())
//                .isPenalty(memberDto.getIsPenalty())
//                .build();
//    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member update(String memberNickname) {
        this.memberNickname = memberNickname;
        return this;
    }

    /**
     * 양방향 연관관계, cascade 유의
     */

    public void addWish(Wish wish) {
        if (wish.getMember() != null) {
            wish.getMember().getWishList().remove(wish);
        }
        wish.setMember(this);
        this.wishList.add(wish);
    }

    public void addProduct(Product product) {
        if (product.getMember() != null) {
            product.getMember().getProducts().remove(product);
        }
        product.setMember(this);
        this.products.add(product);
    }

    public void addAution(Auction auction) {
        if (auction.getMember() != null) {
            auction.getMember().getAuctions().remove(auction);
        }
        auction.setMember(this);
        this.auctions.add(auction);
    }

    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getMember() != null) {
            purchaseOrder.getMember().getPurchaseOrders().remove(purchaseOrder);
        }
        purchaseOrder.setMember(this);
        this.purchaseOrders.add(purchaseOrder);
    }

    public void addSaleOrder(SaleOrder saleOrder) {
        if (saleOrder.getMember() != null) {
            saleOrder.getMember().getSaleOrders().remove(saleOrder);
        }
        saleOrder.setMember(this);
        this.saleOrders.add(saleOrder);
    }

    public void addParcel(Parcel parcel) {
        if (parcel.getMember() != null) {
            parcel.getMember().getParcels().remove(parcel);
        }
        parcel.setMember(this);
        this.parcels.add(parcel);
    }

    public void addPayment(Payment payment) {
        if (payment.getMember() != null) {
            payment.getMember().getPayments().remove(payment);
        }
        payment.setMember(this);
        this.payments.add(payment);
    }

    public void addPay(Pay pay) {
        pay.setMember(this);
        this.pay = pay;
    }

    public void addReviewer(Review reviewer) {
        reviewer.setReviewer(this);
        this.reviewer = reviewer;
    }

    public void addReviewee(Review reviewee) {
        reviewee.setReviewee(this);
        this.reviewee = reviewee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("member"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


