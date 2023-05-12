package kr.code.main.meeting.controller;

import kr.code.main.meeting.service.MeetingService;
import kr.code.main.meeting.vo.MeetingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService service;

    @GetMapping("/meeting")
    public ModelAndView meeting(@RequestParam(name="nowPageNumber",defaultValue = "1", required = false) int PageNumber){

        ModelAndView view = new ModelAndView();

        try{
            Map<String, Object> param = new HashMap();
            param.put("pageNumber", PageNumber);
            MeetingVO.response response = service.getMeetingData(param);

            view.addObject("totalSize", response.getTotalSize());
            view.addObject("MeetingList", response.getList());
            view.addObject("pageHtml", response.getPage().pageHTML());

        }catch(Exception e){
            e.printStackTrace();
        }

        view.setViewName("views/meeting/meetingView");
        return view;
    }

    @GetMapping("/meeting/detail")
    public ModelAndView meetingDetailForm(@RequestParam(name="meet_id") int meet_id){
        Map<String, Object> param = new HashMap<>();
        param.put("meet_id", meet_id);
        ModelAndView view = new ModelAndView();
        view.setViewName("views/meeting/meetingDetail");
        try{

            MeetingVO.Meeting meeting = service.getMeetingDetail(param);
            view.addObject("meeting", meeting);

        }catch(Exception e){
            e.printStackTrace();
        }

        return view;

    }


    @GetMapping("/meeting/write")
    public ModelAndView writeMeetingForm(){
        ModelAndView view = new ModelAndView();
        view.setViewName("views/meeting/meetingWrite");
        return view;
    }

    @GetMapping("/meeting/update")
    public ModelAndView updateMeetingForm(@RequestParam(name="meet_id")int meet_id){
        Map<String, Object> param = new HashMap<>();
        param.put("meet_id", meet_id);
        ModelAndView view = new ModelAndView();
        view.setViewName("views/meeting/meetingUpdate");
        try{
            MeetingVO.Meeting meeting = service.getMeetingDetail(param);
            view.addObject("meeting", meeting);
        }catch(Exception e){
            e.printStackTrace();
        }

        return view;
    }

    @PostMapping("/meeting/update")
    @ResponseBody
    public Map<String, Object> updateMeeting(@ModelAttribute MeetingVO.MeetingRequest meetingRequest){
        Map<String, Object> resultMap = new HashMap<>();
        try{

            int result = service.updateMeeting(meetingRequest);

            if(result > 0){
                resultMap.put("result", 200);
            }else{
                throw new Exception("게시글 수정 실패");
            }


        }catch(Exception e){
            resultMap.put("resultCode", 500);
            e.printStackTrace();
        }
        return resultMap;
    }

    @GetMapping("/meeting/delete")
    public ModelAndView deleteMeeting(@RequestParam(name="meet_id") int meet_id){
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/meeting");
        try{

            service.deleteMeeting(meet_id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }

}
