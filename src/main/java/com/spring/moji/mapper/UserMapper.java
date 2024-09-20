package com.spring.moji.mapper;


import com.spring.moji.entity.UserAuth;
import com.spring.moji.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  public User login(String email);

  public int join(User user) throws Exception;

  public int insertAuth(UserAuth user) throws Exception;

  public int updateProfileImageUrl(String email, String profileImageUrl) throws Exception;
}
