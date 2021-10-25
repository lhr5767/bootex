package com.minp.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //해당 클래스는 테이블로 생성 x
@EntityListeners(value = {AuditingEntityListener.class}) //JPA 내부에서 엔티티객체 변동 감지
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "createdDate",updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;
}
