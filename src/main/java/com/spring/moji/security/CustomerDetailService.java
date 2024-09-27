package com.spring.moji.security;

import com.spring.moji.entity.User;
import com.spring.moji.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerDetailService implements UserDetailsService {


  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userMapper.login(email);
    log.debug("Trying to find user by email: {}", email);
    if (user == null) {
      log.debug("Trying to find user by email: {}", email);
      log.error("User not found: {}", email);

      throw new UsernameNotFoundException("요청하신 이메일디 회원정보에 존재하지 않습니다." + email);
    }

    CustomerUserDetail customerUser = new CustomerUserDetail(user);
    log.debug("Loaded CustomerUserDetail with authorities: {}", customerUser.getAuthorities());

    return customerUser;
  }

}
