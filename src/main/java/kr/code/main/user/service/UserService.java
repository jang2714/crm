package kr.code.main.user.service;

import kr.code.main.common.exception.AppException;
import kr.code.main.common.exception.ErrorCode;
import kr.code.main.user.domain.Role;
import kr.code.main.user.domain.entity.UserEntity;
import kr.code.main.user.domain.repository.UserRepository;
import kr.code.main.user.dto.UserDto;
import kr.code.main.user.dto.UserVO;
import kr.code.main.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String privateKey;
    private Long expiredTimeMs = 1000 * 60 * 60l;

    public String joinUser(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getUserId();
    }

    public List<UserEntity> findAllUser() {
        return userRepository.findAll();
    }

    public boolean CanUserModifiedAuth(String userUid) {
        AtomicBoolean result = new AtomicBoolean(false);
        userRepository.findByUserUid(userUid).ifPresent( user -> {
            if (Role.ADMIN.equals(user.getUserAuth())) {
                result.set(true);
            }
        });
        return result.get();
    }

    public boolean updateUserAuth(String userUid, int userAuth) {
        Optional<UserEntity> user = userRepository.findByUserUid(userUid);
        if (user.isPresent()) {
            user.get().setUserAuth(userAuth);
            userRepository.save(user.get());
            return true;
        }

        return false;
    }

    public String doLogin(String userId, String userPasswd) {
        // userName 없음
        UserEntity selectedUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userId + "이 없습니다."));

        // password 틀림
//        if (!encoder.matches(userPasswd, selectedUser.getUserPw())) {
//            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다.");
//        }

        return JwtUtil.createToken(selectedUser.getUserName(), privateKey, expiredTimeMs);
    }

    public UserVO findUser(String userId) {
        UserEntity foundUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userId + "이 없습니다."));

        UserVO userVO = UserVO.builder()
                .userPosition(foundUser.getUserPosition())
                .userGender(foundUser.getUserGender())
                .userDepart(foundUser.getUserDepart())
                .userBirth(foundUser.getUserBirth())
                .userName(foundUser.getUserName())
                .userUid(foundUser.getUserUid())
                .userId(foundUser.getUserId())
                .build();

        return userVO;
    }

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        Optional<UserEntity> userEntityWrapper = userRepository.findByUserId(userName);
//        UserEntity userEntity = userEntityWrapper.get();
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if(("admin").equals(userName)) {
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
//        }
//        return new User(userEntity.getUserId(), userEntity.getUserPw(), authorities);
//    }
}
