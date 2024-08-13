package com.ssg.bidssgket.user.domain.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductReport is a Querydsl query type for ProductReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductReport extends EntityPathBase<ProductReport> {

    private static final long serialVersionUID = -85163151L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductReport productReport = new QProductReport("productReport");

    public final EnumPath<Acceptance> acceptance = createEnum("acceptance", Acceptance.class);

    public final StringPath complainContent = createString("complainContent");

    public final DateTimePath<java.time.LocalDateTime> complainDate = createDateTime("complainDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> complainNo = createNumber("complainNo", Long.class);

    public final com.ssg.bidssgket.user.domain.member.domain.QMember member;

    public final QProduct product;

    public QProductReport(String variable) {
        this(ProductReport.class, forVariable(variable), INITS);
    }

    public QProductReport(Path<? extends ProductReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductReport(PathMetadata metadata, PathInits inits) {
        this(ProductReport.class, metadata, inits);
    }

    public QProductReport(Class<? extends ProductReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

