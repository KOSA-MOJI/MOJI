package com.spring.moji.dto.request;

import com.spring.moji.entity.UserEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerUserDTO implements UserDetails {

  private UserEntity user;

  public CustomerUserDTO(UserEntity user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthList()
        .stream()
        .map((auth) -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

}
