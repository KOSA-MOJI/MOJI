package com.spring.moji.controller;

import com.spring.moji.service.UserService;
import com.spring.moji.service.UserServiceImpl;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  @GetMapping({"/solo/", "/solo",})
  public String readProfile(Model model, Principal principal) {
    String name = principal.getName();

    log.info("[[[  /profile  ]]]");
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/solo-profile.jsp");
    model.addAttribute("name", name);
    return "user/profile-page";
  }

  @GetMapping({"/solo/update-profile"})
  public String updateProfile(Model model, Principal principal) {
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/update-solo-profile.jsp");
    return "user/profile-page";
  }
}
