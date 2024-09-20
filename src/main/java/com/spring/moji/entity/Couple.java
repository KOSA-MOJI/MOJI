package com.spring.moji.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple {
  private Long couple_id;
  private String user1Email;
  private String user2Email;
  private String coupleName;
  private String coupleProfileImage;
  @Builder
  public Couple(Long couple_id, String user1Email, String user2Email, String coupleName,
      String coupleProfileImage) {
    this.couple_id = couple_id;
    this.user1Email = user1Email;
    this.user2Email = user2Email;
    this.coupleName = coupleName;
    this.coupleProfileImage = coupleProfileImage;
  }
}
