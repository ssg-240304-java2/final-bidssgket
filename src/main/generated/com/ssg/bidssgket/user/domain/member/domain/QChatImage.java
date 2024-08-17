package com.ssg.bidssgket.user.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatImage is a Querydsl query type for ChatImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatImage extends EntityPathBase<ChatImage> {

    private static final long serialVersionUID = -1687026508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatImage chatImage1 = new QChatImage("chatImage1");

    public final QChatContent chatContent;

    public final StringPath chatImage = createString("chatImage");

    public final NumberPath<Long> chatImageNo = createNumber("chatImageNo", Long.class);

    public QChatImage(String variable) {
        this(ChatImage.class, forVariable(variable), INITS);
    }

    public QChatImage(Path<? extends ChatImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatImage(PathMetadata metadata, PathInits inits) {
        this(ChatImage.class, metadata, inits);
    }

    public QChatImage(Class<? extends ChatImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatContent = inits.isInitialized("chatContent") ? new QChatContent(forProperty("chatContent"), inits.get("chatContent")) : null;
    }

}

