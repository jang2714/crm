package kr.code.main.calendar.mapper;

import kr.code.main.calendar.vo.CalendarVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {

    /* 일정관리 조회 화면 */
    List<CalendarVO> selectCalendarList(CalendarVO vo) throws Exception;

    /* 일정관리 등록 처리 (tbl_meet) */
    int calendarMeetInsert(CalendarVO vo) throws Exception;

    /* 일정관리 등록 처리 (tbl_meet_join) */
    void calendarMeetJoinInsert(CalendarVO vo) throws Exception;

    /* 직원 조회 */
    List<CalendarVO> selectUserList(CalendarVO vo) throws Exception;
    
    /* 고객 조회 */
    List<CalendarVO> selectCustList(CalendarVO vo) throws Exception;

    /* 일정관리_ 직원 등록 조회 (상세, 수정 위한) */
    List<CalendarVO> selectMeetUserList(CalendarVO vo) throws Exception;

    /* 일정관리_ 고객 등록 조회 (상세, 수정 위한) */
    List<CalendarVO> selectMeetCustList(CalendarVO vo) throws Exception;

    /* 일정관리_ meet 등록 조회 (상세, 수정 위한) */
    List<CalendarVO> selectMeetList(CalendarVO vo) throws Exception;
}
