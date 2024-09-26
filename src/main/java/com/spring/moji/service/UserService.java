package com.spring.moji.service;

import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {

  int join(User user) throws Exception;

  int updateProfileImageUrl(String email, String profileImageUrl, HttpSession session)
      throws Exception;

  int updateCoupleProfile(Long coupleId
      , String coupleName
      , String profileImageUrl
      , HttpSession session
  ) throws Exception;

  User findPartner(String email) throws Exception;
}
