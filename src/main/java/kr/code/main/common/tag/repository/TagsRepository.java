package kr.code.main.common.tag.repository;

import kr.code.main.common.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findById(Long aLong);

    Optional<Tag>  findByTagTitle(String tagTitle);
}
