package com.spring.moji.controller;

import com.spring.moji.entity.User;
import com.spring.moji.service.UserService;
import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;

  @GetMapping({"", "/"})
  public String home(Model model, Principal principal) {
    if (principal == null) {
      return "user/sign-in";
    }
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/community/community-diary-page.jsp");
    return "user/profile-page";
  }

  @GetMapping("/signup")
  public String signUp(Model model, Principal principal) {
    String loginId = principal != null ? principal.getName() : "guest";
    return "user/sign-up";
  }

  @PostMapping("/signup-process")
  public String signUpProcess(User user, Model model) throws Exception {
    int result = userService.join(user, model);

    if (result > 0) {
      return "redirect:/signin";
    }
    // 에러가 있으면 다시 회원가입 페이지로 이동하지만, 리다이렉트 대신 바로 JSP 페이지 반환
    model.addAttribute("user", user); // 입력한 사용자 데이터를 다시 모델에 넣음
    return "user/sign-up";
  }

  @GetMapping("/find-info")
  public String findInfo(Model model, Principal principal) {
    String loginId = principal != null ? principal.getName() : "guest";
    return "user/password";
  }

  @GetMapping("/signin")
  public String signIn(Model model, Principal principal) {
    String loginId = principal != null ? principal.getName() : "guest";
    return "user/sign-in";
  }

  @GetMapping("/forbidden")
  public String forbidden() {
    return "user/forbidden";
  }


}
