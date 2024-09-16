package com.spring.moji.security;

import com.spring.moji.dto.request.CustomerUserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Slf4j
public class SignInSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("로그인 인증 성공");

    // User user = (User)authentication.getPrincipal();
    CustomerUserDTO user = (CustomerUserDTO) authentication.getPrincipal();

    log.info("아이디 :" + user.getUsername());
    log.info("패스워드 :" + user.getPassword());
    log.info("권한 :" + user.getAuthorities());

    response.sendRedirect("/user/");
  }
}
