package com.spring.moji.service;

import com.spring.moji.entity.Request;

public interface RequestService {

  int requestCouple(String requestEmail, String receiverEmail) throws Exception;

  int acceptRequest(String requestEmail, String receiverEmail) throws Exception;

  Request checkRequest(String receiverEmail) throws Exception;

  int denyRequest(String email) throws Exception;
}
