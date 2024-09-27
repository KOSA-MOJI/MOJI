package com.spring.moji.service;

import com.spring.moji.security.CustomerUserDetail;
import com.spring.moji.entity.UserAuth;
import com.spring.moji.entity.User;
import com.spring.moji.mapper.RequestMapper;
import com.spring.moji.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;
  private final RequestMapper requestMapper;

  @Override
  @Transactional
  public int join(User user) throws Exception {
    String userPassword = user.getPassword();
    String encodedUserPassword = passwordEncoder.encode(userPassword);
    user.setPassword(encodedUserPassword);

    //회원등록
    int result = userMapper.join(user);

    if (result > 0) {
      UserAuth userAuth = new UserAuth();
      userAuth.setUserEmail(user.getEmail());
      userAuth.setAuth("ROLE_SOLO");
      result += userMapper.insertAuth(userAuth);
    }
    return result;
  }

  @Override
  public int updateProfileImageUrl(String email, String profileImageUrl, HttpSession session)
      throws Exception {
    int result = userMapper.updateProfileImageUrl(email, profileImageUrl);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
      CustomerUserDetail currentUser = (CustomerUserDetail) auth.getPrincipal();
      currentUser.getUser().setProfileImageUrl(profileImageUrl);
      UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
          currentUser, auth.getCredentials(), auth.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(newAuth);
      session.setAttribute("profileImageUrl", profileImageUrl);
      log.info("Profile image URL updated in session and authentication object");
    }
    return result;
  }

  @Override
  public int updateCoupleProfile(Long coupleId
      , String coupleName
      , String profileImageUrl
      , HttpSession session)
      throws Exception {
    int result = userMapper.updateCoupleProfile(coupleId, profileImageUrl, coupleName);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
      CustomerUserDetail currentUser = (CustomerUserDetail) auth.getPrincipal();
      currentUser.getCouple().setCoupleProfileImage(profileImageUrl);

      UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
          currentUser, auth.getCredentials(), auth.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(newAuth);

      session.setAttribute("coupleProfileImage", profileImageUrl);
      session.setAttribute("coupleName", coupleName);
      log.info("Profile image URL updated in session and authentication object");
    }
    return result;
  }

  @Override
  public User findPartner(String email) throws Exception {
    return userMapper.findPartner(email);
  }

  @Override
  public boolean isEmailAvailable(String email) throws Exception {
    return userMapper.countByEmail(email) == 0;
  }


}
