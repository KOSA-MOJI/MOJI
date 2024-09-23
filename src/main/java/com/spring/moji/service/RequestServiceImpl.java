package com.spring.moji.service;


import com.spring.moji.entity.User;
import com.spring.moji.mapper.RequestMapper;
import com.spring.moji.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

  private final RequestMapper requestMapper;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public int requestCouple(String requestEmail, String receiverEmail) throws Exception {
    int result;
    User receiver = userMapper.findUserByEmail(receiverEmail);

    if (receiver != null) { // 해당 유저가 존재하면
      if (receiver.getCoupleStatus().equals(1L)) {
        return -1;
      }
      String existingRequestEmail = requestMapper.checkRequest(receiverEmail); // 요청이 있는 지 확인
      if (existingRequestEmail == null) { // 요청이 없으면
        return requestMapper.requestCouple(requestEmail, receiverEmail); // 요청 보내고 1을 반환
      } else {
        return 0;  // 요청이 있으면 0을 반환
      }
    } else {
      return -1; // 해당 유저가 존재하지 않거나 커플이면 -1을 반환
    }
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
  @Transactional
  public User checkRequest(String email) throws Exception {
    String requestEmail = requestMapper.checkRequest(email);
    return userMapper.findUserByEmail(requestEmail);
  }

  @Override
  public int denyRequest(String email) throws Exception {
    int result = requestMapper.deleteRequest(email);
    return result;
  }
}
