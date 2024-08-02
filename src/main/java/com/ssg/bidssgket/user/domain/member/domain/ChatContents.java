package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Time;
import java.util.Date;

@Entity
@Getter
public class ChatContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chat_contents_no;
    private String Contents;
    private Time time;

    
}
