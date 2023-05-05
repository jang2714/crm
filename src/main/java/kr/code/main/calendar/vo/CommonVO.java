package kr.code.main.calendar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CommonVO {
    private String useYn;                           //사용 여부
    private String firstRstId;                      //최초등록 사용자ID
    private Date firstRstDate;                      //최초등록 일시
    private String rgstrDt;                         //최초등록 일시_YYYY-MM-DD
    private String lastRstId;                       //최종수정 사용자ID
    private Date lastRstDate;                       //최종수정일시
    private String modDt;                           //최종수정일시_YYYY-MM-DD

    /* 검색 */
    private String searchType;                      //검색 코드
    private String searchKywd;                      //검색 키워드

    /* 공통 코드 */
    private String upperCodeId;                    //상위코드 아이디
    private String codeId;                         //코드아이디
    private String codeNm;                         //코드명
    private String codeDc;                         //코드설명
    private int codeOrd;                           //코드순서
    private String codeSe;                         //코드구분

}
