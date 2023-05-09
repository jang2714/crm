package kr.code.main.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    NOTHING("ROLE_NONE"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
};
