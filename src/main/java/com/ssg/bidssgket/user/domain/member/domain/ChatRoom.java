package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatroom_no;

    //수신자 닉네임, 발신자닉네임

}
