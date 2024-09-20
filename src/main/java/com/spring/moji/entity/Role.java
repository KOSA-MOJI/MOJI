package com.spring.moji.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
  private Long roleID;
  private String roleName;
  @Builder
  public Role(Long roleID, String roleName) {
    this.roleID = roleID;
    this.roleName = roleName;
  }
}
