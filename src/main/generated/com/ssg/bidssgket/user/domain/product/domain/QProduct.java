package com.ssg.bidssgket.user.domain.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1055546333L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final DateTimePath<java.time.LocalDateTime> auctionEndTime = createDateTime("auctionEndTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> auctionStartPrice = createNumber("auctionStartPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> auctionStartTime = createDateTime("auctionStartTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> bidSuccessPrice = createNumber("bidSuccessPrice", Integer.class);

    public final NumberPath<Integer> buynowPrice = createNumber("buynowPrice", Integer.class);

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final com.ssg.bidssgket.user.domain.member.domain.QMember member;

    public final StringPath productDesc = createString("productDesc");

    public final ListPath<ProductImage, QProductImage> productImages = this.<ProductImage, QProductImage>createList("productImages", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> productNo = createNumber("productNo", Long.class);

    public final EnumPath<Sales_status> salesStatus = createEnum("salesStatus", Sales_status.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

