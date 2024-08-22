package com.ssg.bidssgket.user.domain.payment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -1043420419L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity _super = new com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final com.ssg.bidssgket.user.domain.member.domain.QMember member;

    public final QPayChange payChange;

    public final NumberPath<Long> paymentNo = createNumber("paymentNo", Long.class);

    public final EnumPath<com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus> paymentStatus = createEnum("paymentStatus", com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus.class);

    public final EnumPath<com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentTransactionType> paymentTransactionType = createEnum("paymentTransactionType", com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentTransactionType.class);

    public final EnumPath<com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType> paymentType = createEnum("paymentType", com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType.class);

    public final com.ssg.bidssgket.user.domain.order.domain.QPurchaseOrder purchaseOrder;

    public final com.ssg.bidssgket.user.domain.order.domain.QSaleOrder saleOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.payChange = inits.isInitialized("payChange") ? new QPayChange(forProperty("payChange"), inits.get("payChange")) : null;
        this.purchaseOrder = inits.isInitialized("purchaseOrder") ? new com.ssg.bidssgket.user.domain.order.domain.QPurchaseOrder(forProperty("purchaseOrder"), inits.get("purchaseOrder")) : null;
        this.saleOrder = inits.isInitialized("saleOrder") ? new com.ssg.bidssgket.user.domain.order.domain.QSaleOrder(forProperty("saleOrder"), inits.get("saleOrder")) : null;
    }

}

