package com.spring.moji.entity;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

  List<UserAuth> authList;
  private String email;  // 이메일
  private String userName; // 성함
  private LocalDate birthday;
  private char gender;
  private String password;
  private LocalDate createdAt;
  private int roleId;


  @Builder
  public UserEntity(String email, String userName, LocalDate birthday, char gender, String password,
      LocalDate createdAt, int roleId) {
    this.email = email;
    this.userName = userName;
    this.birthday = birthday;
    this.gender = gender;
    this.password = password;
    this.createdAt = createdAt;
    this.roleId = roleId;
  }
}
