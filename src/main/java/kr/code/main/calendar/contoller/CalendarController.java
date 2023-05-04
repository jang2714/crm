package kr.code.main.calendar.contoller;

import kr.code.main.calendar.service.CalendarService;
import kr.code.main.calendar.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONArray;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

    //일정관리 화면
    @GetMapping("/calendar/calendarView.do")
    public ModelAndView calendarView(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        //log.debug("calendarView" + paramVO == null ? "NULL" : paramVO.toString());

        ModelAndView mav = new ModelAndView("jsonView");
        mav.setViewName("views/calendar/calendarView");
        mav.addObject("paramVO", paramVO);
        return mav;
    }

    //일정관리 로직 처리
    @PostMapping("/calendar/calendarProc.do")
    public Map<String, Object> calendarProc(CalendarVO paramVO, HttpServletRequest request) throws Exception {
        //log.debug("calendarView" + paramVO == null ? "NULL" : paramVO.toString());
        List<CalendarVO> resultList = calenderService.selectCalendarList(paramVO);
        Map<String, Object> map = new HashMap<>();
        map.put("resultList", JSONArray.fromObject(resultList));
        return map;
    }
}


