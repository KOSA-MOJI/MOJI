package com.spring.moji.service;


import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;
import com.spring.moji.mapper.RequestMapper;
import com.spring.moji.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    User receiver = userMapper.findUserByEmail(receiverEmail);

    if (receiver == null) {  // 유저가 존재하지 않는 경우
      return -1;
    }

    if (!receiver.getCoupleStatus().equals(0L)) {  // 유저가 커플인 경우
      return 0;
    }

    Request existingRequest = requestMapper.checkRequest(receiverEmail);
    if (existingRequest == null) { // 이미 커플 신청을 받았을 경우
      return requestMapper.requestCouple(requestEmail, receiverEmail);
    }
    return 2;
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
}
