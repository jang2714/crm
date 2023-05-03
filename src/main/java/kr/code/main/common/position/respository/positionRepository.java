package kr.code.main.common.position.respository;

import kr.code.main.common.position.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface positionRepository extends JpaRepository<Position, Integer> {
}
