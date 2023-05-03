package kr.code.main.common.tag.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
@Table(name = "tbl_tags")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "tag_title", nullable = false, length = 50)
    private String tagTitle;

    @Builder
    public Tag(String title) {
        this.tagTitle = title;
    }
}
