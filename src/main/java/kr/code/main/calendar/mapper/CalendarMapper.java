package kr.code.main.calendar.mapper;

import kr.code.main.calendar.vo.CalendarVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {

    /* 일정관리 조회 화면 */
    List<CalendarVO> selectCalendarList(CalendarVO vo) throws Exception;



}
