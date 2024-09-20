package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.service.UserServiceImpl;
import com.spring.moji.util.S3Util;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

  private final UserServiceImpl userService;
  private final S3Util s3Util;

  @PostMapping({"/solo/update-profile"})
  public ResponseEntity<?> updateProfileProcess(
      @RequestParam("file") MultipartFile file,
      @RequestParam("email") String email,
      HttpSession session)
      throws Exception {
    String profileImageUrl = s3Util.uploadFile(file);
    userService.updateProfileImageUrl(email, profileImageUrl);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.isAuthenticated()) {
      UserRequestDTO currentUser = (UserRequestDTO) auth.getPrincipal();
      currentUser.getUser().setProfileImageUrl(profileImageUrl);
      UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
          currentUser, auth.getCredentials(), auth.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(newAuth);
      session.setAttribute("profileImageUrl", profileImageUrl);
      log.info("Profile image URL updated in session and authentication object");

    } else if (auth != null) {
      log.info("회원정보가 업습니다.");
    } else if (!auth.isAuthenticated()) {
      log.info("회원 인증이 안됨");

    }
    return ResponseEntity.ok()
        .body(Map.of("message", "Profile updated successfully", "redirectUrl", "/user/solo/"));
  }
}
