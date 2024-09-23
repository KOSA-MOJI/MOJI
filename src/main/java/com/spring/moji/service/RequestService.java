package com.spring.moji.service;

import com.spring.moji.entity.User;

public interface RequestService {

  int requestCouple(String requestEmail, String receiverEmail) throws Exception;

  int acceptRequest(String requestEmail, String receiverEmail) throws Exception;

  User checkRequest(String email) throws Exception;

  int denyRequest(String email) throws Exception;
}
