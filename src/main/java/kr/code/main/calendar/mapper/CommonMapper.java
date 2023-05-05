package kr.code.main.calendar.mapper;

import kr.code.main.calendar.vo.CalendarVO;
import kr.code.main.calendar.vo.CommonVO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface CommonMapper {

    /* 일정관리 조회 화면 */
    List<CommonVO> selectCommonCodeList(String str) throws Exception;

}
