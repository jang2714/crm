package kr.code.main.calendar.service;

import kr.code.main.calendar.mapper.CalendarMapper;
import kr.code.main.calendar.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarMapper mapper;

    /* 일정관리 조회 화면 */
    public List<CalendarVO> selectCalendarList(CalendarVO vo) throws Exception {
        return mapper.selectCalendarList(vo);
    }

}
