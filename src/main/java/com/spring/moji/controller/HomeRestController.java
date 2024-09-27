package com.spring.moji.controller;

import com.spring.moji.service.UserServiceImpl;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeRestController {

  private final UserServiceImpl userService;

  @PostMapping("/check-email")
  public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> payload)
      throws Exception {
    String email = payload.get("email");
    boolean isAvailable = userService.isEmailAvailable(email);

    Map<String, Boolean> responseBody = new HashMap<>();
    responseBody.put("available", isAvailable);

    return ResponseEntity.ok().body(responseBody);
  }
}
