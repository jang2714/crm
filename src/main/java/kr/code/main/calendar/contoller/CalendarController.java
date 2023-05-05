package kr.code.main.calendar.contoller;

import kr.code.main.calendar.service.CalendarService;
import kr.code.main.calendar.service.CommonService;
import kr.code.main.calendar.vo.CalendarVO;
import kr.code.main.calendar.vo.CommonVO;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/calendar/calendarView.do")
    public ModelAndView calendarView(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        //log.debug("calendarView" + paramVO == null ? "NULL" : paramVO.toString());

        ModelAndView mav = new ModelAndView("jsonView");
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
        System.out.println("CRM01List.toString(): " + CRM01List.toString());
        ModelAndView mav = new ModelAndView("jsonView");
        mav.setViewName("views/calendar/calendarRegistView");
        mav.addObject("paramVO", paramVO);
        mav.addObject("CRM01List", CRM01List);
        return mav;
    }

    //일정관리 등록 처리
    @PostMapping("/calendar/calendarRegistProc.do")
    public ModelAndView calendarRegistProc(CalendarVO paramVO, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("paramVO", paramVO);
        return mav;
    }
}


