package com.ssg.bidssgket.user.domain.auction.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.ssg.bidssgket.user.domain.auction.entity.Product;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProduct extends BeanPath<Product> {

    private static final long serialVersionUID = 37813578L;

    public static final QProduct product = new QProduct("product");

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> productNo = createNumber("productNo", Long.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

