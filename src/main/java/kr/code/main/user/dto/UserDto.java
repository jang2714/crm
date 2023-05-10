package kr.code.main.user.dto;

import kr.code.main.user.domain.entity.UserEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private String userUid;
    private String userId;
    private String userPw;
    private String userName;
    private String userGender;
    private int userBirth;
    private int userDepart;
    private int userPosition;
    private String addrPost;
    private String addr1;
    private String addr2;
    private String userPhone;
    private String userTele;
    private String mailId;
    private String mailAddr;
    private String userEmail;
    private String userAuth;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userUid(UUID.randomUUID().toString())
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userGender(userGender == "남자" ? 1:2)
                .userBirth(userBirth)
                .userDepart(userDepart)
                .userPosition(userPosition)
                .addrPost(addrPost)
                .userAddrs(addr1 + " " + addr2)
                .userPhone(userPhone)
                .userTele(userTele)
                .userEmail(mailId + "@" + mailAddr)
                .userAuth(userAuth == "ADMIN" ? 1 : 2)
                .build();

    }

    @Builder
    public UserDto(String userUid, String userId, String userPw, String userName,
                   int userGender, int userBirth, int userDepart, int userPosition, String addrPost,
                   String userAddrs, String userPhone, String userTele, String userEmail, int userAuth) {

        this.userUid = userUid;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userGender = userGender == 1 ? "남자" : "여자";
        this.userBirth = userBirth;
        this.userDepart = userDepart;
        this.userPosition = userPosition;
        this.addrPost = addrPost;
        this.addr1 = userAddrs;
        this.addr2 = "";
        this.userPhone = userPhone;
        this.userTele = userTele;
        this.mailId = userEmail.substring( 0, userEmail.indexOf("@"));
        this.mailAddr = userEmail.substring( userEmail.indexOf("@") + 1);
        this.userEmail = userEmail;
        this.userAuth = userAuth == 1 ? "ADMIN" : "USER";

    }

}
