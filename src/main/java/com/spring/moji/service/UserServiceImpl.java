package com.spring.moji.service;

import com.spring.moji.entity.UserAuthEntity;
import com.spring.moji.entity.UserEntity;
import com.spring.moji.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserEntity login(String email) {
    UserEntity user = userMapper.login(email);
    return user;
  }

  @Override
  @Transactional
  public int join(UserEntity user) throws Exception {
    String userPassword = user.getPassword();
    String encodedUserPassword = passwordEncoder.encode(userPassword);
    user.setPassword(encodedUserPassword);

    //회원등록
    int result = userMapper.join(user);

    if (result > 0) {
      UserAuthEntity userAuthEntity = new UserAuthEntity();
      userAuthEntity.setUserEmail(user.getEmail());
      userAuthEntity.setAuth("ROLE_USER");
      result += userMapper.insertAuth(userAuthEntity);
    }
    return result;
  }

  @Override
  public int insertAuth(UserAuthEntity userAuthEntity) throws Exception {
    return 0;
  }
}
