package com.spring.moji.entity;

import lombok.Data;

@Data
public class UserAuth {

  private int authNo;
  private String userEmail;
  private String auth;  //getter
}
