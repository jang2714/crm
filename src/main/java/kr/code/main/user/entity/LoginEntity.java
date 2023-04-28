package kr.code.main.user.entity;

import lombok.Data;

@Data
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
	
}
