package com.spring.moji.dto.request;

import com.spring.moji.entity.Couple;
import com.spring.moji.entity.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Data
@NoArgsConstructor
public class UserRequestDTO implements UserDetails {

  private User user;

  public UserRequestDTO(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthList()
        .stream()
        .map((auth) -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  public String getEmail() {
    log.info("[[  getEmail 메서드 호출됨  ]]");
    return user.getEmail();
  }


  // jstl에서 user의 정보를 조회하는데 필요한 메서드
  public String getUserName() {

    return user.getUserName();
  }

  public LocalDate getBirthday() {

    return user.getBirthday();
  }

  public String getGender() {
    return user.getGender();
  }

  public LocalDate getCreatedAt() {
    return user.getCreatedAt();
  }

  public String getProfileImageUrl() {
    return user.getProfileImageUrl();
  }

  public Long getCoupleStatus() {
    return user.getCoupleStatus();
  }

  public Couple getCouple() {
    return user.getCouple();
  }

  public void setProfileImageUrl(String profileImageUrl) {

    if (this.user == null) {
      this.user = User.builder()  // builder를 통해 객체 생성
          .profileImageUrl(profileImageUrl)
          .build();
    } else {
      this.user.setProfileImageUrl(profileImageUrl);
    }
  }

  public void setEmail(String email) {
    log.info("[[  setEmail 메서드 호출됨  ]]");

    if (this.user == null) {
      this.user = User.builder()  // builder를 통해 객체 생성
          .email(email)
          .build();
    } else {
      this.user.setEmail(email);
    }
  }


}
