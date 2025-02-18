package kr.code.main.user.domain.repository;

import kr.code.main.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserUid(String userUid);

    boolean existsByUserUid(String userUid);

    UserEntity findByUserId(String userId);

}
