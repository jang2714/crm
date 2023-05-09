package kr.code.main.user.service;

import kr.code.main.user.domain.Role;
import kr.code.main.user.domain.entity.UserEntity;
import kr.code.main.user.domain.repository.UserRepository;
import kr.code.main.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

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
