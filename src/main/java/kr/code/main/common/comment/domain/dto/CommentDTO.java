package kr.code.main.common.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDTO {

    private String customerUid;
    private String writerUid;
    private String writerName;
    private String commentTitle;
    private String contents;
}
