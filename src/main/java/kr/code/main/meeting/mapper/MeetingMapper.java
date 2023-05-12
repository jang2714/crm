package kr.code.main.meeting.mapper;

import kr.code.main.meeting.vo.MeetingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface MeetingMapper {
    int getMeetingTotalRows(Map<String, Object> param) throws Exception;
    //게시판에 보여줄 게시글 리스트 가져오기
    List<MeetingVO.Meeting> getMeetingList(Map<String, Object> param) throws Exception;
    //게시글 쓰기
    int writeMeeting(MeetingVO.Meeting createMeeting) throws Exception;
    //게시판에 보여줄 게시글 가져오기
    MeetingVO.Meeting getMeetingDetail(Map<String, Object> param) throws Exception;
    //게시글 삭제
    int deleteMeeting(@Param("meet_id") int meet_id) throws Exception;
    //게시판 수정
    int updateMeeting(MeetingVO.Meeting updateMeeting) throws Exception;
}
