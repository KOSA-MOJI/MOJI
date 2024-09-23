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
    if (receiver != null) {
      String existingRequestEmail = requestMapper.checkRequest(receiverEmail);
      if (existingRequestEmail == null) {
        result = requestMapper.requestCouple(requestEmail, receiverEmail);
      } else {
        result = 0;
      }
    } else {
      result = -1;
    }
    return result;
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
