package kr.code.main.common.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentVO {
    private String writer;
    private String title;
    private String comment;
    private Date createDate;
}