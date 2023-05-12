package kr.code.main.meeting.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MeetingVO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class response {
        private List<Meeting> list;
        private int totalSize;
        private PagingVO page;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meeting {

        private int meet_id;
        private String meet_se;
        private String meet_title;
        private String meet_cn;
        private String meet_date;
        private String meet_loc;
        private String meet_yn;
        private String first_rst_date;
        private String first_rst_id;
        private String last_rst_date;
        private String last_rst_id;
        private String meet_user_id;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingRequest{

        private String meet_title;
        private String meet_date;
        private String meet_loc;
        private String meet_cn;
        private String meet_user_id;
        private String meet_yn;
        private String last_rst_date;
        private String last_rst_id;
        private String first_rst_date;
        private String first_rst_id;


    }


}
