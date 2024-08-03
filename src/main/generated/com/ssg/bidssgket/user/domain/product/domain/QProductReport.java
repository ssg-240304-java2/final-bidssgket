package com.ssg.bidssgket.user.domain.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductReport is a Querydsl query type for ProductReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductReport extends EntityPathBase<ProductReport> {

    private static final long serialVersionUID = -85163151L;

    public static final QProductReport productReport = new QProductReport("productReport");

    public final NumberPath<Integer> complainContent = createNumber("complainContent", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> complainDate = createDateTime("complainDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> complainNo = createNumber("complainNo", Long.class);

    public QProductReport(String variable) {
        super(ProductReport.class, forVariable(variable));
    }

    public QProductReport(Path<? extends ProductReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductReport(PathMetadata metadata) {
        super(ProductReport.class, metadata);
    }

}

