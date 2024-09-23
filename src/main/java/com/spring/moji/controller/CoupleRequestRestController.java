package com.spring.moji.controller;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.service.RequestServiceImpl;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*
*   int requestCouple(String requestEmail, String receiverEmail) throws Exception;

  int acceptRequest(String requestEmail, String receiverEmail) throws Exception;

  User checkRequest(String email) throws Exception;

  int denyRequest(String email) throws Exception;
* */


@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/solo/request")
public class CoupleRequestRestController {

  private final RequestServiceImpl requestService;

  @PostMapping({"/", ""})
  public ResponseEntity<?> requestCouple(@AuthenticationPrincipal UserRequestDTO user,
      @RequestParam("receiverEmail") String receiverEmail) throws Exception {
    String requestEmail = user.getEmail();
    int result = requestService.requestCouple(requestEmail, receiverEmail);

    if (result == -1) {
      return ResponseEntity.ok()
          .body(Map.of("message", "해당 이메일의 사용자는 존재하지 않습니다.", "alert", true));
    } else if (result == 0) {
      return ResponseEntity.ok()
          .body(Map.of("message", "해당 사용자는 이미 커플 신청을 받아서 신청을 보낼 수 없습니다.", "alert", true));
    }
    return ResponseEntity.ok()
        .body(Map.of("message", "커플 신청이 성공적으로 완료됐습니다."));
  }


  @PostMapping("/accept")
  public ResponseEntity<?> acceptRequest(@AuthenticationPrincipal UserRequestDTO user,
      @RequestParam("receiverEmail") String receiverEmail) throws Exception {
    String requestEmail = user.getEmail();
    int result = requestService.requestCouple(requestEmail, receiverEmail);

    if (result == -1) {
      return ResponseEntity.ok()
          .body(Map.of("message", "해당 이메일의 사용자가 존재하지 않거나 이미 커플입니다.", "alert", true));
    } else if (result == 0) {
      return ResponseEntity.ok()
          .body(Map.of("message", "해당 사용자는 이미 커플 신청을 받아서 신청을 보낼 수 없습니다.", "alert", true));
    }
    return ResponseEntity.ok()
        .body(Map.of("message", "커플 신청이 성공적으로 완료됐습니다."));
  }
}
