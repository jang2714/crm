package kr.code.main.common.position.service;

import kr.code.main.common.position.domain.Position;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.common.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository repository;

    public List<PositionVO> GetAllPosition() {
        List<Position> positionList = repository.findAll();

        return positionList.stream().map( pos -> {
            return new PositionVO(pos.getPosCode(), pos.getPosName());
        }).toList();
    }
}
