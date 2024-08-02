package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class ChatImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chat_image_no;

    @Column
    private String chat_image;
}
