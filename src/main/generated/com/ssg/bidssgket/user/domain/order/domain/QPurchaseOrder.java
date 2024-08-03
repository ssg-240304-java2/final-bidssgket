package com.ssg.bidssgket.user.domain.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseOrder is a Querydsl query type for PurchaseOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseOrder extends EntityPathBase<PurchaseOrder> {

    private static final long serialVersionUID = 1048103036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseOrder purchaseOrder = new QPurchaseOrder("purchaseOrder");

    public final com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity _super = new com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity(this);

    public final com.ssg.bidssgket.user.domain.member.domain.QMember buyer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType> deliveryType = createEnum("deliveryType", com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus> orderStatus = createEnum("orderStatus", com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus.class);

    public final com.ssg.bidssgket.user.domain.payment.domain.QPayment payment;

    public final com.ssg.bidssgket.user.domain.product.domain.QProduct product;

    public final NumberPath<Integer> purchaseOrderNo = createNumber("purchaseOrderNo", Integer.class);

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType> transactionType = createEnum("transactionType", com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPurchaseOrder(String variable) {
        this(PurchaseOrder.class, forVariable(variable), INITS);
    }

    public QPurchaseOrder(Path<? extends PurchaseOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseOrder(PathMetadata metadata, PathInits inits) {
        this(PurchaseOrder.class, metadata, inits);
    }

    public QPurchaseOrder(Class<? extends PurchaseOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("buyer"), inits.get("buyer")) : null;
        this.payment = inits.isInitialized("payment") ? new com.ssg.bidssgket.user.domain.payment.domain.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.product = inits.isInitialized("product") ? new com.ssg.bidssgket.user.domain.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

