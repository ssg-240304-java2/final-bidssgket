package com.ssg.bidssgket.user.domain.payment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayChange is a Querydsl query type for PayChange
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayChange extends EntityPathBase<PayChange> {

    private static final long serialVersionUID = 1095281071L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayChange payChange = new QPayChange("payChange");

    public final NumberPath<Integer> balance = createNumber("balance", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QPay pay;

    public final NumberPath<Integer> payChangeAmount = createNumber("payChangeAmount", Integer.class);

    public final NumberPath<Integer> payChangeNo = createNumber("payChangeNo", Integer.class);

    public final EnumPath<com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType> payChangeType = createEnum("payChangeType", com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType.class);

    public QPayChange(String variable) {
        this(PayChange.class, forVariable(variable), INITS);
    }

    public QPayChange(Path<? extends PayChange> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayChange(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayChange(PathMetadata metadata, PathInits inits) {
        this(PayChange.class, metadata, inits);
    }

    public QPayChange(Class<? extends PayChange> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pay = inits.isInitialized("pay") ? new QPay(forProperty("pay"), inits.get("pay")) : null;
    }

}

