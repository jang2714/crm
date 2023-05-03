package kr.code.main.calendar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CalendarVO {
    private String meetId;                      //회의 아이디
    private String meetSe;                      //회의 구분
    private String meetTitle;                   //회의 제목
    private String meetCn;                      //회의 내용
    private String meetDate;                    //회의 일시
    private String meetLoc;                     //회의 장소

    private String meetJoinId;                  //회의 참석 아이디
    private String meetUserSe;                  //회의 유저(직원+고객) 구분
    private String meetUserId;                  //회의 유저 아이디
    
    private String id;                          //캘린더 아이디
    private String title;                       //캘린더 제목
    private String start;                       //캘린더 시작
}
