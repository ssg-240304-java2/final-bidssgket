package com.ssg.bidssgket.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseTimeAndDeleteEntity is a Querydsl query type for BaseTimeAndDeleteEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseTimeAndDeleteEntity extends EntityPathBase<BaseTimeAndDeleteEntity> {

    private static final long serialVersionUID = -936511026L;

    public static final QBaseTimeAndDeleteEntity baseTimeAndDeleteEntity = new QBaseTimeAndDeleteEntity("baseTimeAndDeleteEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QBaseTimeAndDeleteEntity(String variable) {
        super(BaseTimeAndDeleteEntity.class, forVariable(variable));
    }

    public QBaseTimeAndDeleteEntity(Path<? extends BaseTimeAndDeleteEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseTimeAndDeleteEntity(PathMetadata metadata) {
        super(BaseTimeAndDeleteEntity.class, metadata);
    }

}

