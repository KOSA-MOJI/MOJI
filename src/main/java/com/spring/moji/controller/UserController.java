package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.service.UserService;
import com.spring.moji.service.UserServiceImpl;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  @GetMapping("/solo/profile")
  public String readSoloProfile(Model model) {

    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/solo-profile.jsp");

    return "user/profile-page";
  }

  @GetMapping("/couple/profile")
  public String readCoupleProfile(Model model) {

    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/couple-profile.jsp");

    return "user/profile-page";
  }

  @GetMapping({"/solo/profile/update"})
  public String updateSoloProfile(Model model, @AuthenticationPrincipal UserRequestDTO user) {
    Long coupleId = user.getCoupleId();

    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/update-solo-profile.jsp");

    return "user/profile-page";
  }

  @GetMapping({"/couple/profile/update"})
  public String updateCoupleProfile(Model model, @AuthenticationPrincipal UserRequestDTO user) {
    Long coupleId = user.getCoupleId();

    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/update-couple-profile.jsp");
    return "user/profile-page";
  }
}

