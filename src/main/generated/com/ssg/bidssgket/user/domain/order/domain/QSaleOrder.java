package com.ssg.bidssgket.user.domain.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSaleOrder is a Querydsl query type for SaleOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSaleOrder extends EntityPathBase<SaleOrder> {

    private static final long serialVersionUID = 306468982L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSaleOrder saleOrder = new QSaleOrder("saleOrder");

    public final com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity _super = new com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType> deliveryType = createEnum("deliveryType", com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus> orderStatus = createEnum("orderStatus", com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus.class);

    public final com.ssg.bidssgket.user.domain.payment.domain.QPayment payment;

    public final com.ssg.bidssgket.user.domain.product.domain.QProduct product;

    public final NumberPath<Integer> saleOrderNo = createNumber("saleOrderNo", Integer.class);

    public final com.ssg.bidssgket.user.domain.member.domain.QMember seller;

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType> transactionType = createEnum("transactionType", com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSaleOrder(String variable) {
        this(SaleOrder.class, forVariable(variable), INITS);
    }

    public QSaleOrder(Path<? extends SaleOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSaleOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSaleOrder(PathMetadata metadata, PathInits inits) {
        this(SaleOrder.class, metadata, inits);
    }

    public QSaleOrder(Class<? extends SaleOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.payment = inits.isInitialized("payment") ? new com.ssg.bidssgket.user.domain.payment.domain.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.product = inits.isInitialized("product") ? new com.ssg.bidssgket.user.domain.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
        this.seller = inits.isInitialized("seller") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("seller"), inits.get("seller")) : null;
    }

}

