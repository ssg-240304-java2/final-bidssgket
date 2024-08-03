package com.ssg.bidssgket.user.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatImage is a Querydsl query type for ChatImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatImage extends EntityPathBase<ChatImage> {

    private static final long serialVersionUID = -1687026508L;

    public static final QChatImage chatImage = new QChatImage("chatImage");

    public final StringPath chat_image = createString("chat_image");

    public final NumberPath<Integer> chat_image_no = createNumber("chat_image_no", Integer.class);

    public QChatImage(String variable) {
        super(ChatImage.class, forVariable(variable));
    }

    public QChatImage(Path<? extends ChatImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatImage(PathMetadata metadata) {
        super(ChatImage.class, metadata);
    }

}

