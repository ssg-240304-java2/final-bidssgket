package com.ssg.bidssgket.user.domain.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParcel is a Querydsl query type for Parcel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParcel extends EntityPathBase<Parcel> {

    private static final long serialVersionUID = -184600262L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParcel parcel = new QParcel("parcel");

    public final com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity _super = new com.ssg.bidssgket.common.domain.QBaseTimeAndDeleteEntity(this);

    public final StringPath courierName = createString("courierName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryStatus> deliveryStatus = createEnum("deliveryStatus", com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryStatus.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Integer> parcelNo = createNumber("parcelNo", Integer.class);

    public final QPurchaseOrder purchaseOrder;

    public final QSaleOrder saleOrder;

    public final com.ssg.bidssgket.user.domain.member.domain.QMember seller;

    public final StringPath trackingNum = createString("trackingNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QParcel(String variable) {
        this(Parcel.class, forVariable(variable), INITS);
    }

    public QParcel(Path<? extends Parcel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParcel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParcel(PathMetadata metadata, PathInits inits) {
        this(Parcel.class, metadata, inits);
    }

    public QParcel(Class<? extends Parcel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.purchaseOrder = inits.isInitialized("purchaseOrder") ? new QPurchaseOrder(forProperty("purchaseOrder"), inits.get("purchaseOrder")) : null;
        this.saleOrder = inits.isInitialized("saleOrder") ? new QSaleOrder(forProperty("saleOrder"), inits.get("saleOrder")) : null;
        this.seller = inits.isInitialized("seller") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("seller"), inits.get("seller")) : null;
    }

}

