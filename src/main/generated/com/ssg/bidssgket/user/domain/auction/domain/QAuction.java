package com.ssg.bidssgket.user.domain.auction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuction is a Querydsl query type for Auction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuction extends EntityPathBase<Auction> {

    private static final long serialVersionUID = -1264383651L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuction auction = new QAuction("auction");

    public final NumberPath<Long> bidNo = createNumber("bidNo", Long.class);

    public final BooleanPath bidSuccess = createBoolean("bidSuccess");

    public final NumberPath<Integer> maxTenderPrice = createNumber("maxTenderPrice", Integer.class);

    public final com.ssg.bidssgket.user.domain.member.domain.QMember member;

    public final NumberPath<Integer> minTenderPrice = createNumber("minTenderPrice", Integer.class);

    public final com.ssg.bidssgket.user.domain.product.domain.QProduct product;

    public final DateTimePath<java.time.LocalDateTime> tenderDate = createDateTime("tenderDate", java.time.LocalDateTime.class);

    public final BooleanPath tenderDeleted = createBoolean("tenderDeleted");

    public QAuction(String variable) {
        this(Auction.class, forVariable(variable), INITS);
    }

    public QAuction(Path<? extends Auction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuction(PathMetadata metadata, PathInits inits) {
        this(Auction.class, metadata, inits);
    }

    public QAuction(Class<? extends Auction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new com.ssg.bidssgket.user.domain.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

