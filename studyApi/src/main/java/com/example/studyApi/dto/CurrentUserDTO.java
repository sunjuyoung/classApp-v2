package com.example.studyApi.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUserDTO extends User {

    private String nickname;
    private String password;

    public CurrentUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.nickname = username;
        this.password = password;
    }
}
