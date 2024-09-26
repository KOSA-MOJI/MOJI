package com.spring.moji.controller;

import com.spring.moji.entity.User;
import com.spring.moji.service.UserService;
import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    String loginId = principal != null ? principal.getName() : "guest";
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/solo-profile.jsp");
    return "user/profile-page";
  }

  @GetMapping("/signup")
  public String signUp(Model model, Principal principal) {
    String loginId = principal != null ? principal.getName() : "guest";
    return "user/sign-up";
  }

  @PostMapping("/signup-process")
  public String signUpProcess(User user) throws Exception {
    int result = userService.join(user);

    if (result > 0) {
      return "redirect:/signin";
    }
    return "redirect:/signup?error";
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
