package com.spring.moji.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

  @GetMapping({"", "/"})
  public String home(Model model, Principal principal) {
    String loginId = principal != null ? principal.getName() : "guest";
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/solo-profile.jsp");
    return "user/profile-page";
  }

  @GetMapping("/signup")
  public String signUp(Model model, Principal principal) {
    String loginId = principal != null ? principal.getName() : "guest";
    model.addAttribute("contentURL", "/WEB-INF/jsp/page/user/password.jsp");
    return "user/profile-page";
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
}
