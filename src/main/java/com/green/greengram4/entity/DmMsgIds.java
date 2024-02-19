package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable
//@EqualsAndHashCode    임베디드 쓸 때 필수로 있어야 함.
public class DmMsgIds implements Serializable {
    private Long idm;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long seq;
}
