package com.spring.moji.entity;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

  private String email;  // 이메일
  private String userName; // 성함
  private LocalDate birthday;
  private char gender;
  private String password;
  private LocalDate createdAt;


  @Builder
  public UserEntity(String email, String userName, LocalDate birthday, char gender, String password,
      LocalDate createdAt) {
    this.email = email;
    this.userName = userName;
    this.birthday = birthday;
    this.gender = gender;
    this.password = password;
    this.createdAt = createdAt;
  }
}
