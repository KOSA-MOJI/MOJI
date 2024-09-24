package com.spring.moji.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spring.moji.dto.request.UserRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Slf4j
public class SignInSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("로그인 인증 성공");

    UserRequestDTO user = (UserRequestDTO) authentication.getPrincipal();

    log.info(user.toString());
    log.info("아이디 :" + user.getUsername());
    log.info("패스워드 :" + user.getPassword());
    log.info("권한 :" + user.getAuthorities());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String jsonResponse = objectMapper.writeValueAsString(user);
    response.getWriter().write(jsonResponse);
    response.sendRedirect("/user/community");
  }
}
