package com.ssg.bidssgket.user.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatContents is a Querydsl query type for ChatContents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatContents extends EntityPathBase<ChatContents> {

    private static final long serialVersionUID = 1792972065L;

    public static final QChatContents chatContents = new QChatContents("chatContents");

    public final NumberPath<Integer> chat_contents_no = createNumber("chat_contents_no", Integer.class);

    public final StringPath Contents = createString("Contents");

    public final TimePath<java.sql.Time> time = createTime("time", java.sql.Time.class);

    public QChatContents(String variable) {
        super(ChatContents.class, forVariable(variable));
    }

    public QChatContents(Path<? extends ChatContents> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatContents(PathMetadata metadata) {
        super(ChatContents.class, metadata);
    }

}

