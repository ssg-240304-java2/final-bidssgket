package com.ssg.bidssgket.user.domain.auction.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.ssg.bidssgket.user.domain.auction.entity.Auction;


/**
 * QAuction is a Querydsl query type for Auction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuction extends EntityPathBase<Auction> {

    private static final long serialVersionUID = -314569058L;

    public static final QAuction auction = new QAuction("auction");

    public final NumberPath<Integer> bidNo = createNumber("bidNo", Integer.class);

    public final BooleanPath bidSuccess = createBoolean("bidSuccess");

    public final NumberPath<Integer> maxTenderPrice = createNumber("maxTenderPrice", Integer.class);

    public final NumberPath<Integer> minTenderPrice = createNumber("minTenderPrice", Integer.class);

    public final DateTimePath<org.joda.time.DateTime> tenderDate = createDateTime("tenderDate", org.joda.time.DateTime.class);

    public final BooleanPath tenderDeleted = createBoolean("tenderDeleted");

    public QAuction(String variable) {
        super(Auction.class, forVariable(variable));
    }

    public QAuction(Path<? extends Auction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuction(PathMetadata metadata) {
        super(Auction.class, metadata);
    }

}

