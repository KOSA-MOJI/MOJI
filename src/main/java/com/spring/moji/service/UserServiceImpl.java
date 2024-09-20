package com.spring.moji.service;

import com.spring.moji.entity.UserAuth;
import com.spring.moji.entity.User;
import com.spring.moji.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

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
      userAuth.setAuth("ROLE_USER");
      result += userMapper.insertAuth(userAuth);
    }
    return result;
  }

  @Override
  public void updateProfileImageUrl(String email, String profileImageUrl) throws Exception {
    userMapper.updateProfileImageUrl(email, profileImageUrl);
  }
}
