package kr.code.main.common.position.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter @Setter
@Table(name="tbl_position")
public class Position implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int posCode;

    @Column(length = 50, nullable = false, unique = true)
    private String posName;

    @Builder
    public Position(String posName) {
        this.posName = posName;
    }
}
