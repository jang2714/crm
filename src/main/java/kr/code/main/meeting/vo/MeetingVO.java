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

        private int meetId;
        private String meetSe;
        private String meetTitle;
        private String meetCn;
        private String meetDate;
        private String meetLoc;
        private String meetYn;
        private String firstRstDate;
        private String firstRstId;
        private String lastRstDate;
        private String lastRstId;
        private String meetUserId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingRequest{

        private String meetTitle;
        private String meetDate;
        private String meetLoc;
        private String meetCn;
        private String meetUserId;
        private String meetYn;
        private String lastRstDate;
        private String lastRstId;
        private String firstRstDate;
        private String firstRstId;


    }


}
