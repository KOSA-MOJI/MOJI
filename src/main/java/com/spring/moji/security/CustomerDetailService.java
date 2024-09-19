package com.spring.moji.security;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.UserEntity;
import com.spring.moji.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerDetailService implements UserDetailsService {


  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserEntity user = userMapper.login(email);
    log.debug("Trying to find user by email: {}", email);
    if (user == null) {
      log.debug("Trying to find user by email: {}", email);
      log.error("User not found: {}", email);

      throw new UsernameNotFoundException("요청하신 이메일디 회원정보에 존재하지 않습니다." + email);
    }

    UserRequestDTO customerUser = new UserRequestDTO(user);
    log.debug("Loaded UserRequestDTO with authorities: {}", customerUser.getAuthorities());

    return customerUser;
  }

}
