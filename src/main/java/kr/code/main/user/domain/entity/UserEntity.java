package kr.code.main.user.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity @Setter
@Table(name="tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userUid;
    private String userId;
    private String userPw;
    private String userName;
    private int userGender;
    private int userBirth;
    private int userDepart;
    @Column(name="user_posi")
    private int userPosition;
    private String addrPost;
    private String userAddrs;
    private String userPhone;
    private String userTele;
    private String userEmail;
    private int userAuth;

    @Builder
    public UserEntity(String userUid, String userId, String userPw, String userName,
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
