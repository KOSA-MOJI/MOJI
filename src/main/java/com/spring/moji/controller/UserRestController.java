package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/solo/upload")
  public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file)
      throws Exception {
    // 파일 저장 로직
    String fileUrl = userService.uploadFile(file);
    Map<String, String> response = new HashMap<>();
    response.put("fileUrl", fileUrl);
    return ResponseEntity.ok(response);
  }

  @PutMapping({"/solo/update-profile"})
  public ResponseEntity<?> updateProfileProcess(@RequestBody UserRequestDTO user,
      HttpSession session) throws Exception {
    userService.updateProfileImageUrl(user);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.isAuthenticated()) {
      session.setAttribute("profileImageUrl", user.getProfileImageUrl());
    }
    return ResponseEntity.ok()
        .body(Map.of("message", "Profile updated successfully", "redirectUrl", "/user/solo/"));
  }
}
