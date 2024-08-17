package com.ssg.bidssgket.user.domain.member.api.chat.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomMember is a Querydsl query type for ChatRoomMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomMember extends EntityPathBase<ChatRoomMember> {

    private static final long serialVersionUID = -648464487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomMember chatRoomMember = new QChatRoomMember("chatRoomMember");

    public final QChatRoom chatRoom;

    public final NumberPath<Long> chatRoomMemberNo = createNumber("chatRoomMemberNo", Long.class);

    public final com.ssg.bidssgket.user.domain.member.domain.QMember member;

    public QChatRoomMember(String variable) {
        this(ChatRoomMember.class, forVariable(variable), INITS);
    }

    public QChatRoomMember(Path<? extends ChatRoomMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomMember(PathMetadata metadata, PathInits inits) {
        this(ChatRoomMember.class, metadata, inits);
    }

    public QChatRoomMember(Class<? extends ChatRoomMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new com.ssg.bidssgket.user.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

