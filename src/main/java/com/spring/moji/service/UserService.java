package com.spring.moji.service;

import com.spring.moji.entity.UserAuthEntity;
import com.spring.moji.entity.UserEntity;

public interface UserService {

  public int join(UserEntity user) throws Exception;

  public void updateProfileImageUrl(String email, String profileImageUrl) throws Exception;

}
