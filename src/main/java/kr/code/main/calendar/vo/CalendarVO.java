package kr.code.main.calendar.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarVO extends CommonVO{
    private String meetId;                      //회의 아이디
    private String meetSe;                      //회의 구분
    private String meetTitle;                   //회의 제목
    private String meetCn;                      //회의 내용
    private String meetDate;                    //회의 일시
    private String meetHhMi;                    //회의 상세 (시간+분)
    private String meetLoc;                     //회의 장소

    private String meetJoinId;                  //회의 참석 아이디
    private String meetUserSe;                  //회의 유저(직원+고객) 구분
    private String meetUserId;                  //회의 유저 아이디

    /* 캘린더.js를 위한 */
    private String id;                          //캘린더 아이디
    private String title;                       //캘린더 제목
    private String start;                       //캘린더 시작

    /* 직원조회(autocomplete)를 위한 */
    private String userUid;                     //직원 U아이디
    private String userId;                      //직원 아이디
    private String userName;                    //직원 이름
    private String userSeqs[];                  //직원들

    /* 고객조회(autocomplete)를 위한 */
    private String custUid;                     //고객 U아이디
    private String custName;                    //고객 이름
    private String companyName;                 //고객 회사명
    private String custSeqs[];                  //고객들
}
