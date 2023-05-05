package kr.code.main.calendar.service;

import kr.code.main.calendar.mapper.CommonMapper;
import kr.code.main.calendar.vo.CommonVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonMapper mapper;

    /* 공통코드 조회 */
    public List<CommonVO> selectCommonCodeList(String str) throws Exception {
        return mapper.selectCommonCodeList(str);
    }
}
