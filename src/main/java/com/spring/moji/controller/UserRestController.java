package com.spring.moji.controller;

import com.spring.moji.dto.request.BreakupRequestDTO;
import com.spring.moji.dto.request.CoupleInfoEditRequestDTO;
import com.spring.moji.dto.request.UserInfoEditRequestDTO;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
      @ModelAttribute UserInfoEditRequestDTO userInfoEditRequestDTO,
      HttpSession session)
      throws Exception {
    MultipartFile file = userInfoEditRequestDTO.getFile();
    String email = userInfoEditRequestDTO.getEmail();
    if (file != null) {
      String profileImageUrl = s3Util.uploadFile(file);
      userService.updateProfileImageUrl(email, profileImageUrl, session);
    }
    return ResponseEntity.ok()
        .body(Map.of("message", "Profile updated successfully", "redirectUrl", "/user/solo/"));
  }

  @PostMapping({"/couple/update-profile"})
  public ResponseEntity<Map<String, String>> updateCoupleProfileProcess(
      @ModelAttribute CoupleInfoEditRequestDTO coupleInfoEditRequestDTO,
      @AuthenticationPrincipal CustomerUserDetail user,
      HttpSession session)
      throws Exception {
    MultipartFile file = coupleInfoEditRequestDTO.getFile();
    String profileImageUrl;

    if (file != null && !file.isEmpty()) {
      profileImageUrl = s3Util.uploadFile(file);
    } else {
      profileImageUrl = user.getCouple().getCoupleProfileImage();
    }

    int result = userService.updateCoupleProfile(
        user.getCouple().getCouple_id()
        , coupleInfoEditRequestDTO.getCoupleName()
        , profileImageUrl
        , session
    );
    log.info("db에 커플네임이 바꼈는지 여부 {}", result);

    return ResponseEntity.ok()
        .body(Map.of("message", "Profile updated successfully",
            "couple_id", user.getCouple().getCouple_id().toString()
        ));

  }


  @PostMapping("/solo/request")
  public ResponseEntity<?> requestCouple(@AuthenticationPrincipal CustomerUserDetail user,
      @RequestPart("receiverEmail") String receiverEmail) throws Exception {
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
      @RequestBody BreakupRequestDTO breakupRequestDTO, HttpSession session) throws Exception {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("alert", true);

    String receiverEmail = breakupRequestDTO.getReceiverEmail();
    log.info("파트너의 이메일 : {}", receiverEmail);
    log.info("결별하는 사람의 이메일 : {}", user.getEmail());

    int result = requestService.breakup(user.getEmail(), receiverEmail, session);

    log.info("결별의 결과 {} ", result);
    if (result > 0) {
      responseBody.put("message", "헤어졌습니다.");

    } else {
      responseBody.put("message", "알수 없는 오류가 발생했습니다.");
    }
    return ResponseEntity.ok().body(responseBody);
  }
}
