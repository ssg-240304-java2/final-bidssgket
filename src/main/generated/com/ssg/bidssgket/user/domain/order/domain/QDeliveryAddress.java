package com.ssg.bidssgket.user.domain.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryAddress is a Querydsl query type for DeliveryAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryAddress extends EntityPathBase<DeliveryAddress> {

    private static final long serialVersionUID = 1222642511L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryAddress deliveryAddress1 = new QDeliveryAddress("deliveryAddress1");

    public final com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity _super = new com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity(this);

    public final StringPath contactNumber = createString("contactNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deliveryAddress = createString("deliveryAddress");

    public final NumberPath<Long> deliveryAddressNo = createNumber("deliveryAddressNo", Long.class);

    public final StringPath deliveryRequest = createString("deliveryRequest");

    public final StringPath detailAddress = createString("detailAddress");

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final com.ssg.bidssgket.user.domain.member.domain.QMember member;

    public final StringPath postcode = createString("postcode");

    public final com.ssg.bidssgket.user.domain.product.domain.QProduct product;

    public final StringPath receiverName = createString("receiverName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDeliveryAddress(String variable) {
        this(DeliveryAddress.class, forVariable(variable), INITS);
    }

    public QDeliveryAddress(Path<? extends DeliveryAddress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryAddress(PathMetadata metadata, PathInits inits) {
        this(DeliveryAddress.class, metadata, inits);
    }

    public QDeliveryAddress(Class<? extends DeliveryAddress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new com.ssg.bidssgket.user.domain.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

