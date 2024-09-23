package com.spring.moji.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestMapper {

  int requestCouple(String requestEmail, String receiverEmail) throws Exception;

  int addCouple(String user1Email, String user2Email) throws Exception;

  String checkRequest(String receiverEmail) throws Exception;

  int deleteRequest(String email) throws Exception;

  int updateAuth(String requestEmail) throws Exception;

}
