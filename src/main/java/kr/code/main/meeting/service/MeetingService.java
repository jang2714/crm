package kr.code.main.meeting.service;

import kr.code.main.meeting.mapper.MeetingMapper;
import kr.code.main.meeting.vo.MeetingVO;
import kr.code.main.meeting.vo.PagingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingMapper mapper;

    public MeetingVO.response getMeetingData(Map<String, Object> param) throws Exception{
        //전체 게시판 글 개수 가져오기
        int totalSize = mapper.getMeetingTotalRows(param);
        //게시글 다을 리스트
        List<MeetingVO.Meeting> list = new ArrayList<>();
        //현재 페이지
        int pageNumber = (int) param.get("pageNumber");
        //페이징 처리
        PagingVO page = new PagingVO(pageNumber, totalSize);

        //게시글이 있을 경우만 가져온다.
        if(totalSize > 0){
            param.put("start", page.getBeginPage());
            param.put("end", page.getEndPage());
            list = mapper.getMeetingList(param);
        }
        return MeetingVO.response
                .builder()
                .list(list)
                .totalSize(totalSize)
                .page(page)
                .build();
    }

    public MeetingVO.Meeting getMeetingDetail(Map<String, Object> param) throws Exception{
        MeetingVO.Meeting meeting = mapper.getMeetingDetail(param);
        return meeting;
    }

    public int updateMeeting(MeetingVO.MeetingRequest meetingRequest) throws Exception{
        int result = 0;

        MeetingVO.Meeting updateMeeting = MeetingVO.Meeting
                .builder()
                .meetTitle(meetingRequest.getMeetTitle())
                .meetYn(meetingRequest.getMeetYn())
                .meetLoc(meetingRequest.getMeetLoc())
                .meetUserId(meetingRequest.getMeetUserId())
                .lastRstDate(meetingRequest.getLastRstDate())
                .lastRstId(meetingRequest.getLastRstId())
                .build();

        result = mapper.updateMeeting(updateMeeting);
        return result;

    }

    public int writeMeeting(MeetingVO.MeetingRequest meetingRequest) throws Exception{
        int result = 0;

        MeetingVO.Meeting meetingCreate = MeetingVO.Meeting
                                            .builder()
                                            .meetTitle(meetingRequest.getMeetTitle())
                                            .meetUserId(meetingRequest.getMeetUserId())
                                            .meetDate(meetingRequest.getMeetDate())
                                            .meetLoc(meetingRequest.getMeetLoc())
                                            .meetCn(meetingRequest.getMeetCn())
                                            .firstRstId(meetingRequest.getFirstRstId())
                                            .firstRstDate(meetingRequest.getFirstRstDate())
                                            .build();

        result = mapper.writeMeeting(meetingCreate);

        return result;

    }

    public void deleteMeeting(int meet_id) throws Exception{
        Map<String, Object> param = new HashMap<>();
        param.put("meet_id", meet_id);

        MeetingVO.Meeting meeting = mapper.getMeetingDetail(param);

        mapper.deleteMeeting(meet_id);

    }
}
