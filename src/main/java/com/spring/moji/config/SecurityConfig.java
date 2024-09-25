package com.spring.moji.config;

import static org.springframework.security.config.Customizer.*;

import com.spring.moji.security.CustomerAccessDeniedHandler;
import com.spring.moji.security.CustomerDetailService;
import com.spring.moji.security.SignInSuccessHandler;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomerDetailService customerDetailService;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(auth -> auth
//            .requestMatchers("/user/couple/diary").hasRole("COUPLE")
//            .requestMatchers("/user/solo/**").hasRole("SOLO")
//            .requestMatchers("/").hasAnyRole("COUPLE", "SOLO")
            .anyRequest().permitAll())
        .formLogin(withDefaults())
        .logout(withDefaults()
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
        .invalidateHttpSession(true))
    ;

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
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("http://localhost:8090");
      }
    };
  }
}
