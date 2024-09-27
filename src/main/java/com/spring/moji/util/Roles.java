package com.spring.moji.util;

import lombok.Getter;

@Getter
public enum Roles {
  COUPLE("ROLE_COUPLE"),
  SOLO("ROLE_SOLO");

  private final String role;

  Roles(String role) {
    this.role = role;
  }
}
