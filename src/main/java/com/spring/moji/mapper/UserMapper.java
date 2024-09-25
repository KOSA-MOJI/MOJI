package com.spring.moji.mapper;


import com.spring.moji.entity.UserAuth;
import com.spring.moji.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  User login(String email);

  int join(User user) throws Exception;

  int insertAuth(UserAuth user) throws Exception;

  int updateProfileImageUrl(String email, String profileImageUrl) throws Exception;

  User findUserByEmail(String email) throws Exception;

  int convertCoupleStatusIntoCouple(String email);
}
