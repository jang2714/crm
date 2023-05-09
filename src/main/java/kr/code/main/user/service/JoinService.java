package kr.code.main.user.service;

import kr.code.main.user.dto.UserDto;

public interface JoinService {

    /*
    * 회원가입, 정보수정, 회원탈퇴, 정보수정
    * */

    void joinUp(UserDto userDto) throws Exception;
    void update(UserDto userDto) throws Exception;

    void updateUserPw(String checkUserPw, String toBeUserPw) throws Exception;

    void withdraw(String checkUserPw) throws Exception;

    UserDto getInfo(String userUid) throws Exception;

    UserDto getMyInfo() throws Exception;

}
