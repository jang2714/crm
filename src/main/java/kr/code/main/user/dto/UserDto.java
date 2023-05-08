package kr.code.main.user.dto;

import kr.code.main.user.domain.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private String userUid;
    private String userId;
    private String userPw;
    private String userName;
    private int userGender;
    private int userBirth;
    private int userDepart;
    private int userPosition;
    private String addrPost;
    private String userAddrs;
    private String userPhone;
    private String userTele;
    private String userEmail;
    private int userAuth;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userUid(userUid)
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userGender(userGender)
                .userBirth(userBirth)
                .userDepart(userDepart)
                .userPosition(userPosition)
                .addrPost(addrPost)
                .userAddrs(userAddrs)
                .userPhone(userPhone)
                .userTele(userTele)
                .userEmail(userEmail)
                .userAuth(userAuth)
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
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userDepart = userDepart;
        this.userPosition = userPosition;
        this.addrPost = addrPost;
        this.userAddrs = userAddrs;
        this.userPhone = userPhone;
        this.userTele = userTele;
        this.userEmail = userEmail;
        this.userAuth = userAuth;

    }

}
