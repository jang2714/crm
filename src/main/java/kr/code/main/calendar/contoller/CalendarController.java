package kr.code.main.calendar.contoller;

import kr.code.main.calendar.service.CalendarService;
import kr.code.main.calendar.service.CommonService;
import kr.code.main.calendar.vo.CalendarVO;
import kr.code.main.calendar.vo.CommonVO;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
*
 * calendar
 * @author grace
 *
*/
@RestController //ResponseBody를 생략하기 위해
//@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    //Logger log = Logger.getLogger(this.getClass());

    private final CalendarService calenderService;
    private final CommonService commonService;

    //일정관리 화면
    @RequestMapping("/calendar/calendarView.do")
    public ModelAndView calendarView(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        //log.debug("calendarView" + paramVO == null ? "NULL" : paramVO.toString());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/calendar/calendarView");
        mav.addObject("paramVO", paramVO);
        return mav;
    }

    //일정관리 화면 처리
    @PostMapping("/calendar/calendarProc.do")
    public Map<String, Object> calendarProc(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        List<CalendarVO> resultList = calenderService.selectCalendarList(paramVO);
        Map<String, Object> map = new HashMap<>();
        map.put("resultList", JSONArray.fromObject(resultList));
        return map;
    }

    //일정관리 등록 화면
    @PostMapping("/calendar/calendarRegistView.do")
    public ModelAndView calendarRegistView(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        List<CommonVO> CRM01List = commonService.selectCommonCodeList("CRM01");
        List<CommonVO> CRM04List = commonService.selectCommonCodeList("CRM04");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/calendar/calendarRegistView");
        mav.addObject("paramVO", paramVO);
        mav.addObject("CRM01List", CRM01List);
        mav.addObject("CRM04List", CRM04List);
        return mav;
    }

    //일정관리 등록 처리
    @PostMapping("/calendar/calendarRegistProc.do")
    public Map<String, Object> calendarRegistProc(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        calenderService.calendarRegistProc(paramVO);
        Map<String, Object> map = new HashMap<>();
        map.put("paramVO", paramVO);
        return map;
    }

    //직원 조회
    @PostMapping(value = "/calendar/selectUserList.do")
    public Map<String, Object>  selectUserList(CalendarVO paramVO, HttpServletRequest request) throws Exception{
        List<CalendarVO> resultList = calenderService.selectUserList(paramVO);
        Map<String, Object> map = new HashMap<>();
        map.put("resultList", JSONArray.fromObject(resultList));
        return map;
    }
    //고객 조회
    @PostMapping(value = "/calendar/selectCustList.do")
    public Map<String, Object> selectCustList(CalendarVO paramVO, HttpServletRequest request) throws Exception{
        List<CalendarVO> resultList = calenderService.selectCustList(paramVO);
        Map<String, Object> map = new HashMap<>();
        map.put("resultList", JSONArray.fromObject(resultList));
        return map;
    }
    
    //일정관리 상세 화면
    @PostMapping("/calendar/calendarDetailView.do")
    public ModelAndView calendarDetailView(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        List<CommonVO> CRM01List = commonService.selectCommonCodeList("CRM01");
        List<CommonVO> CRM04List = commonService.selectCommonCodeList("CRM04");

        List<CalendarVO> userList = calenderService.selectMeetUserList(paramVO);
        List<CalendarVO> custList = calenderService.selectMeetCustList(paramVO);
        CalendarVO resultVO = calenderService.selectMeetDetailView(paramVO);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/calendar/calendarDetailView");
        mav.addObject("paramVO", paramVO);
        mav.addObject("CRM01List", CRM01List);
        mav.addObject("CRM04List", CRM04List);
        mav.addObject("userList", userList);
        mav.addObject("custList", custList);
        mav.addObject("resultVO", resultVO);
        return mav;
    }
    
    //일정관리 상세 삭제 처리
    @PostMapping(value = "/calendar/calendarDeleteProc.do")
    public Map<String, Object> calendarDeleteProc(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        calenderService.calendarMeetDeleteProc(paramVO);
        Map<String, Object> map = new HashMap<>();
        map.put("paramVO", paramVO);
        return map;
    }
}


