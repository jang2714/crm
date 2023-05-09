package kr.code.main.calendar.service;

import kr.code.main.calendar.mapper.CalendarMapper;
import kr.code.main.calendar.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarMapper mapper;

    /* 일정관리 조회 화면 */
    public List<CalendarVO> selectCalendarList(CalendarVO vo) throws Exception {
        return mapper.selectCalendarList(vo);
    }

    /* 일정관리 등록 처리 */
    @Transactional
    public void calendarRegistProc(CalendarVO vo) throws Exception {
        String meetDt;
        if(StringUtils.isNotBlank(vo.getMeetDate())) {
            meetDt = vo.getMeetDate().replace("-","") + vo.getMeetHhMi(); //202305010900
            vo.setMeetDate(meetDt);
        }
        int meetCnt = mapper.calendarMeetInsert(vo);
        if(meetCnt > 0) {
            System.out.println("vo.getMeetId(): " + vo.getMeetId());

            //직원 등록 시작
            String[] userSeqs = vo.getUserSeqs();
            if(ArrayUtils.isNotEmpty(userSeqs)) {
                vo.setMeetUserSe("SE01");
                for(int i=0; i<userSeqs.length; i++) {
                    vo.setMeetUserId(userSeqs[i]);
                    mapper.calendarMeetJoinInsert(vo);
                }
            }
            //직원 등록 끝

            //고객 등록 시작
            String[] custSeqs = vo.getCustSeqs();
            if(ArrayUtils.isNotEmpty(custSeqs)) {
                vo.setMeetUserSe("SE02");
                for(int i=0; i<custSeqs.length; i++) {
                    vo.setMeetUserId(custSeqs[i]);
                    mapper.calendarMeetJoinInsert(vo);
                }
            }
            //고객 등록 끝
        }
    }

    /* 직원 조회 */
    public List<CalendarVO> selectUserList(CalendarVO vo) throws Exception {
        return mapper.selectUserList(vo);
    }
    
    /* 고객 조회 */
    public List<CalendarVO> selectCustList(CalendarVO vo) throws Exception {
        return mapper.selectCustList(vo);
    }

    /* 일정관리_ 직원 등록 조회 (상세, 수정 위한) */
    public List<CalendarVO> selectMeetUserList(CalendarVO vo) throws Exception {
        return mapper.selectMeetUserList(vo);
    }

    /* 일정관리_ 고객 등록 조회 (상세, 수정 위한) */
    public List<CalendarVO> selectMeetCustList(CalendarVO vo) throws Exception {
        return mapper.selectMeetCustList(vo);
    }

    /* 일정관리_ meet 등록 조회 (상세, 수정 위한) */
    public CalendarVO selectMeetDetailView(CalendarVO vo) throws Exception {
        return mapper.selectMeetDetailView(vo);
    }

    /* 일정관리_ 상세삭제 처리 (meet, meet_join 합침)*/
    @Transactional
    public void calendarMeetDeleteProc(CalendarVO vo) throws Exception {
        vo.setUseYn("N");
        mapper.calendarMeetJoinDelete(vo);
        mapper.calendarMeetDelete(vo);
    }

    

}
