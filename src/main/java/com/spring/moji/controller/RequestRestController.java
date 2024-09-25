package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;
import com.spring.moji.service.RequestServiceImpl;
import com.spring.moji.service.UserServiceImpl;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin(origins = "http://localhost:8090") // 추가

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/solo/api/request")
public class RequestRestController {

  private final RequestServiceImpl requestService;
  private final UserServiceImpl userService;

  @PostMapping({"/", ""})
  public ResponseEntity<?> requestCouple(@AuthenticationPrincipal UserRequestDTO user,
      @RequestParam("receiverEmail") String receiverEmail) throws Exception {
    String requestEmail = user.getEmail();
    int result = requestService.requestCouple(requestEmail, receiverEmail);

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    switch (result) {
      case -1:
        responseBody.put("message", "해당 이메일의 사용자가 존재하지 않습니다.");
        break;
      case 0:
        responseBody.put("message", "커플은 커플신청을 할 수 없습니다.");
        break;
      case 1:
        responseBody.put("message", "커플 신청이 성공적으로 완료됐습니다.");
        break;
      case 2:
        responseBody.put("message", "해당 사용자는 이미 커플 신청을 받아서 신청을 보낼 수 없습니다.");
        responseBody.put("alert", false);
        break;
      default:
        responseBody.put("message", "알 수 없는 오류가 발생했습니다.");
    }

    return ResponseEntity.ok().body(responseBody);
  }

  @PostMapping("/accept")
  @Transactional
  public ResponseEntity<?> acceptRequest(@AuthenticationPrincipal UserRequestDTO user,
      @RequestParam("requestUserEmail") String requestUserEmail) throws Exception {
    log.info("백엔드로 넘어온 requestUserEmail : {}", requestUserEmail);
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);
    int result = requestService.acceptRequest(requestUserEmail, user.getEmail());
    log.info("acceptRequest 호출완료, 결과 : {}", result);

    if (result > 0) {
      responseBody.put("message", "커플 신청을 성공적으로 받았습니다.");

    } else {
      responseBody.put("message", "알 수없는 오류가 발생했습니다.");
    }
    return ResponseEntity.ok().body(responseBody);
  }

  @GetMapping("/check")
  public ResponseEntity<?> checkRequest(@AuthenticationPrincipal UserRequestDTO user, Model model)
      throws Exception {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    User requestUser = requestService.checkRequestUser(user.getEmail());
    String requestUserName = requestUser.getUserName();
    if (requestUserName == null) {
      responseBody.put("message", "커플 신청이 존재하지 않거나 이미 커플입니다.");
    }
    log.info("커플 신청한 사람의 이름 : {}", requestUserName);
    log.info("커플 신청한 사람의 이메일 : {}", requestUser.getEmail());
    responseBody.put("alert", false);
    model.addAttribute("requestUserName", requestUser.getUserName());
    model.addAttribute("requestUserProfileImageSource", requestUser.getProfileImageUrl());
    model.addAttribute("requestUserEmail", requestUser.getEmail());
    return ResponseEntity.ok().body(responseBody);
  }

}
