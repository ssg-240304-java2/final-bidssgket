package com.ssg.bidssgket.user.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 938769513L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QAddress address;

    public final ListPath<com.ssg.bidssgket.user.domain.auction.domain.Auction, com.ssg.bidssgket.user.domain.auction.domain.QAuction> auctions = this.<com.ssg.bidssgket.user.domain.auction.domain.Auction, com.ssg.bidssgket.user.domain.auction.domain.QAuction>createList("auctions", com.ssg.bidssgket.user.domain.auction.domain.Auction.class, com.ssg.bidssgket.user.domain.auction.domain.QAuction.class, PathInits.DIRECT2);

    public final NumberPath<Integer> biscuit = createNumber("biscuit", Integer.class);

    public final StringPath email = createString("email");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isPenalty = createBoolean("isPenalty");

    public final StringPath memberId = createString("memberId");

    public final StringPath memberName = createString("memberName");

    public final StringPath memberNickname = createString("memberNickname");

    public final NumberPath<Long> memberNo = createNumber("memberNo", Long.class);

    public final ListPath<com.ssg.bidssgket.user.domain.order.domain.Parcel, com.ssg.bidssgket.user.domain.order.domain.QParcel> parcels = this.<com.ssg.bidssgket.user.domain.order.domain.Parcel, com.ssg.bidssgket.user.domain.order.domain.QParcel>createList("parcels", com.ssg.bidssgket.user.domain.order.domain.Parcel.class, com.ssg.bidssgket.user.domain.order.domain.QParcel.class, PathInits.DIRECT2);

    public final com.ssg.bidssgket.user.domain.payment.domain.QPay pay;

    public final ListPath<com.ssg.bidssgket.user.domain.payment.domain.Payment, com.ssg.bidssgket.user.domain.payment.domain.QPayment> payments = this.<com.ssg.bidssgket.user.domain.payment.domain.Payment, com.ssg.bidssgket.user.domain.payment.domain.QPayment>createList("payments", com.ssg.bidssgket.user.domain.payment.domain.Payment.class, com.ssg.bidssgket.user.domain.payment.domain.QPayment.class, PathInits.DIRECT2);

    public final ListPath<com.ssg.bidssgket.user.domain.product.domain.Product, com.ssg.bidssgket.user.domain.product.domain.QProduct> products = this.<com.ssg.bidssgket.user.domain.product.domain.Product, com.ssg.bidssgket.user.domain.product.domain.QProduct>createList("products", com.ssg.bidssgket.user.domain.product.domain.Product.class, com.ssg.bidssgket.user.domain.product.domain.QProduct.class, PathInits.DIRECT2);

    public final ListPath<com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder, com.ssg.bidssgket.user.domain.order.domain.QPurchaseOrder> purchaseOrders = this.<com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder, com.ssg.bidssgket.user.domain.order.domain.QPurchaseOrder>createList("purchaseOrders", com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder.class, com.ssg.bidssgket.user.domain.order.domain.QPurchaseOrder.class, PathInits.DIRECT2);

    public final StringPath pwd = createString("pwd");

    public final QReview reviewee;

    public final QReview reviewer;

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<com.ssg.bidssgket.user.domain.order.domain.SaleOrder, com.ssg.bidssgket.user.domain.order.domain.QSaleOrder> saleOrders = this.<com.ssg.bidssgket.user.domain.order.domain.SaleOrder, com.ssg.bidssgket.user.domain.order.domain.QSaleOrder>createList("saleOrders", com.ssg.bidssgket.user.domain.order.domain.SaleOrder.class, com.ssg.bidssgket.user.domain.order.domain.QSaleOrder.class, PathInits.DIRECT2);

    public final ListPath<Wish, QWish> wishList = this.<Wish, QWish>createList("wishList", Wish.class, QWish.class, PathInits.DIRECT2);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.pay = inits.isInitialized("pay") ? new com.ssg.bidssgket.user.domain.payment.domain.QPay(forProperty("pay"), inits.get("pay")) : null;
        this.reviewee = inits.isInitialized("reviewee") ? new QReview(forProperty("reviewee"), inits.get("reviewee")) : null;
        this.reviewer = inits.isInitialized("reviewer") ? new QReview(forProperty("reviewer"), inits.get("reviewer")) : null;
    }

}

