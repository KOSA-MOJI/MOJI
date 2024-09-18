package com.spring.moji.service;

import com.spring.moji.entity.UserAuthEntity;
import com.spring.moji.entity.UserEntity;

public interface UserService {

  public UserEntity login(String username);

  public int join(UserEntity user) throws Exception;

  public int insertAuth(UserAuthEntity userAuthEntity) throws Exception;
}
