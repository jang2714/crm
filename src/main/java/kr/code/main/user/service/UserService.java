package kr.code.main.user.service;

import kr.code.main.user.domain.Role;
import kr.code.main.user.domain.entity.UserEntity;
import kr.code.main.user.domain.repository.UserRepository;
import kr.code.main.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public String joinUser(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getUserId();
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
