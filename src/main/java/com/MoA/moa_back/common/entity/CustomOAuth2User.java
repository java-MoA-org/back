package com.MoA.moa_back.common.entity;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomOAuth2User implements OAuth2User{

    private String name;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean existed;

    public CustomOAuth2User (String name, Map<String, Object> attributes, boolean existed) {
        this.name = name;
        this.attributes = attributes;
        this.authorities = AuthorityUtils.NO_AUTHORITIES;
        this.existed = existed;
    }
    
}
