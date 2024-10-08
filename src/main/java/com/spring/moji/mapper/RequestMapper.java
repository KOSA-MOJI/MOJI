package com.spring.moji.mapper;

import com.spring.moji.dto.request.CoupleRequestDTO;
import com.spring.moji.entity.Request;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestMapper {

  int requestCouple(String requestEmail, String receiverEmail) throws Exception;


  Request checkRequest(String receiverEmail) throws Exception;

  int deleteRequest(String email) throws Exception;

  int addCouple(CoupleRequestDTO coupleRequestDTO) throws Exception;

  int deleteCouple(String email) throws Exception;

  int addCoupleAuth(String requestEmail) throws Exception;

  int deleteCoupleAuth(String email) throws Exception;

  int cancelRequest(String email) throws Exception;

  Request checkMyRequest(String requestEmail) throws Exception;
}
