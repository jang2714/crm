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

    private List<PositionVO> cachedList = null;

    public List<PositionVO> GetAllPosition() {
        if (cachedList != null) {
            if (cachedList.size() == repository.count()) {
                return cachedList;
            }
        }

        List<Position> positionList = repository.findAll();
        List<PositionVO> result = positionList.stream().map( pos -> {
            return new PositionVO(pos.getPosCode(), pos.getPosName());
        }).toList();
        if (result.size() > 0 && cachedList == null) {
            cachedList = result;
        }

        return result;
    }
}
