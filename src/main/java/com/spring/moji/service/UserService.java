package com.spring.moji.service;

import com.spring.moji.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface UserService {

  int join(User user, Model model) throws Exception;

  int updateProfileImageUrl(String email, String profileImageUrl, HttpSession session)
      throws Exception;

  int updateCoupleProfile(Long coupleId
      , String coupleName
      , String profileImageUrl
      , HttpSession session
  ) throws Exception;

  User findPartner(String email) throws Exception;

  boolean isEmailAvailable(String email) throws Exception;
}
