package com.spring.moji.service;

import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;

public interface UserService {

  int join(User user) throws Exception;

  int updateProfileImageUrl(String email, String profileImageUrl) throws Exception;

  User findUserbyEmail(String email) throws Exception;

}
