package kr.code.main.common.comment.repository;

import kr.code.main.common.comment.domain.Comment;
import kr.code.main.common.comment.domain.CommentVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentVO> findAllByCustomerUidOrderByCreateDateDesc(String customerUid);
}