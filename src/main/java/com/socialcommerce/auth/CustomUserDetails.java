package com.socialcommerce.auth;

import com.socialcommerce.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {
    private final User user;
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        System.out.println("CustomUserDetails 생성자 호출");
        this.user = user;
        this.id = user.getUserid();
        System.out.println("User ID: " + id);
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println("Authorities 설정됨: " + authorities);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        System.out.println("getPassword 호출");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("getUsername 호출");
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
