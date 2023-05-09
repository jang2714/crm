package kr.code.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserVO {

    private String userUid;
    private String userId;
    private String userName;
    private int userGender;
    private int userBirth;
    private int userDepart;
    private int userPosition;
    private String userTele;
    private String userEmail;
}
