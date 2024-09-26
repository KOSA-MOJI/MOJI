package com.spring.moji.service;

import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;

public interface RequestService {

  int requestCouple(String requestEmail, String receiverEmail) throws Exception;

  int acceptRequest(String requestEmail, String receiverEmail) throws Exception;

  User checkRequestUser(String receiverEmail) throws Exception;

  int deleteRequest(String email) throws Exception;

  int cancelRequest(String email) throws Exception;
}
