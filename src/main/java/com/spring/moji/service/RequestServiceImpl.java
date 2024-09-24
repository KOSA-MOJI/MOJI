package com.spring.moji.service;


import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;
import com.spring.moji.mapper.RequestMapper;
import com.spring.moji.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

  private final RequestMapper requestMapper;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public int requestCouple(String requestEmail, String receiverEmail) throws Exception {
    log.info("요청을 보낸 이메일 {}", receiverEmail);

    User receiver = userMapper.findUserByEmail(receiverEmail);
    log.info("요청을 보낸 이메일 {}", receiverEmail);

    if (receiver == null) {  // 유저가 존재하지 않는 경우
      return -1;
    }
    log.info("요청을 보낸 이메일 {}", receiverEmail);

    if (!receiver.getCoupleStatus().equals(0L)) {  // 유저가 커플인 경우
      return 0;
    }
    log.info("요청을 보낸 이메일 {}", receiverEmail);

    Request existingRequest = requestMapper.checkRequest(receiverEmail);
    log.info("existingRequest {}", existingRequest.getRequestEmail());
    log.info("existingRequest {}", existingRequest.getCreatedAt());
 
    if (existingRequest == null) { // 이미 커플 신청을 받았을 경우
      return requestMapper.requestCouple(requestEmail, receiverEmail);
    }
    log.info("현재 진행중인 리퀘스트 {}", existingRequest);
    log.info("요청값이 null로 들어옴");

    return 2;
  }

  @Override
  @Transactional
  public int acceptRequest(String requestEmail, String receiverEmail) throws Exception {
    int result = requestMapper.addCouple(requestEmail, receiverEmail);

    if (result > 0) {
      result += requestMapper.updateAuth(requestEmail);
      result += requestMapper.updateAuth(receiverEmail);
    }
    return result;
  }

  @Override
  public Request checkRequest(String receiverEmail) throws Exception {
    return requestMapper.checkRequest(receiverEmail);
  }

  @Override
  public int denyRequest(String email) throws Exception {
    int result = requestMapper.deleteRequest(email);
    return result;
  }
}
