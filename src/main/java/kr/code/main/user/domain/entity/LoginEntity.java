package kr.code.main.user.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tbl_user")
public class LoginEntity {

	private String userUid;
	private String userId;
	private String userPw;
	private String userName;
	private String userGender;
	private int userBirth;
	private int userDepart;
	private int userPosition;
	private String addrPost;
	private String userAddrs;
	private String userPhone;
	private String userTele;
	private String userEmail;
	private int userAuth;

	@Builder
	public LoginEntity(String userUid, String userId, String userPw, String userName,
		String userGender, int userBirth, int userDepart, int userPosition, String addrPost,
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
