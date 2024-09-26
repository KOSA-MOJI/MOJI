package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.User;
import com.spring.moji.service.RequestServiceImpl;
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

  private final UserServiceImpl userService;
  private final RequestServiceImpl requestService;


  @GetMapping({"/solo/", "/solo",})
  public String readProfile(Model model, @AuthenticationPrincipal UserRequestDTO user)
      throws Exception {
    User requestUser = requestService.checkRequestUser(user.getEmail());
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/solo-profile.jsp");
    model.addAttribute("requestUserName", requestUser.getUserName());
    model.addAttribute("requestUserEmail", requestUser.getEmail());
    model.addAttribute("requestUserProfileImageSource", requestUser.getProfileImageUrl());

    return "user/profile-page";
  }

  @GetMapping({"/solo/update-profile"})
  public String updateProfile(Model model) {
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/update-solo-profile.jsp");
    return "user/profile-page";
  }

  @GetMapping({"/couple/", "/couple",})
  public String readCoupleProfile(Model model) {
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/couple-profile.jsp");
    return "user/profile-page";
  }

  @GetMapping({"/couple/update-profile"})
  public String updateCoupleProfile(Model model) {
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/update-couple-profile.jsp");
    return "user/profile-page";
  }
}
