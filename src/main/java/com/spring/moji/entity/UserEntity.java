package com.spring.moji.entity;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

  List<UserAuthEntity> authList;
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
