package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable
//@EqualsAndHashCode    임베디드 쓸 때 필수로 있어야 함.
public class DmMsgIds implements Serializable {
    private Long idm;

    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(
//            name = "sequence_generator",
//            sequenceName = "my_seq",
//            allocationSize = 1
//    )
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long seq;
}
