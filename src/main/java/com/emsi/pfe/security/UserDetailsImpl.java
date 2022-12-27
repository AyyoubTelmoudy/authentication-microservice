package com.emsi.pfe.security;

import com.emsi.pfe.entity.Role;
import com.emsi.pfe.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private String email;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private List<String> roles;
    public UserDetailsImpl(User user) {
        this.email=user.getEmail();
        this.password=user.getPassword();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roles=new ArrayList<>();
        for (Role role:user.getRoles())
        {
            roles.add(role.getRole());
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));
        }
        this.authorities=authorities;
        this.roles=roles;
    }

   public List<String> getRoles()
   {
       return this.roles;
   }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
