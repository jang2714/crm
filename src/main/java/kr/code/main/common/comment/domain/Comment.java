package kr.code.main.common.comment.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import kr.code.main.user.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name="tbl_comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String customerUid;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer", foreignKey = @ForeignKey(name = "FK_COMMENT_WRITER"))
    private UserEntity writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createDate;

    @Builder
    public Comment(String customerUid, UserEntity writer, String title, String comment) {
        this.customerUid = customerUid;
        this.writer = writer;
        this.title = title;
        this.comment = comment;
    }
}
