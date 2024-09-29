package com.spring.moji.service;

import com.spring.moji.entity.Request;
import com.spring.moji.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

public interface RequestService {

  String requestCouple(String requestEmail, String receiverEmail) throws Exception;

  int acceptRequest(String requestEmail, String receiverEmail, HttpSession session)
      throws Exception;

  User checkRequestUser(String receiverEmail) throws Exception;

  int deleteRequest(String email) throws Exception;

  int cancelRequest(String email) throws Exception;

  int breakup(String requestEmail, String receiverEmail, HttpSession session) throws Exception;
}
