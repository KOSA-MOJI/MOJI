package com.spring.moji.service;


import com.spring.moji.security.CustomerUserDetail;
import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;
import com.spring.moji.entity.UserAuth;
import com.spring.moji.mapper.RequestMapper;
import com.spring.moji.mapper.UserMapper;
import com.spring.moji.util.Roles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

  @Getter
  private enum Messages {
    CANNOT_SEND_TO_MYSELF("자기 자신에게 커플 신청을 보낼 수 없습니다."),
    USER_NOT_EXISTS("해당 이메일의 사용자가 존재하지 않습니다."),
    REQUESTER_COUPLE_ALREADY("커플은 커플신청을 할 수 없습니다."),
    RECEIVER_COUPLE_ALREADY("커플에겐 커플 신청을 할 수 없습니다."),
    RECEIVED_REQUEST_ALREADY("해당 사용자는 이미 커플 신청을 받아서 신청을 보낼 수 없습니다."),
    SENT_REQUEST_ALREADY("이미 커플 신청을 보냈습니다."),
    REQUEST_SUCCESS("성공적으로 커플 신청을 보냈습니다.");

    private final String message;

    Messages(String message) {
      this.message = message;
    }
  }


  private final RequestMapper requestMapper;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public String requestCouple(String requestEmail, String receiverEmail) throws Exception {
    User requester = userMapper.findUserByEmail(requestEmail);
    User receiver = userMapper.findUserByEmail(receiverEmail);

    if (requestEmail.equals(receiverEmail)) { // 스스로에게 커플 신청을 보내는 경우
      return Messages.CANNOT_SEND_TO_MYSELF.getMessage();
    }

    if (receiver == null) {  // 유저가 존재하지 않는 경우 X
      return Messages.USER_NOT_EXISTS.getMessage();
    }

    if (!requester.getCoupleStatus().equals(0L)) {// 보내는 사람이 커플인경우 X
      return Messages.REQUESTER_COUPLE_ALREADY.getMessage();
    }

    if (!receiver.getCoupleStatus().equals(0L)) {  // 받는 사람이 커플인 경우 X
      return Messages.RECEIVER_COUPLE_ALREADY.getMessage();
    }

    Request existingRequest = requestMapper.checkRequest(receiverEmail);
    Request sentRequest = requestMapper.checkMyRequest(requestEmail);

    if (existingRequest != null) { // 대상자가 받은 커플 신청이 있을 경우 X
      return Messages.RECEIVED_REQUEST_ALREADY.getMessage();
    }

    if (sentRequest != null) { // 내가 보낸  커플 신청이 없을 경우 X
      return Messages.SENT_REQUEST_ALREADY.getMessage();
    }
    requestMapper.requestCouple(requestEmail, receiverEmail); // 모든 조건을 다 통과하면 커플 신청을 함

    return Messages.REQUEST_SUCCESS.getMessage();
  }

  @Override
  @Transactional
  public int acceptRequest(String requestEmail, String receiverEmail) throws Exception {
    int result = requestMapper.addCouple(requestEmail, receiverEmail);

    if (result > 0) {
      result += requestMapper.addCoupleAuth(requestEmail);
      result += requestMapper.addCoupleAuth(receiverEmail);

      result += requestMapper.deleteRequest(receiverEmail);
      result += userMapper.convertCoupleStatusIntoCouple(requestEmail);
      result += userMapper.convertCoupleStatusIntoCouple(receiverEmail);
    }

    return result;
  }

  @Override
  public User checkRequestUser(String receiverEmail) throws Exception {
    Request result = requestMapper.checkRequest(receiverEmail);
    if (result == null) {
      return User.builder().build();
    }
    return userMapper.findUserByEmail(result.getRequestEmail());
  }

  @Override
  public int deleteRequest(String email) throws Exception {
    int result = requestMapper.deleteRequest(email);
    return result;
  }

  @Override
  public int cancelRequest(String email) throws Exception {
    int result = requestMapper.cancelRequest(email);
    return result;
  }

  @Override
  @Transactional
  public int breakup(String requestEmail, String receiverEmail) throws Exception {
    int result = 0;
    result += requestMapper.deleteCoupleAuth(requestEmail);
    result += requestMapper.deleteCoupleAuth(requestEmail);
    result += requestMapper.deleteCouple(requestEmail);
    return result;
  }
}
