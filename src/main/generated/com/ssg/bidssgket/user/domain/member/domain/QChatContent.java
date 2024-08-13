package com.ssg.bidssgket.user.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatContent is a Querydsl query type for ChatContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatContent extends EntityPathBase<ChatContent> {

    private static final long serialVersionUID = 1304763794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatContent chatContent = new QChatContent("chatContent");

    public final NumberPath<Long> chatContentNo = createNumber("chatContentNo", Long.class);

    public final QChatImage chatImage;

    public final QChatRoom chatRoom;

    public final StringPath contents = createString("contents");

    public final QMember member;

    public final TimePath<java.sql.Time> time = createTime("time", java.sql.Time.class);

    public QChatContent(String variable) {
        this(ChatContent.class, forVariable(variable), INITS);
    }

    public QChatContent(Path<? extends ChatContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatContent(PathMetadata metadata, PathInits inits) {
        this(ChatContent.class, metadata, inits);
    }

    public QChatContent(Class<? extends ChatContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatImage = inits.isInitialized("chatImage") ? new QChatImage(forProperty("chatImage"), inits.get("chatImage")) : null;
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

