package kr.code.main.user.dto;

import java.util.Optional;

public record UserUpdateDto(Optional<String> userPw, Optional<Integer> userBirth, Optional<Integer> userDepart, Optional<Integer> userPosition,
                            Optional<String> userAddrs, Optional<String> userPhone, Optional<String> userTele, Optional<String> userEmail, Optional<Integer> userAuth) {
}
