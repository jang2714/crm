package kr.code.main.common.comment.repository;

import kr.code.main.common.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCustomerUidOrderByCreateDateDesc(String customerUid);
}