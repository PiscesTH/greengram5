package com.green.greengram4.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class DmUserIds implements Serializable {
    private Long idm;
    private Long iuser;
}
