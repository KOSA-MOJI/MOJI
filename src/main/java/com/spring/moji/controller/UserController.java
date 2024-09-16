package com.spring.moji.controller;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping({"/", ""})
  public String profile(Model model, Principal principal) {
    log.info("[[[  /profile  ]]]");
    String loginId = principal != null ? principal.getName() : "guest";
    model.addAttribute("loginId", loginId);
    return "user/solo-profile";
  }
}
