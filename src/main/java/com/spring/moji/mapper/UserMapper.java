package com.spring.moji.mapper;


import com.spring.moji.entity.UserAuthEntity;
import com.spring.moji.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  public UserEntity login(String email);

  public int join(UserEntity user) throws Exception;

  public int insertAuth(UserAuthEntity userAuthEntity) throws Exception;
}
