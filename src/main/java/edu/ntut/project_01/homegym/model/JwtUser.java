package edu.ntut.project_01.homegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final String id;
    private final String username;
    private final String password;
    private final Integer status ;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String id, String username, String password, Integer status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.authorities = authorities;
    }

    //返回用戶權限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 帳戶是否過期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 帳戶是否未鎖定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    // 密碼是否未過期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 帳戶是否驗證過
    @JsonIgnore
    @Override
        public boolean isEnabled() {
            return status == 1 ? true : false;

    }
}
