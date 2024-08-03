package com.ssg.bidssgket.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeAndDeleteEntity {

    @CreatedDate
    @Column(updatable=false, nullable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable=false)
    private LocalDateTime updatedAt;

    @Column(nullable=false)
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        this.isDeleted = false; // 기본값 설정
    }
}
