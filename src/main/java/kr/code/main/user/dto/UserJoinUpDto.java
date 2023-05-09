package kr.code.main.user.dto;

import kr.code.main.user.domain.entity.UserEntity;

public record UserJoinUpDto(String userUid, String userId, String userPw, String userName, Integer userGender,
                            Integer userBirth, Integer userDepart, Integer userPosition, String addrPost, String userAddrs,
                            String userPhone, String userTele, String userEmail, Integer userAuth) {

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
}
