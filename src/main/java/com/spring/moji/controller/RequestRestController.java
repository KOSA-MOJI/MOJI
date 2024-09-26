package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.User;
import com.spring.moji.service.RequestServiceImpl;
import com.spring.moji.service.UserServiceImpl;
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


@Slf4j
@RequiredArgsConstructor
@RestController
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


  @GetMapping("/check")
  public ResponseEntity<?> checkRequest(@AuthenticationPrincipal UserRequestDTO user)
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

  @PostMapping("/accept")
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

  @DeleteMapping("/deny")
  public ResponseEntity<?> denyRequest(@AuthenticationPrincipal UserRequestDTO user
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

  @DeleteMapping("/cancel")
  public ResponseEntity<?> cancelRequest(@AuthenticationPrincipal UserRequestDTO user
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

}
