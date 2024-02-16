package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass   //상속받아도 빼면 안됨.
@EntityListeners(AuditingEntityListener.class)  //listener : 이벤트, main method에 어노테이션 추가해야함
public class BaseEntity extends CreatedAtEntity {
//    @CreatedDate
//    @Column(updatable = false, nullable = false)  //수정 불가능
////    @ColumnDefault("NOW()")   없어도 알아서 jpa가 해줌
//    private LocalDateTime createdAt; -> 상속으로 처리

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
