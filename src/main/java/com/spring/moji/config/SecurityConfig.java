package com.spring.moji.config;

import static org.springframework.security.config.Customizer.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/user/**").hasAnyRole("COUPLE", "SOLO")
            .requestMatchers("/couple/**").hasRole("COUPLE")
            .requestMatchers("/solo/**").hasRole("SOLO")
            .anyRequest().permitAll())
        .formLogin(withDefaults())
        .logout(withDefaults());

    http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

    http.logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true));

    return http.build();

  }

  @Bean
  public UserDetailsService userDetailsService() {
    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

    //사용자 인증 쿼리
    String sql1 = "select email as email , password as password  from users where email=?";

    //사용자 권한 쿼리
    String sql2 = "select email as email , role_id as roleId from user_auth where email=?";

    userDetailsManager.setUsersByUsernameQuery(sql1); //계정 비번 확인
    userDetailsManager.setAuthoritiesByUsernameQuery(sql2); //권한 처리

    return userDetailsManager;
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
}
