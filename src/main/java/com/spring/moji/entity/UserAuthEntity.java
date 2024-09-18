package com.spring.moji.entity;

import lombok.Data;

@Data
public class UserAuthEntity {

  private int authNo;
  private String userEmail;
  private String auth;  //getter
}
