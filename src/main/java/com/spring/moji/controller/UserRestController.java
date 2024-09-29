package com.spring.moji.controller;

import com.spring.moji.security.CustomerUserDetail;
import com.spring.moji.entity.User;
import com.spring.moji.service.RequestServiceImpl;
import com.spring.moji.service.UserServiceImpl;
import com.spring.moji.util.S3Util;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final RequestServiceImpl requestService;
  private final S3Util s3Util;

  @PostMapping({"/solo/update-profile"})
  public ResponseEntity<?> updateProfileProcess(
      @RequestParam("file") MultipartFile file,
      @RequestParam("email") String email,
      HttpSession session)
      throws Exception {
    if (file != null) {
      String profileImageUrl = s3Util.uploadFile(file);
      userService.updateProfileImageUrl(email, profileImageUrl, session);
    }
    return ResponseEntity.ok()
        .body(Map.of("message", "Profile updated successfully", "redirectUrl", "/user/solo/"));
  }

  @PostMapping({"/couple/update-profile"})
  public ResponseEntity<?> updateCoupleProfileProcess(
      @RequestParam("file") MultipartFile file,
      @AuthenticationPrincipal CustomerUserDetail user,
      HttpSession session)
      throws Exception {
    if (file != null) {
      String profileImageUrl = s3Util.uploadFile(file);
      userService.updateCoupleProfile(
          user.getCouple().getCouple_id()
          , user.getCouple().getCoupleName()
          , profileImageUrl
          , session
      );
    }
    return ResponseEntity.ok()
        .body(Map.of("message", "Profile updated successfully", "redirectUrl", "/user/solo/"));
  }


  @PostMapping("/solo/request")
  public ResponseEntity<?> requestCouple(@AuthenticationPrincipal CustomerUserDetail user,
      @RequestParam("receiverEmail") String receiverEmail) throws Exception {
    String requestEmail = user.getEmail();
    String message = requestService.requestCouple(requestEmail, receiverEmail);

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);
    responseBody.put("message", message);

    return ResponseEntity.ok().body(responseBody);
  }


  @GetMapping("/solo/request/check")
  public ResponseEntity<?> checkRequest(@AuthenticationPrincipal CustomerUserDetail user)
      throws Exception {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    User requestUser = requestService.checkRequestUser(user.getEmail());
    String requestUserName = requestUser.getUserName();

    if (requestUserName == null) {
      responseBody.put("message", "커플 신청이 존재하지 않거나 이미 커플입니다.");
      return ResponseEntity.ok().body(responseBody);
    }
    responseBody.put("alert", false);
    return ResponseEntity.ok().body(responseBody);
  }

  @PostMapping("/solo/request/accept")
  public ResponseEntity<?> acceptRequest(@AuthenticationPrincipal CustomerUserDetail user,
      @RequestParam("requestUserEmail") String requestUserEmail, HttpSession session)
      throws Exception {
    log.info("백엔드로 넘어온 requestUserEmail : {}", requestUserEmail);
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    int result = requestService.acceptRequest(requestUserEmail, user.getEmail(), session);

    log.info("acceptRequest 호출완료, 결과 : {}", result);

    if (result == 6) {
      responseBody.put("message", "커플 신청을 성공적으로 받았습니다.");

    } else if (result == 0) {
      responseBody.put("message", "진행중인 커플 신청이 존재하지 않습니다.");
    }
    return ResponseEntity.ok().body(responseBody);
  }

  @DeleteMapping("/solo/request/deny")
  public ResponseEntity<?> denyRequest(@AuthenticationPrincipal CustomerUserDetail user
  ) throws Exception {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);
    int result = requestService.deleteRequest(user.getEmail());

    if (result > 0) {
      responseBody.put("message", "커플 신청을 거부했습니다.");

    } else {
      responseBody.put("message", "받은 커플 신청이 없습니다.");
    }
    return ResponseEntity.ok().body(responseBody);
  }

  @DeleteMapping("/solo/request/cancel")
  public ResponseEntity<?> cancelRequest(@AuthenticationPrincipal CustomerUserDetail user
  ) throws Exception {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    int result = requestService.cancelRequest(user.getEmail());

    if (result > 0) {
      responseBody.put("message", "커플 신청을 취소했습니다.");

    } else {
      responseBody.put("message", "보낸 커플 신청이 없습니다.");
    }
    return ResponseEntity.ok().body(responseBody);
  }

  @DeleteMapping("/couple/breakup")
  public ResponseEntity<?> breakup(@AuthenticationPrincipal CustomerUserDetail user,
      @RequestParam("receiverEmail") String receiverEmail) throws Exception {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    int result = requestService.breakup(user.getEmail(), receiverEmail);

    if (result > 0) {
      responseBody.put("message", "헤어졌습니다.");

    } else {
      responseBody.put("message", "알수 없는 오류가 발생했습니다.");
    }
    return ResponseEntity.ok().body(responseBody);
  }
}
