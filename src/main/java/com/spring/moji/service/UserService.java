package com.spring.moji.service;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.User;

public interface UserService {

  public int join(User user) throws Exception;

  public int updateProfileImageUrl(String email, String profileImageUrl) throws Exception;
}
