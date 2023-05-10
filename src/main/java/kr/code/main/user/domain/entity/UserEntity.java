package kr.code.main.user.domain.entity;

import kr.code.main.user.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity @Setter
@Table(name="tbl_user")
public class UserEntity {

    @Id
    @Column(nullable = false)
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

    //정보수정

    public void updatePassword(PasswordEncoder passwordEncoder, String userPw){
        this.userPw = passwordEncoder.encode(userPw);
    }

    public void updateName(String userName){
        this.userName = userName;
    }

    public void updateDepart(int userDepart) {
        this.userDepart = userDepart;
    }

    public void updatePosition(int userPosition) {
        this.userPosition = userPosition;
    }

    public void updatePhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void updateTelephone(String userTele) {
        this.userTele = userTele;
    }


    //비밀번호 변경, 회원탈퇴 시, 비밀번호를 확인하며, 비번 일치여부 판단 메서드
    public boolean matchPasswd(PasswordEncoder passwordEncoder, String checkPasswd) {
        return passwordEncoder.matches(checkPasswd, getUserPw());
    }

    //회원가입시, user 권한을 부여하는 메서드
    public void addUserAuthority() {
        this.userAuth = Role.USER.ordinal();
    }

}
