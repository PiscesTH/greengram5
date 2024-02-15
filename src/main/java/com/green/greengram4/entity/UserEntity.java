package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.common.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity //pk적용 필수
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"provider_type", "uid"}
        )
})   //복합 유니크 걸때 클래스 위에서 함
@Builder    //실무에서는 entity에 빌더 사용 권장 x
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id //pk설정
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 설정. AUTO -> DB마다 권장 방식 사용
    private Long iuser;

    @Column(name = "provider_type", nullable = false)
    @Enumerated(value = EnumType.STRING)    //enum String으로 사용할 때
    @ColumnDefault("'LOCAL'")   //기본값 설정
    //pk외의 컬럼 설정, 길이, 이름, null허용 설정 가능
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private RoleEnum role;

    @Column(length = 2100)
    private String firebaseToken;
}
