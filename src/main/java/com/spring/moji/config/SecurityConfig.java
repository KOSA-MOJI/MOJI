package com.spring.moji.config;


import com.spring.moji.security.CustomerAccessDeniedHandler;
import com.spring.moji.security.CustomerDetailService;
import com.spring.moji.security.SignInSuccessHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomerDetailService customerDetailService;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/user/diary/**").hasRole("COUPLE")
        .requestMatchers("/api/user/solo/**").hasAnyRole("SOLO", "COUPLE")
        .requestMatchers("/api/user/couple/**").hasRole("COUPLE")

        .requestMatchers("/user/couple/**").hasRole("COUPLE")
        .requestMatchers("/user/solo/**").hasAnyRole("COUPLE", "SOLO")
        .requestMatchers("/user/community").hasAnyRole("COUPLE", "SOLO")
        .requestMatchers("/user/map").hasAnyRole("COUPLE", "SOLO")

        .anyRequest().permitAll()
    );

    http.formLogin(form -> form
        .loginPage("/signin")  // 커스텀 로그인 페이지 요청 경로
        .loginProcessingUrl("/signin-process")  // 커스텀 로그인 처리 경로 지정
        .defaultSuccessUrl("/user/solo", true)  // 로그인 성공 시 이동할 경로
        .usernameParameter("email")  // 사용자 이름 파라미터 설정
        .passwordParameter("password")  // 패스워드 파라미터 설정
        .successHandler(authenticationSuccessHandler())  // 성공 시 핸들러 설정
        .permitAll()  // 모든 사용자에게 로그인 페이지 접근 허용
    );

    http.sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .maximumSessions(1)
        .maxSessionsPreventsLogin(true)
    );
    http.logout((logout) -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutSuccessUrl("/signin")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID"));

    http.exceptionHandling(exceptions -> exceptions
        .accessDeniedHandler(accessDeniedHandler())
    );

    http.userDetailsService(customerDetailService);

    return http.build();

  }


  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws
      Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new SignInSuccessHandler();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomerAccessDeniedHandler();
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    SessionRegistry sessionRegistry = new SessionRegistryImpl();
    return sessionRegistry;

  }

  @Bean
  public ServletListenerRegistrationBean httpSessionEventPublisher() {
    return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
  }
}
