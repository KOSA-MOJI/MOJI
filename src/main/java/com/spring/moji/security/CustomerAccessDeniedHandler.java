package com.spring.moji.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;


@Slf4j
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.access.AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    log.info("접근 거부 에러 처리");

    int statusCode = response.getStatus(); //응답 상태코드

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
      log.error("권한 부족 에러: " + accessDeniedException.getMessage());
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.sendRedirect("/forbidden");
    } else {
      log.error("인증 에러: " + accessDeniedException.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.sendRedirect("/signin");
    }

    log.info("HTTP 응답 상태 코드 : " + statusCode);
    log.error("accessDeniedException : " + accessDeniedException);
  }
}

