package kr.code.main.common.department.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter @Getter
@Table(name="tbl_department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int deptCode;

    @Column(length = 50, nullable = false, unique = true)
    private String deptName;

    @Builder
    public Department(String deptName) {
        this.deptName = deptName;
    }
}
