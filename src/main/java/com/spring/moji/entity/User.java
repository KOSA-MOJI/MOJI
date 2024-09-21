package com.spring.moji.entity;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  List<UserAuth> authList;
  private String email;  // 이메일
  private String userName; // 성함
  private LocalDate birthday;
  private String gender;
  private String password;
  private LocalDate createdAt;
  private String profileImageUrl;
  private Long coupleId;


  @Builder
  public User(String email, String userName, LocalDate birthday, String gender,
      String password,
      LocalDate createdAt, String profileImageUrl, Long coupleId) {
    this.email = email;
    this.userName = userName;
    this.birthday = birthday;
    this.gender = gender;
    this.password = password;
    this.createdAt = createdAt;
    this.profileImageUrl = profileImageUrl;
    this.coupleId = coupleId;
  }
}
